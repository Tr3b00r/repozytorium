/*
 * Copyright 2010 Mario Zechner (contact@badlogicgames.com), Nathan Sweet (admin@esotericsoftware.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package com.blogspot.steigert.tyrian.services;
/*
 * Copyright 2010 Mario Zechner (contact@badlogicgames.com), Nathan Sweet (admin@esotericsoftware.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */


import java.awt.Component;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.loaders.obj.ObjLoader;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;


/** The renderer receives a simulation and renders it.
 * @author mzechner */
public class RendererGL10 implements Renderer  {
	/** sprite batch to draw text **/

	private Mesh sphere;
	/** the ship texture **/
	private Texture steel;
	/** the invader mesh **/
	private Mesh plane;
	/** the invader texture **/
	private Texture wood;
	/** the block mesh **/
	private Mesh cube;
	/** the background texture **/
	private Texture fire;
	/** the explosion texture **/
	/** the font **/
	
	/** the rotation angle of all invaders around y **/
	/** status string **/
	/** keeping track of the last score so we don't constantly construct a new string **/
	private static float deltaTime = 0.0f;
	private static float[] glMat = new float[16];
	private static Vector3 position;

	Matrix4 matrix = new Matrix4();
	SpriteBatch batch;
	/** view and transform matrix for text rendering **/

	/** perspective camera **/
	private PerspectiveCamera camera;

	public RendererGL10 () {
try{
			
			InputStream spherefile = Gdx.files.internal("sphere.obj").read();
			sphere = ObjLoader.loadObj(spherefile);
			spherefile.close();
			

			InputStream planefile = Gdx.files.internal("plane.obj").read();
			plane = ObjLoader.loadObj(planefile);
			planefile.close();

			InputStream cubefile = Gdx.files.internal("cube.obj").read();
			cube = ObjLoader.loadObj(cubefile);
			cubefile.close();

			steel = new Texture(Gdx.files.internal("steel.png"), Format.RGB565, true);
			steel.setFilter(TextureFilter.MipMap, TextureFilter.Linear);
			wood = new Texture(Gdx.files.internal("wood.png"), Format.RGB565, true);
			wood.setFilter(TextureFilter.MipMap, TextureFilter.Linear);
			fire = new Texture(Gdx.files.internal("fire.png"), Format.RGB565, true);
			fire.setFilter(TextureFilter.MipMap, TextureFilter.Linear);
			

		}catch(IOException e){
			e.printStackTrace();
		}
		//Camera initialization
		float aspectRatio =	(float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
			camera = new PerspectiveCamera(67,2f * aspectRatio, 2f);

	}

	
	final Vector3 dir = new Vector3();

	public void cameraSetup(){
		camera.near = 1;
		camera.far = 100;
		camera.position.set(position.x,10,position.y+5);
		camera.direction.set(0,-5,-3);
		camera.update();
		camera.apply(Gdx.gl10);
	}	

	float[] direction = {1, 0.5f, 0, 0};


	public void dispose() {
		sphere.dispose();
		steel.dispose();
		plane.dispose();
		wood.dispose();
		cube.dispose();
		fire.dispose();
	}

	@Override
	public void render(Simulation sim, float delta) {
		// We explicitly require GL10, otherwise we could've used the GLCommon
				// interface via Gdx.gl
GL10 gl = Gdx.graphics.getGL10();
		
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		
		/*batch.setProjectionMatrix(camera.combined);
		batch.setTransformMatrix(matrix);
		batch.begin();
		for(int z = 0; z < 10; z++){
			for(int x = 0; x < 10; x++){
				sprites[x][z].draw(batch);
			}
		}
		batch.end();*/
		
		Gdx.gl.glEnable(GL10.GL_DEPTH_TEST);
		
		cameraSetup();
		
		//Gdx.gl.glEnable(GL10.GL_CULL_FACE);
		Gdx.gl.glEnable(GL10.GL_TEXTURE_2D);
		
		steel.bind();
		gl.glPushMatrix();
		sphere.render(GL10.GL_TRIANGLES);
		gl.glPopMatrix();
		
		wood.bind();
		gl.glPushMatrix();
		gl.glRotatef(45,0,1,0);
		plane.render(GL10.GL_TRIANGLES);
		gl.glPopMatrix();
		
	      /*if ( Gdx.input.isTouched())
	      {
	         camera.rotate(new Vector3(0.0f,1.0f,0.0f),0.2f);
	    	 camera.translate(0.1f,0.2f,0.2f);
	      }*/
	}



}
