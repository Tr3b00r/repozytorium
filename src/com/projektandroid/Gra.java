package com.projektandroid;

import java.io.IOException;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.util.Map;

import org.siprop.bullet.Bullet;
import org.siprop.bullet.Geometry;
import org.siprop.bullet.MotionState;
import org.siprop.bullet.RigidBody;
import org.siprop.bullet.Transform;
import org.siprop.bullet.shape.BoxShape;
import org.siprop.bullet.shape.SphereShape;
import org.siprop.bullet.shape.StaticPlaneShape;
import org.siprop.bullet.util.Point3;
import org.siprop.bullet.util.ShapeType;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.loaders.obj.ObjLoader;
import com.badlogic.gdx.math.Vector3;


/**
 * Actual game logic + physic implementation class
 * 
 * @author Robert Pietrzko
 *
 */
public class Gra implements ApplicationListener{
	
	private Mesh sphere,plane,cube;
	private Texture steel,wood,fire;
	private PerspectiveCamera camera;
	private static float spherex = 0.0f, spherey = 0.0f, spherez = 0.0f, deltaTime = 0.0f, startTime = 0.0f, endTime = 0.0f;
	private static Vector3 position;
	private Bullet physics = new Bullet();
	private BoxShape cubeShape;
	private StaticPlaneShape groundShape;
	private SphereShape sphereShape;
	private Geometry cubeGeom,sphereGeom,groundGeom;
	private static MotionState cubeState,groundState,sphereState;
	org.siprop.bullet.util.Vector3 origin =  new org.siprop.bullet.util.Vector3(0.0f, 0.0f, 0.0f);
	org.siprop.bullet.util.Vector3 idSize =  new org.siprop.bullet.util.Vector3(2.0f, 2.0f, 2.0f);
	private Map<Integer, RigidBody> rigidBody;
	private static float[] glMat = new float[16], matrix = new float[16];
	
	
	/**
	 * This method is called right after initialize() method in Android Project.
	 * Loads meshes(.obj) and textures(.png) from /data folder.
	 * Creates perspective camera.
	 * Starts physics.
	 * @throws IOException This Exception is thrown when specific file is not found.
	 */
	public void create() {
		//Loading meshes + textures
		try{
			
			InputStream spherefile = Gdx.files.internal("sphere.obj").read();
			sphere = ObjLoader.loadObj(spherefile,false);
			spherefile.close();
			
			InputStream planefile = Gdx.files.internal("plane.obj").read();
			plane = ObjLoader.loadObj(planefile,false);
			planefile.close();
			
			InputStream cubefile = Gdx.files.internal("cube.obj").read();
			cube = ObjLoader.loadObj(cubefile,false);
			planefile.close();
			
			FileHandle firefile = Gdx.files.internal("Fire.png");
			fire = new Texture(firefile);
			
			FileHandle steelfile = Gdx.files.internal("Steel.png");
			steel = new Texture(steelfile);
			
			FileHandle woodfile = Gdx.files.internal("Wood.png");
			wood = new Texture(woodfile);
		}catch(IOException e){
			e.printStackTrace();
		}
		
		
		//Camera initialization
		float aspectRatio =	(float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
		camera = new PerspectiveCamera(67,2f * aspectRatio, 2f);
		
		position = new Vector3();
		
		initPhysics();
	}

	public void resize(int width, int height) {
	}

	/**
	 * This method is responsible for OpenGL rendering mechanism.
	 * Adds lighting to the world.
	 * Checks rigidBody map for proper collisionshapes (like SphereShape or BoxShape).
	 */
	public void render() {
		float[] light_ambient = new float[] { 0.2f, 0.2f, 0.2f, 1.0f };
		float[] light_diffuse = new float[] { 1.0f, 1.0f, 1.0f, 1.0f };
		float[] light_specular = new float[] { 1.0f, 1.0f, 1.0f, 1.0f };
		float[] light_position0 = new float[] { 1.0f, 10.0f, 1.0f, 0.0f };
		float[] light_position1 = new float[] { -1.0f, -10.0f, -1.0f, 0.0f };
		
		GL10 gl = Gdx.graphics.getGL10();
		deltaTime = Gdx.graphics.getDeltaTime();
		
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		
		Gdx.graphics.getGL11().glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, light_ambient, 0);
		Gdx.graphics.getGL11().glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, light_diffuse, 0);
		Gdx.graphics.getGL11().glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, light_specular, 0);
		Gdx.graphics.getGL11().glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, light_position0, 0);
		
		Gdx.graphics.getGL11().glLightfv(GL10.GL_LIGHT1, GL10.GL_AMBIENT, light_ambient, 0);
		Gdx.graphics.getGL11().glLightfv(GL10.GL_LIGHT1, GL10.GL_DIFFUSE, light_diffuse, 0);
		Gdx.graphics.getGL11().glLightfv(GL10.GL_LIGHT1, GL10.GL_SPECULAR, light_specular, 0);
		Gdx.graphics.getGL11().glLightfv(GL10.GL_LIGHT1, GL10.GL_POSITION, light_position1, 0);
		
		Gdx.graphics.getGL11().glEnable(GL10.GL_LIGHTING);
		Gdx.graphics.getGL11().glEnable(GL10.GL_LIGHT0);
		Gdx.graphics.getGL11().glEnable(GL10.GL_LIGHT1);
		
		
		Gdx.gl.glEnable(GL10.GL_DEPTH_TEST);
		
		
		Gdx.gl.glEnable(GL10.GL_CULL_FACE);
		Gdx.gl.glEnable(GL10.GL_TEXTURE_2D);
		
		startTime = System.nanoTime();
		rigidBody = physics.doSimulation(deltaTime,1);
		endTime = (System.nanoTime() - startTime) / 1000000000.0f;
		
		for(RigidBody body: rigidBody.values()) {
			
			if(body.geometry.shape.getType() == ShapeType.BOX_SHAPE_PROXYTYPE) {
				gl.glPushMatrix();
				
				body.motionState.resultSimulation.getOpenGLMatrix(glMat);
				gl.glMultMatrixf(glMat, 0);

				cubeShape = (BoxShape) body.geometry.shape;
				
				fire.bind();
				gl.glScalef(5,5,5);
				cube.render(GL10.GL_TRIANGLES);
				gl.glPopMatrix();
			}
			
			if(body.geometry.shape.getType() == ShapeType.STATIC_PLANE_PROXYTYPE) {
					gl.glPushMatrix();
				
					body.motionState.resultSimulation.getOpenGLMatrix(glMat);
					gl.glMultMatrixf(glMat,0);

					groundShape = (StaticPlaneShape) body.geometry.shape;
				
					wood.bind();
					gl.glRotatef(270,1,0,0);
					plane.render(GL10.GL_TRIANGLES);
					gl.glPopMatrix();
			}
			
			if(body.geometry.shape.getType() == ShapeType.SPHERE_SHAPE_PROXYTYPE) {
				//float[] matrix = new float[16];
															
				gl.glPushMatrix();
												
				body.motionState.resultSimulation.getOpenGLMatrix(matrix);
				
				sphereMovement(gl);
				
				matrix[12] = position.x;
				matrix[14] = position.y;
				
				gl.glMultMatrixf(matrix,0);
								
				for(int i=0; i <matrix.length;i++){
				String x1 = Float.toString(body.motionState.resultSimulation.originPoint.x);
				String y1 = Float.toString(body.motionState.resultSimulation.originPoint.y);
				String z1 = Float.toString(body.motionState.resultSimulation.originPoint.z);
				Gdx.app.log("X",x1);
				Gdx.app.log("Y",y1);
				Gdx.app.log("Z",z1);
				Gdx.app.log("lol","---------");
				}
				
				steel.bind();
				sphere.render(GL10.GL_TRIANGLES);
				gl.glPopMatrix();
				cameraSetup(body);
			}
		}
		
		gl.glDisable(GL10.GL_CULL_FACE);
		gl.glDisable(GL10.GL_DEPTH_TEST);
		
	}

	public void pause() {	
	}

	public void resume() {
	}

	/**
	 * Destroys every model and texture from world.
	 */
	public void dispose() {
		sphere.dispose();
		steel.dispose();
		plane.dispose();
		wood.dispose();
		cube.dispose();
		fire.dispose();
	}
	
	/**
	 * Method responsible for sphere mesh (player) movement.
	 * @param gl loads current OpenGl version that is used.
	 */
	public void sphereMovement(GL10 gl){
		spherex = Gdx.input.getAccelerometerX();
		spherey = Gdx.input.getAccelerometerY();
		spherez = Gdx.input.getAccelerometerZ();
		position.add(-spherex/10,spherey/10,0);
	}
	
	/**
	 * Method sets up camera and binds it to player.
	 */
	public void cameraSetup(RigidBody body){
		camera.near = 1;
		camera.far = 100;
		camera.position.set(body.motionState.resultSimulation.originPoint.x,body.motionState.resultSimulation.originPoint.y+10.0f,body.motionState.resultSimulation.originPoint.z + 5.0f);
		camera.direction.set(0,-5,-3);
		camera.update();
		camera.apply(Gdx.gl10);
	}	
	
	/**
	 * Method initializes physics, creates collision body from: -mesh shape
	 * 															-mesh geometry
	 * 															-mesh motion state
	 */
	public void initPhysics(){
		float cubemass = 1.0f, spheremass = 5.0f;
		
		physics.createPhysicsWorld(new org.siprop.bullet.util.Vector3(-60.0f,-60.0f,-60.0f),
									new org.siprop.bullet.util.Vector3(60.0f,60.0f,60.0f),
									200, new org.siprop.bullet.util.Vector3(0.0f,-10.0f,0.0f) );
		
		groundShape = new StaticPlaneShape(new org.siprop.bullet.util.Vector3(0.0f, 1.0f, 0.0f),0.0f);
		groundGeom = physics.createGeometry(groundShape, 0.0f, new org.siprop.bullet.util.Vector3(0.0f, 0.0f, 0.0f));
		groundState = new MotionState();
		groundState.worldTransform = new Transform(new Point3(0f,-10.0f, 0.0f));
				
		for(int i = 0; i < 3; i++){
		cubeShape = new BoxShape(idSize);
		cubeGeom = physics.createGeometry(cubeShape, cubemass, origin);
		cubeState = new MotionState();
		org.siprop.bullet.util.Vector3 cubelocalInertia = new org.siprop.bullet.util.Vector3(0.026f, 0.026f, 0.026f);
		cubeShape.calculateLocalInertia(cubemass, cubelocalInertia);
		cubeGeom.localInertia = cubelocalInertia;
		cubeState.worldTransform = new Transform(new Point3(0.0f,10.0f+4*i,0.0f));
		physics.createAndAddRigidBody(cubeGeom, cubeState);
		}
		
		sphereShape = new SphereShape(2.0f);
		sphereGeom = physics.createGeometry(sphereShape, spheremass, origin);
		sphereState = new MotionState();
		org.siprop.bullet.util.Vector3 spherelocalInertia = new org.siprop.bullet.util.Vector3(0.026f, 0.026f, 0.026f);
		sphereGeom.localInertia = spherelocalInertia;
		sphereState.worldTransform = new Transform(new Point3(0.0f, 50.0f,0.0f));
		
		physics.createAndAddRigidBody(sphereGeom, sphereState);
		physics.createAndAddRigidBody(groundGeom, groundState);
	}
}