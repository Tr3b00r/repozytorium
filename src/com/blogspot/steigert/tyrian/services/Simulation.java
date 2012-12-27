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


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Vector3;

public class Simulation {
	
	private static float spherex = 0.0f, spherey = 0.0f, spherez = 0.0f, deltaTime = 0.0f, startTime = 0.0f, endTime = 0.0f;
	private static Vector3 position;
	
	org.siprop.bullet.util.Vector3 origin =  new org.siprop.bullet.util.Vector3(0.0f, 0.0f, 0.0f);
	org.siprop.bullet.util.Vector3 idSize =  new org.siprop.bullet.util.Vector3(2.0f, 2.0f, 2.0f);
	public Simulation () {

	}

	

	public void update (float delta) {
	}


	public void sphereMovement(GL10 gl){
		spherex = Gdx.input.getAccelerometerX();
		spherey = Gdx.input.getAccelerometerY();
		spherez = Gdx.input.getAccelerometerZ();
		position.add(-spherex/10,spherey/10,0);
		gl.glTranslatef(position.x,0.0f,position.y);
		gl.glRotatef(45 * (position.y / 5), 1, 0, 0);
		gl.glRotatef(-45 * (position.x / 2), 0, 0, 1);
	}


}
