package com.blogspot.steigert.tyrian.screens;

import java.io.FileOutputStream;
import java.io.IOException;

import java.io.InputStream;
import java.util.Locale;
import java.util.Map;

import org.siprop.bullet.Bullet;
import org.siprop.bullet.Geometry;
import org.siprop.bullet.MotionState;
import org.siprop.bullet.RigidBody;
import org.siprop.bullet.Transform;
import org.siprop.bullet.shape.BoxShape;
import org.siprop.bullet.shape.CapsuleShape;
import org.siprop.bullet.shape.CylinderShape;
import org.siprop.bullet.shape.SphereShape;
import org.siprop.bullet.shape.StaticPlaneShape;
import org.siprop.bullet.util.Matrix3x3;
import org.siprop.bullet.util.Point3;
import org.siprop.bullet.util.Quaternion;
import org.siprop.bullet.util.ShapeType;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.loaders.obj.ObjLoader;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.projektandroid.Tyrian;

public class LevelScreen2 implements Screen{
	
	public static int ui;
	Tyrian game;
	Stage stage;
	Float indexior;
	private Texture steel,wood,stone,green,maja,pink,black;
	private PerspectiveCamera camera;
	private static float spherex = 0.0f, spherey = 0.0f, deltaTime = 0.0f, startTime = 0.0f, endTime = 0.0f;
	private static Vector3 position;
	private Bullet physics = new Bullet();
	private BoxShape wallShapeZ;
	private StaticPlaneShape groundShape;
	private SphereShape sphereShape;
	private Geometry sphereGeom,groundGeom,wallGeomZ;
	private static MotionState groundState,sphereState,wallStateZ;
	private static org.siprop.bullet.util.Vector3 origin =  new org.siprop.bullet.util.Vector3(0.0f, 0.0f, 0.0f);
	private static org.siprop.bullet.util.Vector3 wallSizeZ =  new org.siprop.bullet.util.Vector3(2.0f, 2.0f, 2.0f);
	private static org.siprop.bullet.util.Vector3 movement1 =  new org.siprop.bullet.util.Vector3(0.0f, 0.0f, 0.0f);
	private Map<Integer, RigidBody> rigidBody;
	private static float[] glMat = new float[16];
	private Mesh sphere,plane,wall;
	private Skin skin;
	public static final int GAME_VIEWPORT_WIDTH = 140, GAME_VIEWPORT_HEIGHT = 400;
	public static final int MENU_VIEWPORT_WIDTH = 300, MENU_VIEWPORT_HEIGHT = 300;
	float pozx,pozy,pozz;
	
	
	/**
	 * This method is called right after initialize() method in Android Project.
	 * Loads meshes(.obj) and textures(.png) from /data folder.
	 * Creates perspective camera.
	 * Starts physics.
	 * @throws IOException This Exception is thrown when specific file is not found.
	 */
	public LevelScreen2(Tyrian game,Float index,float x,float y, float z ) throws IOException{
		this.game=game;
		int width = ( isGameScreen() ? GAME_VIEWPORT_WIDTH : MENU_VIEWPORT_WIDTH );
		int height = ( isGameScreen() ? GAME_VIEWPORT_HEIGHT : MENU_VIEWPORT_HEIGHT );
		this.stage = new Stage( width, height, true );
		this.indexior=index;
		this.pozx=x;
		this.pozy=y;
		this.pozz=z;
		
  //Loading meshes + textures
  		try{
  			
  			InputStream spherefile = Gdx.files.internal("sphere.obj").read();
  			sphere = ObjLoader.loadObj(spherefile,false);
  			spherefile.close();
  			
  			InputStream planefile = Gdx.files.internal("plane.obj").read();
  			plane = ObjLoader.loadObj(planefile,false);
  			planefile.close();
  					
  			InputStream wallfile = Gdx.files.internal("cube.obj").read();
  			wall = ObjLoader.loadObj(wallfile,false);
  			wallfile.close();
  			  			
  			FileHandle steelfile = Gdx.files.internal("Steel.png");
  			steel = new Texture(steelfile);
  			
  			FileHandle woodfile = Gdx.files.internal("flour.png");
  			wood = new Texture(woodfile);
  			
  			FileHandle stonefile = Gdx.files.internal("corn.png");
  			stone = new Texture(stonefile);
  			
  			FileHandle greenfile = Gdx.files.internal("zielonaa.png");
  			green = new Texture(greenfile);
  			
  			FileHandle pinkfile = Gdx.files.internal("roze.png");
  			pink = new Texture(pinkfile);
  			
  			FileHandle majafile = Gdx.files.internal("maja.png");
  			maja = new Texture(majafile);
  			
  			FileHandle blackfile = Gdx.files.internal("czarna.png");
  			black = new Texture(blackfile);
  			
  		}catch(IOException e){
  			e.printStackTrace();
  		}
  		
  		
  		//Camera initialization
  		float aspectRatio =	(float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
  		camera = new PerspectiveCamera(67,2f * aspectRatio, 2f);
  		
  		position = new Vector3();
  		  		
  		initPhysics();
	}
	
	public static void Zapis() {

		FileOutputStream fos = null;
		String str = "Tekst do zapisania w pliku i dwie liczby: 123 i 321";

		try {
		  fos = new FileOutputStream("ala.txt"); //Open File 
		   for(int i = 0; i < str.length(); i++){
		     fos.write((int)str.charAt(i)); //Saving every char byte after by
		   }
		} catch(IOException ex){
		   System.out.println("File exception: "+ex);
		}

		 try {
		  fos.close(); //File close 
		 } catch (IOException e) {
		   e.printStackTrace();
		} 
	}
	
	protected boolean isGameScreen(){
		return false;
	}
	
	protected Skin getSkin(){
		if( skin == null ) {
			FileHandle skinFile = Gdx.files.internal( "skin/uiskin.json" );
			skin = new Skin( skinFile );
		}
		return skin;
	}

	public void show(){
	}
	
	public static int ui(){
		return ui;
	}
	
	/**
	 * This method is responsible for OpenGL rendering mechanism.
	 * Adds lighting to the world.
	 * Checks rigidBody map for proper collisionshapes (like SphereShape or BoxShape).
	 */
	public void render(float delta) {
		float[] light_ambient = new float[] { 1.5f, 1.5f, 1.5f, 1.5f };
		float[] light_diffuse = new float[] { 1.0f, 1.0f, 1.0f, 1.0f };
		float[] light_specular = new float[] { 1.0f, 1.0f, 1.0f, 1.0f };
		float[] light_position0 = new float[] { 1.0f, 10.0f, 1.0f, 0.0f };
		light_position0[0] = position.x + 10;
		light_position0[1] = position.y;
		light_position0[2] = position.z;
		float x = 0.0f, z = 0.0f;
		
		GL10 gl = Gdx.graphics.getGL10();
		deltaTime = Gdx.graphics.getDeltaTime();
		
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		
		Gdx.graphics.getGL11().glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, light_ambient, 0);
		Gdx.graphics.getGL11().glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, light_diffuse, 0);
		Gdx.graphics.getGL11().glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, light_specular, 0);
		Gdx.graphics.getGL11().glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, light_position0, 0);
				
		Gdx.graphics.getGL11().glEnable(GL10.GL_LIGHTING);
		Gdx.graphics.getGL11().glEnable(GL10.GL_LIGHT0);
		
		
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
								
				wallShapeZ = (BoxShape) body.geometry.shape;
				
				wood.bind();
				gl.glRotatef(270,1,0,0);
				gl.glScalef(6,6,6);
				wall.render(GL10.GL_TRIANGLES);
				gl.glPopMatrix();
			}
			
			if(body.geometry.shape.getType() == ShapeType.STATIC_PLANE_PROXYTYPE) {
					gl.glPushMatrix();
				
					body.motionState.resultSimulation.getOpenGLMatrix(glMat);
					gl.glMultMatrixf(glMat,0);

					groundShape = (StaticPlaneShape) body.geometry.shape;
				
					stone.bind();
					gl.glRotatef(270,1,0,0);
					gl.glScalef(6f,6f,6f);
					plane.render(GL10.GL_TRIANGLES);
					gl.glPopMatrix();
			}
			
			if(body.geometry.shape.getType() == ShapeType.SPHERE_SHAPE_PROXYTYPE) {
															
				gl.glPushMatrix();
										
				sphereMovement(body);
				
				body.motionState.resultSimulation.getOpenGLMatrix(glMat);
				gl.glMultMatrixf(glMat,0);
								
				x = body.motionState.resultSimulation.originPoint.x;
				z = body.motionState.resultSimulation.originPoint.z;
				
				String a = Float.toString(x);
				String c = Float.toString(z);
				Gdx.app.log("X",a);
				Gdx.app.log("Z",c);
				
				if((x >= 19.0 && x <= 26.5) && (z  >= 8.4 && z  <= 8.6)){
					Gdx.app.log("Zwyciestwo","lolol");
				}
					
				if(indexior==0)									
			 		black.bind();
						else if (indexior==1)	
							green.bind();
						else if (indexior==2)	
							pink.bind();
						else if (indexior==3)	
							steel.bind();
						else if (indexior==4)	
							maja.bind();
				
				sphere.render(GL10.GL_TRIANGLES);
				gl.glPopMatrix();
				cameraSetup(body);
			}
		}
		
		if(Gdx.input.justTouched()){
			FileHandle fh=Gdx.files.local("ala.txt");
			fh.writeString(String.valueOf(x)+" "+String.valueOf(z)+" "+String.valueOf(indexior),false);
			game.setScreen(new MenuScreen(game));
		}
		
		gl.glDisable(GL10.GL_CULL_FACE);
		gl.glDisable(GL10.GL_DEPTH_TEST);
		
	}

	public void resize(int width, int height) {
		stage.setViewport( width, height, true );
	}
	
	/**
	 * Method sets up camera and binds it to player.
	 */
	public void cameraSetup(RigidBody body){
		camera.near = 1;
		camera.far = 100;
		//camera.position.set(body.motionState.resultSimulation.originPoint.x,body.motionState.resultSimulation.originPoint.y+10.0f,body.motionState.resultSimulation.originPoint.z + 5.0f);
		camera.position.set(position.x,30,position.y);
		camera.direction.set(0,-5,-3);
		camera.update();
		camera.apply(Gdx.gl10);
	}	
	public void hide() {	
	}

	public void pause() {
	}

	public void resume() {
	}
	
	/**
	 * Method responsible for sphere mesh (player) movement.
	 * @param body loads current RigidBody body that is used.
	 */
	public void sphereMovement(RigidBody body){
		spherex = Gdx.input.getAccelerometerX();
		spherey = Gdx.input.getAccelerometerY();
		position.add(-spherex/10,spherey/10,0);
		movement1.x = -spherex/10;
		movement1.z = spherey/10;
		//physics.applyCentralImpulse(body,movement1);
	}

	public void dispose() {
		sphere.dispose();
		steel.dispose();
		plane.dispose();
		wood.dispose();
		wall.dispose();
		green.dispose();
		maja.dispose();
		pink.dispose();
		black.dispose();
	}
	
	/**
	 * Method initializes physics, creates collision body from: -mesh shape
	 * 															-mesh geometry
	 * 															-mesh motion state
	 */
	public void initPhysics(){
		float spheremass = 5.0f, wallmass = 0.0f, offset = 3.0f;
		
		physics.createPhysicsWorld(new org.siprop.bullet.util.Vector3(-60.0f,-60.0f,-60.0f),
									new org.siprop.bullet.util.Vector3(60.0f,60.0f,60.0f),
									200, new org.siprop.bullet.util.Vector3(0.0f,-10.0f,0.0f) );
		
		groundShape = new StaticPlaneShape(new org.siprop.bullet.util.Vector3(0.0f, 1.0f, 0.0f),0.0f);
		groundGeom = physics.createGeometry(groundShape, 0.0f, new org.siprop.bullet.util.Vector3(0.0f, 0.0f, 0.0f));
		groundState = new MotionState();
		groundState.worldTransform = new Transform(new Point3(12f,-10.0f, 11.5f));
				
		sphereShape = new SphereShape(1.5f);
		sphereGeom = physics.createGeometry(sphereShape, spheremass, origin);
		sphereState = new MotionState();
		org.siprop.bullet.util.Vector3 spherelocalInertia = new org.siprop.bullet.util.Vector3(0.026f, 0.026f, 0.026f);
		sphereGeom.localInertia = spherelocalInertia;
		sphereState.worldTransform = new Transform(new Point3(pozx, pozy,pozz));
		
		physics.createAndAddRigidBody(sphereGeom, sphereState);
		physics.createAndAddRigidBody(groundGeom, groundState);
		
		for(int i = 0; i < 3; i++){
			wallShapeZ = new BoxShape(wallSizeZ);
			wallGeomZ = physics.createGeometry(wallShapeZ, wallmass, origin);
			wallStateZ = new MotionState();
			org.siprop.bullet.util.Vector3 walllocalInertiaZ = new org.siprop.bullet.util.Vector3(0.026f, 0.026f, 0.026f);
			wallShapeZ.calculateLocalInertia(wallmass, walllocalInertiaZ);
			wallGeomZ.localInertia = walllocalInertiaZ;
			wallStateZ.worldTransform = new Transform(new Point3(-20.0f,10.0f,25.0f + offset*i));
			physics.createAndAddRigidBody(wallGeomZ, wallStateZ);
		}
		
		for(int i = 0; i < 3; i++){
			wallShapeZ = new BoxShape(wallSizeZ);
			wallGeomZ = physics.createGeometry(wallShapeZ, wallmass, origin);
			wallStateZ = new MotionState();
			org.siprop.bullet.util.Vector3 walllocalInertiaZ = new org.siprop.bullet.util.Vector3(0.026f, 0.026f, 0.026f);
			wallShapeZ.calculateLocalInertia(wallmass, walllocalInertiaZ);
			wallGeomZ.localInertia = walllocalInertiaZ;
			wallStateZ.worldTransform = new Transform(new Point3(-11.0f,10.0f,25.0f + offset*i));
			physics.createAndAddRigidBody(wallGeomZ, wallStateZ);
		}
		
		for(int j = 0; j < 4; j++){
			wallShapeZ = new BoxShape(wallSizeZ);
			wallGeomZ = physics.createGeometry(wallShapeZ, wallmass, origin);
			wallStateZ = new MotionState();
			org.siprop.bullet.util.Vector3 walllocalInertiaZ = new org.siprop.bullet.util.Vector3(0.026f, 0.026f, 0.026f);
			wallShapeZ.calculateLocalInertia(wallmass, walllocalInertiaZ);
			wallGeomZ.localInertia = walllocalInertiaZ;
			wallStateZ.worldTransform = new Transform(new Point3(-11.0f - offset*j,10.0f,34.2f));
			physics.createAndAddRigidBody(wallGeomZ, wallStateZ);
		}
		
		for(int j = 0; j < 4; j++){
			wallShapeZ = new BoxShape(wallSizeZ);
			wallGeomZ = physics.createGeometry(wallShapeZ, wallmass, origin);
			wallStateZ = new MotionState();
			org.siprop.bullet.util.Vector3 walllocalInertiaZ = new org.siprop.bullet.util.Vector3(0.026f, 0.026f, 0.026f);
			wallShapeZ.calculateLocalInertia(wallmass, walllocalInertiaZ);
			wallGeomZ.localInertia = walllocalInertiaZ;
			wallStateZ.worldTransform = new Transform(new Point3(-11.0f - offset*j,7.0f,34.2f));
			physics.createAndAddRigidBody(wallGeomZ, wallStateZ);
		}
		
		for(int j = 0; j < 2; j++){
			wallShapeZ = new BoxShape(wallSizeZ);
			wallGeomZ = physics.createGeometry(wallShapeZ, wallmass, origin);
			wallStateZ = new MotionState();
			org.siprop.bullet.util.Vector3 walllocalInertiaZ = new org.siprop.bullet.util.Vector3(0.026f, 0.026f, 0.026f);
			wallShapeZ.calculateLocalInertia(wallmass, walllocalInertiaZ);
			wallGeomZ.localInertia = walllocalInertiaZ;
			wallStateZ.worldTransform = new Transform(new Point3(-14.0f - offset*j,7.0f,31.2f));
			physics.createAndAddRigidBody(wallGeomZ, wallStateZ);
		}
		
		for(int j = 0; j < 2; j++){
			wallShapeZ = new BoxShape(wallSizeZ);
			wallGeomZ = physics.createGeometry(wallShapeZ, wallmass, origin);
			wallStateZ = new MotionState();
			org.siprop.bullet.util.Vector3 walllocalInertiaZ = new org.siprop.bullet.util.Vector3(0.026f, 0.026f, 0.026f);
			wallShapeZ.calculateLocalInertia(wallmass, walllocalInertiaZ);
			wallGeomZ.localInertia = walllocalInertiaZ;
			wallStateZ.worldTransform = new Transform(new Point3(-14.0f - offset*j,7.0f,28.2f));
			physics.createAndAddRigidBody(wallGeomZ, wallStateZ);
		}
		
		for(int i = 0; i < 3; i++){
			wallShapeZ = new BoxShape(wallSizeZ);
			wallGeomZ = physics.createGeometry(wallShapeZ, wallmass, origin);
			wallStateZ = new MotionState();
			org.siprop.bullet.util.Vector3 walllocalInertiaZ = new org.siprop.bullet.util.Vector3(0.026f, 0.026f, 0.026f);
			wallShapeZ.calculateLocalInertia(wallmass, walllocalInertiaZ);
			wallGeomZ.localInertia = walllocalInertiaZ;
			wallStateZ.worldTransform = new Transform(new Point3(-20.0f,7.0f,19.0f + offset*i));
			physics.createAndAddRigidBody(wallGeomZ, wallStateZ);
		}
		
		for(int i = 0; i < 3; i++){
			wallShapeZ = new BoxShape(wallSizeZ);
			wallGeomZ = physics.createGeometry(wallShapeZ, wallmass, origin);
			wallStateZ = new MotionState();
			org.siprop.bullet.util.Vector3 walllocalInertiaZ = new org.siprop.bullet.util.Vector3(0.026f, 0.026f, 0.026f);
			wallShapeZ.calculateLocalInertia(wallmass, walllocalInertiaZ);
			wallGeomZ.localInertia = walllocalInertiaZ;
			wallStateZ.worldTransform = new Transform(new Point3(-11.0f,7.0f,19.0f + offset*i));
			physics.createAndAddRigidBody(wallGeomZ, wallStateZ);
		}
		
		for(int j = 0; j < 2; j++){
			wallShapeZ = new BoxShape(wallSizeZ);
			wallGeomZ = physics.createGeometry(wallShapeZ, wallmass, origin);
			wallStateZ = new MotionState();
			org.siprop.bullet.util.Vector3 walllocalInertiaZ = new org.siprop.bullet.util.Vector3(0.026f, 0.026f, 0.026f);
			wallShapeZ.calculateLocalInertia(wallmass, walllocalInertiaZ);
			wallGeomZ.localInertia = walllocalInertiaZ;
			wallStateZ.worldTransform = new Transform(new Point3(-14.0f - offset*j,4.0f,22.2f));
			physics.createAndAddRigidBody(wallGeomZ, wallStateZ);
		}
		
		for(int j = 0; j < 2; j++){
			wallShapeZ = new BoxShape(wallSizeZ);
			wallGeomZ = physics.createGeometry(wallShapeZ, wallmass, origin);
			wallStateZ = new MotionState();
			org.siprop.bullet.util.Vector3 walllocalInertiaZ = new org.siprop.bullet.util.Vector3(0.026f, 0.026f, 0.026f);
			wallShapeZ.calculateLocalInertia(wallmass, walllocalInertiaZ);
			wallGeomZ.localInertia = walllocalInertiaZ;
			wallStateZ.worldTransform = new Transform(new Point3(-14.0f - offset*j,4.0f,25.2f));
			physics.createAndAddRigidBody(wallGeomZ, wallStateZ);
		}
		
		for(int i = 0; i < 3; i++){
			wallShapeZ = new BoxShape(wallSizeZ);
			wallGeomZ = physics.createGeometry(wallShapeZ, wallmass, origin);
			wallStateZ = new MotionState();
			org.siprop.bullet.util.Vector3 walllocalInertiaZ = new org.siprop.bullet.util.Vector3(0.026f, 0.026f, 0.026f);
			wallShapeZ.calculateLocalInertia(wallmass, walllocalInertiaZ);
			wallGeomZ.localInertia = walllocalInertiaZ;
			wallStateZ.worldTransform = new Transform(new Point3(-20.0f,4.0f,13.0f + offset*i));
			physics.createAndAddRigidBody(wallGeomZ, wallStateZ);
		}
		
		for(int i = 0; i < 3; i++){
			wallShapeZ = new BoxShape(wallSizeZ);
			wallGeomZ = physics.createGeometry(wallShapeZ, wallmass, origin);
			wallStateZ = new MotionState();
			org.siprop.bullet.util.Vector3 walllocalInertiaZ = new org.siprop.bullet.util.Vector3(0.026f, 0.026f, 0.026f);
			wallShapeZ.calculateLocalInertia(wallmass, walllocalInertiaZ);
			wallGeomZ.localInertia = walllocalInertiaZ;
			wallStateZ.worldTransform = new Transform(new Point3(-11.0f,4.0f,13.0f + offset*i));
			physics.createAndAddRigidBody(wallGeomZ, wallStateZ);
		}
		
		for(int j = 0; j < 2; j++){
			wallShapeZ = new BoxShape(wallSizeZ);
			wallGeomZ = physics.createGeometry(wallShapeZ, wallmass, origin);
			wallStateZ = new MotionState();
			org.siprop.bullet.util.Vector3 walllocalInertiaZ = new org.siprop.bullet.util.Vector3(0.026f, 0.026f, 0.026f);
			wallShapeZ.calculateLocalInertia(wallmass, walllocalInertiaZ);
			wallGeomZ.localInertia = walllocalInertiaZ;
			wallStateZ.worldTransform = new Transform(new Point3(-14.0f - offset*j,1.0f,16.2f));
			physics.createAndAddRigidBody(wallGeomZ, wallStateZ);
		}
		
		for(int j = 0; j < 2; j++){
			wallShapeZ = new BoxShape(wallSizeZ);
			wallGeomZ = physics.createGeometry(wallShapeZ, wallmass, origin);
			wallStateZ = new MotionState();
			org.siprop.bullet.util.Vector3 walllocalInertiaZ = new org.siprop.bullet.util.Vector3(0.026f, 0.026f, 0.026f);
			wallShapeZ.calculateLocalInertia(wallmass, walllocalInertiaZ);
			wallGeomZ.localInertia = walllocalInertiaZ;
			wallStateZ.worldTransform = new Transform(new Point3(-14.0f - offset*j,1.0f,19.2f));
			physics.createAndAddRigidBody(wallGeomZ, wallStateZ);
		}
		
		for(int i = 0; i < 3; i++){
			wallShapeZ = new BoxShape(wallSizeZ);
			wallGeomZ = physics.createGeometry(wallShapeZ, wallmass, origin);
			wallStateZ = new MotionState();
			org.siprop.bullet.util.Vector3 walllocalInertiaZ = new org.siprop.bullet.util.Vector3(0.026f, 0.026f, 0.026f);
			wallShapeZ.calculateLocalInertia(wallmass, walllocalInertiaZ);
			wallGeomZ.localInertia = walllocalInertiaZ;
			wallStateZ.worldTransform = new Transform(new Point3(-20.0f,1.0f,7.0f + offset*i));
			physics.createAndAddRigidBody(wallGeomZ, wallStateZ);
		}
		
		for(int i = 0; i < 3; i++){
			wallShapeZ = new BoxShape(wallSizeZ);
			wallGeomZ = physics.createGeometry(wallShapeZ, wallmass, origin);
			wallStateZ = new MotionState();
			org.siprop.bullet.util.Vector3 walllocalInertiaZ = new org.siprop.bullet.util.Vector3(0.026f, 0.026f, 0.026f);
			wallShapeZ.calculateLocalInertia(wallmass, walllocalInertiaZ);
			wallGeomZ.localInertia = walllocalInertiaZ;
			wallStateZ.worldTransform = new Transform(new Point3(-11.0f,1.0f,7.0f + offset*i));
			physics.createAndAddRigidBody(wallGeomZ, wallStateZ);
		}
		
		for(int j = 0; j < 2; j++){
			wallShapeZ = new BoxShape(wallSizeZ);
			wallGeomZ = physics.createGeometry(wallShapeZ, wallmass, origin);
			wallStateZ = new MotionState();
			org.siprop.bullet.util.Vector3 walllocalInertiaZ = new org.siprop.bullet.util.Vector3(0.026f, 0.026f, 0.026f);
			wallShapeZ.calculateLocalInertia(wallmass, walllocalInertiaZ);
			wallGeomZ.localInertia = walllocalInertiaZ;
			wallStateZ.worldTransform = new Transform(new Point3(-14.0f - offset*j,-2.0f,10.2f));
			physics.createAndAddRigidBody(wallGeomZ, wallStateZ);
		}
		
		for(int j = 0; j < 2; j++){
			wallShapeZ = new BoxShape(wallSizeZ);
			wallGeomZ = physics.createGeometry(wallShapeZ, wallmass, origin);
			wallStateZ = new MotionState();
			org.siprop.bullet.util.Vector3 walllocalInertiaZ = new org.siprop.bullet.util.Vector3(0.026f, 0.026f, 0.026f);
			wallShapeZ.calculateLocalInertia(wallmass, walllocalInertiaZ);
			wallGeomZ.localInertia = walllocalInertiaZ;
			wallStateZ.worldTransform = new Transform(new Point3(-14.0f - offset*j,-2.0f,13.2f));
			physics.createAndAddRigidBody(wallGeomZ, wallStateZ);
		}
		
		for(int i = 0; i < 3; i++){
			wallShapeZ = new BoxShape(wallSizeZ);
			wallGeomZ = physics.createGeometry(wallShapeZ, wallmass, origin);
			wallStateZ = new MotionState();
			org.siprop.bullet.util.Vector3 walllocalInertiaZ = new org.siprop.bullet.util.Vector3(0.026f, 0.026f, 0.026f);
			wallShapeZ.calculateLocalInertia(wallmass, walllocalInertiaZ);
			wallGeomZ.localInertia = walllocalInertiaZ;
			wallStateZ.worldTransform = new Transform(new Point3(-20.0f,-2.0f,1.0f + offset*i));
			physics.createAndAddRigidBody(wallGeomZ, wallStateZ);
		}
		
		for(int i = 0; i < 3; i++){
			wallShapeZ = new BoxShape(wallSizeZ);
			wallGeomZ = physics.createGeometry(wallShapeZ, wallmass, origin);
			wallStateZ = new MotionState();
			org.siprop.bullet.util.Vector3 walllocalInertiaZ = new org.siprop.bullet.util.Vector3(0.026f, 0.026f, 0.026f);
			wallShapeZ.calculateLocalInertia(wallmass, walllocalInertiaZ);
			wallGeomZ.localInertia = walllocalInertiaZ;
			wallStateZ.worldTransform = new Transform(new Point3(-11.0f,-2.0f,1.0f + offset*i));
			physics.createAndAddRigidBody(wallGeomZ, wallStateZ);
		}
		
		for(int j = 0; j < 2; j++){
			wallShapeZ = new BoxShape(wallSizeZ);
			wallGeomZ = physics.createGeometry(wallShapeZ, wallmass, origin);
			wallStateZ = new MotionState();
			org.siprop.bullet.util.Vector3 walllocalInertiaZ = new org.siprop.bullet.util.Vector3(0.026f, 0.026f, 0.026f);
			wallShapeZ.calculateLocalInertia(wallmass, walllocalInertiaZ);
			wallGeomZ.localInertia = walllocalInertiaZ;
			wallStateZ.worldTransform = new Transform(new Point3(-14.0f - offset*j,-5.0f,4.2f));
			physics.createAndAddRigidBody(wallGeomZ, wallStateZ);
		}
		
		for(int j = 0; j < 2; j++){
			wallShapeZ = new BoxShape(wallSizeZ);
			wallGeomZ = physics.createGeometry(wallShapeZ, wallmass, origin);
			wallStateZ = new MotionState();
			org.siprop.bullet.util.Vector3 walllocalInertiaZ = new org.siprop.bullet.util.Vector3(0.026f, 0.026f, 0.026f);
			wallShapeZ.calculateLocalInertia(wallmass, walllocalInertiaZ);
			wallGeomZ.localInertia = walllocalInertiaZ;
			wallStateZ.worldTransform = new Transform(new Point3(-14.0f - offset*j,-5.0f,7.2f));
			physics.createAndAddRigidBody(wallGeomZ, wallStateZ);
		}
		
		for(int j = 0; j < 6; j++){
			wallShapeZ = new BoxShape(wallSizeZ);
			wallGeomZ = physics.createGeometry(wallShapeZ, wallmass, origin);
			wallStateZ = new MotionState();
			org.siprop.bullet.util.Vector3 walllocalInertiaZ = new org.siprop.bullet.util.Vector3(0.026f, 0.026f, 0.026f);
			wallShapeZ.calculateLocalInertia(wallmass, walllocalInertiaZ);
			wallGeomZ.localInertia = walllocalInertiaZ;
			wallStateZ.worldTransform = new Transform(new Point3(-2.0f - offset*j,-10.0f,-10.5f));
			physics.createAndAddRigidBody(wallGeomZ, wallStateZ);
		}
		
		for(int j = 0; j < 6; j++){
			wallShapeZ = new BoxShape(wallSizeZ);
			wallGeomZ = physics.createGeometry(wallShapeZ, wallmass, origin);
			wallStateZ = new MotionState();
			org.siprop.bullet.util.Vector3 walllocalInertiaZ = new org.siprop.bullet.util.Vector3(0.026f, 0.026f, 0.026f);
			wallShapeZ.calculateLocalInertia(wallmass, walllocalInertiaZ);
			wallGeomZ.localInertia = walllocalInertiaZ;
			wallStateZ.worldTransform = new Transform(new Point3(-2.0f - offset*j,-7.0f,-10.5f));
			physics.createAndAddRigidBody(wallGeomZ, wallStateZ);
		}
		
		for(int j = 0; j < 6; j++){
			wallShapeZ = new BoxShape(wallSizeZ);
			wallGeomZ = physics.createGeometry(wallShapeZ, wallmass, origin);
			wallStateZ = new MotionState();
			org.siprop.bullet.util.Vector3 walllocalInertiaZ = new org.siprop.bullet.util.Vector3(0.026f, 0.026f, 0.026f);
			wallShapeZ.calculateLocalInertia(wallmass, walllocalInertiaZ);
			wallGeomZ.localInertia = walllocalInertiaZ;
			wallStateZ.worldTransform = new Transform(new Point3(-2.0f - offset*j,-4.0f,-10.5f));
			physics.createAndAddRigidBody(wallGeomZ, wallStateZ);
		}
		
		for(int j = 0; j < 6; j++){
			wallShapeZ = new BoxShape(wallSizeZ);
			wallGeomZ = physics.createGeometry(wallShapeZ, wallmass, origin);
			wallStateZ = new MotionState();
			org.siprop.bullet.util.Vector3 walllocalInertiaZ = new org.siprop.bullet.util.Vector3(0.026f, 0.026f, 0.026f);
			wallShapeZ.calculateLocalInertia(wallmass, walllocalInertiaZ);
			wallGeomZ.localInertia = walllocalInertiaZ;
			wallStateZ.worldTransform = new Transform(new Point3(-2.0f - offset*j,-1.0f,-10.5f));
			physics.createAndAddRigidBody(wallGeomZ, wallStateZ);
		}
		
		for(int i = 0; i < 6; i++){
			wallShapeZ = new BoxShape(wallSizeZ);
			wallGeomZ = physics.createGeometry(wallShapeZ, wallmass, origin);
			wallStateZ = new MotionState();
			org.siprop.bullet.util.Vector3 walllocalInertiaZ = new org.siprop.bullet.util.Vector3(0.026f, 0.026f, 0.026f);
			wallShapeZ.calculateLocalInertia(wallmass, walllocalInertiaZ);
			wallGeomZ.localInertia = walllocalInertiaZ;
			wallStateZ.worldTransform = new Transform(new Point3(-20.0f,-4.0f,-10.5f + offset*i));
			physics.createAndAddRigidBody(wallGeomZ, wallStateZ);
		}
		
		for(int i = 0; i < 6; i++){
			wallShapeZ = new BoxShape(wallSizeZ);
			wallGeomZ = physics.createGeometry(wallShapeZ, wallmass, origin);
			wallStateZ = new MotionState();
			org.siprop.bullet.util.Vector3 walllocalInertiaZ = new org.siprop.bullet.util.Vector3(0.026f, 0.026f, 0.026f);
			wallShapeZ.calculateLocalInertia(wallmass, walllocalInertiaZ);
			wallGeomZ.localInertia = walllocalInertiaZ;
			wallStateZ.worldTransform = new Transform(new Point3(-20.0f,-7.0f,-10.5f + offset*i));
			physics.createAndAddRigidBody(wallGeomZ, wallStateZ);
		}
		
		for(int i = 0; i < 6; i++){
			wallShapeZ = new BoxShape(wallSizeZ);
			wallGeomZ = physics.createGeometry(wallShapeZ, wallmass, origin);
			wallStateZ = new MotionState();
			org.siprop.bullet.util.Vector3 walllocalInertiaZ = new org.siprop.bullet.util.Vector3(0.026f, 0.026f, 0.026f);
			wallShapeZ.calculateLocalInertia(wallmass, walllocalInertiaZ);
			wallGeomZ.localInertia = walllocalInertiaZ;
			wallStateZ.worldTransform = new Transform(new Point3(-20.0f,-10.0f,-10.5f + offset*i));
			physics.createAndAddRigidBody(wallGeomZ, wallStateZ);
		}
		
		for(int j = 0; j < 6; j++){
			wallShapeZ = new BoxShape(wallSizeZ);
			wallGeomZ = physics.createGeometry(wallShapeZ, wallmass, origin);
			wallStateZ = new MotionState();
			org.siprop.bullet.util.Vector3 walllocalInertiaZ = new org.siprop.bullet.util.Vector3(0.026f, 0.026f, 0.026f);
			wallShapeZ.calculateLocalInertia(wallmass, walllocalInertiaZ);
			wallGeomZ.localInertia = walllocalInertiaZ;
			wallStateZ.worldTransform = new Transform(new Point3(-2.0f - offset*j,-10.0f,-3.5f));
			physics.createAndAddRigidBody(wallGeomZ, wallStateZ);
		}
		
		for(int j = 0; j < 6; j++){
			wallShapeZ = new BoxShape(wallSizeZ);
			wallGeomZ = physics.createGeometry(wallShapeZ, wallmass, origin);
			wallStateZ = new MotionState();
			org.siprop.bullet.util.Vector3 walllocalInertiaZ = new org.siprop.bullet.util.Vector3(0.026f, 0.026f, 0.026f);
			wallShapeZ.calculateLocalInertia(wallmass, walllocalInertiaZ);
			wallGeomZ.localInertia = walllocalInertiaZ;
			wallStateZ.worldTransform = new Transform(new Point3(-2.0f - offset*j,-7.0f,-3.5f));
			physics.createAndAddRigidBody(wallGeomZ, wallStateZ);
		}
		
		for(int j = 0; j < 14; j++){
			wallShapeZ = new BoxShape(wallSizeZ);
			wallGeomZ = physics.createGeometry(wallShapeZ, wallmass, origin);
			wallStateZ = new MotionState();
			org.siprop.bullet.util.Vector3 walllocalInertiaZ = new org.siprop.bullet.util.Vector3(0.026f, 0.026f, 0.026f);
			wallShapeZ.calculateLocalInertia(wallmass, walllocalInertiaZ);
			wallGeomZ.localInertia = walllocalInertiaZ;
			wallStateZ.worldTransform = new Transform(new Point3(40.0f - offset*j,-10.0f,-10.5f));
			physics.createAndAddRigidBody(wallGeomZ, wallStateZ);
		}
		
		for(int j = 0; j < 14; j++){
			wallShapeZ = new BoxShape(wallSizeZ);
			wallGeomZ = physics.createGeometry(wallShapeZ, wallmass, origin);
			wallStateZ = new MotionState();
			org.siprop.bullet.util.Vector3 walllocalInertiaZ = new org.siprop.bullet.util.Vector3(0.026f, 0.026f, 0.026f);
			wallShapeZ.calculateLocalInertia(wallmass, walllocalInertiaZ);
			wallGeomZ.localInertia = walllocalInertiaZ;
			wallStateZ.worldTransform = new Transform(new Point3(40.0f - offset*j,-7.0f,-10.5f));
			physics.createAndAddRigidBody(wallGeomZ, wallStateZ);
		}
		
		for(int j = 0; j < 14; j++){
			wallShapeZ = new BoxShape(wallSizeZ);
			wallGeomZ = physics.createGeometry(wallShapeZ, wallmass, origin);
			wallStateZ = new MotionState();
			org.siprop.bullet.util.Vector3 walllocalInertiaZ = new org.siprop.bullet.util.Vector3(0.026f, 0.026f, 0.026f);
			wallShapeZ.calculateLocalInertia(wallmass, walllocalInertiaZ);
			wallGeomZ.localInertia = walllocalInertiaZ;
			wallStateZ.worldTransform = new Transform(new Point3(40.0f - offset*j,-4.0f,-10.5f));
			physics.createAndAddRigidBody(wallGeomZ, wallStateZ);
		}
		
		for(int i = 0; i < 18; i++){
			wallShapeZ = new BoxShape(wallSizeZ);
			wallGeomZ = physics.createGeometry(wallShapeZ, wallmass, origin);
			wallStateZ = new MotionState();
			org.siprop.bullet.util.Vector3 walllocalInertiaZ = new org.siprop.bullet.util.Vector3(0.026f, 0.026f, 0.026f);
			wallShapeZ.calculateLocalInertia(wallmass, walllocalInertiaZ);
			wallGeomZ.localInertia = walllocalInertiaZ;
			wallStateZ.worldTransform = new Transform(new Point3(40.0f,-10.0f,-7.5f + offset*i));
			physics.createAndAddRigidBody(wallGeomZ, wallStateZ);
		}
		
		for(int i = 0; i < 18; i++){
			wallShapeZ = new BoxShape(wallSizeZ);
			wallGeomZ = physics.createGeometry(wallShapeZ, wallmass, origin);
			wallStateZ = new MotionState();
			org.siprop.bullet.util.Vector3 walllocalInertiaZ = new org.siprop.bullet.util.Vector3(0.026f, 0.026f, 0.026f);
			wallShapeZ.calculateLocalInertia(wallmass, walllocalInertiaZ);
			wallGeomZ.localInertia = walllocalInertiaZ;
			wallStateZ.worldTransform = new Transform(new Point3(40.0f,-7.0f,-7.5f + offset*i));
			physics.createAndAddRigidBody(wallGeomZ, wallStateZ);
		}
		
		for(int i = 0; i < 18; i++){
			wallShapeZ = new BoxShape(wallSizeZ);
			wallGeomZ = physics.createGeometry(wallShapeZ, wallmass, origin);
			wallStateZ = new MotionState();
			org.siprop.bullet.util.Vector3 walllocalInertiaZ = new org.siprop.bullet.util.Vector3(0.026f, 0.026f, 0.026f);
			wallShapeZ.calculateLocalInertia(wallmass, walllocalInertiaZ);
			wallGeomZ.localInertia = walllocalInertiaZ;
			wallStateZ.worldTransform = new Transform(new Point3(40.0f,-4.0f,-7.5f + offset*i));
			physics.createAndAddRigidBody(wallGeomZ, wallStateZ);
		}
		
		for(int i = 0; i < 15; i++){
			wallShapeZ = new BoxShape(wallSizeZ);
			wallGeomZ = physics.createGeometry(wallShapeZ, wallmass, origin);
			wallStateZ = new MotionState();
			org.siprop.bullet.util.Vector3 walllocalInertiaZ = new org.siprop.bullet.util.Vector3(0.026f, 0.026f, 0.026f);
			wallShapeZ.calculateLocalInertia(wallmass, walllocalInertiaZ);
			wallGeomZ.localInertia = walllocalInertiaZ;
			wallStateZ.worldTransform = new Transform(new Point3(1.0f,-10.0f,-10.5f + offset*i));
			physics.createAndAddRigidBody(wallGeomZ, wallStateZ);
		}
	}
}
