package com.projektandroid;

import java.io.IOException;
import java.io.InputStream;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.loaders.obj.ObjLoader;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.collision.CollisionMesh;
import com.collision.EllipsoidCollider;
import com.collision.SlideResponse;


public class Gra implements ApplicationListener{
	
	Mesh sphere,plane,cube;
	Texture steel,wood,fire;
	PerspectiveCamera camera;
	Sprite[][] sprites = new Sprite[10][10];
	SpriteBatch batch;
	private static float spherex = 0.0f, spherey = 0.0f, spherez = 0.0f, deltaTime = 0.0f;
	private static Vector3 move,position;
	CollisionMesh spherecollisionmesh,planecollisionmesh,cubecollisionmesh;
	EllipsoidCollider spherecollider,planecollider,cubecollider;
	
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
		
		spherecollisionmesh = new CollisionMesh(sphere, true);
		planecollisionmesh = new CollisionMesh(plane,true);
		cubecollisionmesh = new CollisionMesh(cube, true);
		
		spherecollider = new EllipsoidCollider(3,3,3,new SlideResponse());
		planecollider = new EllipsoidCollider(10,1,10,new SlideResponse());
		cubecollider = new EllipsoidCollider(10,10,10,new SlideResponse());
		
		//Camera set
		float aspectRatio =	(float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
		camera = new PerspectiveCamera(67,2f * aspectRatio, 2f);
		
		move = new Vector3();
		position = new Vector3();
		
	}

	public void resize(int width, int height) {
	}

	public void render() {
		GL10 gl = Gdx.graphics.getGL10();
		deltaTime = Gdx.graphics.getDeltaTime();
		
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		
		
		Gdx.gl.glEnable(GL10.GL_DEPTH_TEST);
		
		cameraSetup();
		collisionSetup(deltaTime);
		
		Gdx.gl.glEnable(GL10.GL_CULL_FACE);
		Gdx.gl.glEnable(GL10.GL_TEXTURE_2D);
		
		
		steel.bind();
		gl.glPushMatrix();
		//gl.glTranslatef(0.0f,1.0f,0.0f);
		sphereMovement(gl);
		sphere.render(GL10.GL_TRIANGLES);
		gl.glPopMatrix();
		
		wood.bind();
		gl.glPushMatrix();
		gl.glRotatef(270,1,0,0);
		gl.glScalef(2,2,2);
		plane.render(GL10.GL_TRIANGLES);
		gl.glPopMatrix();
		
		fire.bind();
		gl.glPushMatrix();
		gl.glTranslatef(3,3,3);
		gl.glScalef(10,10,10);
		cube.render(GL10.GL_TRIANGLES);
		gl.glPopMatrix();
	}

	public void pause() {	
	}

	public void resume() {
	}

	public void dispose() {
		sphere.dispose();
		steel.dispose();
		plane.dispose();
		wood.dispose();
		cube.dispose();
		fire.dispose();
	}
	
	public void sphereMovement(GL10 gl){
		spherex = Gdx.input.getAccelerometerX();
		spherey = Gdx.input.getAccelerometerY();
		spherez = Gdx.input.getAccelerometerZ();
		position.add(-spherex/10,spherey/10,0);
		gl.glTranslatef(position.x,0,position.y);
		gl.glRotatef(45 * (position.y / 5), 1, 0, 0);
		gl.glRotatef(-45 * (position.x / 2), 0, 0, 1);
	}
	
	public void cameraSetup(){
		camera.near = 1;
		camera.far = 100;
		camera.view.set(new Matrix4());
		camera.projection.set(new Matrix4());
		camera.position.set(position.x,10,position.y+5);
		camera.direction.set(0,-5,-3);
		camera.update();
		camera.apply(Gdx.gl10);
	}
	
	public void collisionSetup(float delta){
		if( planecollider.collide( spherecollisionmesh, position, move, 0.00001f ) )
		{
			Gdx.app.log("plane","Is on Plane");
		}
		if(!planecollider.collide( spherecollisionmesh, position, move, 0.00001f )){
			move.add(-0.1f*delta,0,0);
			Gdx.app.log("plane","OUT");
		}
		if(cubecollider.collide( spherecollisionmesh, position, move, 0.00001f ))
		{ 
			//move.add(0,-0.1f*delta,0);
			Gdx.app.log("cube","Collides with Cube");
		}
	}
	
}