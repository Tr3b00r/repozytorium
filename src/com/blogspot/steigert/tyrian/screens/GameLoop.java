package com.blogspot.steigert.tyrian.screens;
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


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.blogspot.steigert.tyrian.services.Renderer;
import com.blogspot.steigert.tyrian.services.RendererGL10;
import com.blogspot.steigert.tyrian.services.Simulation;
import com.blogspot.steigert.tyrian.services.SimulationListener;
import com.projektandroid.Tyrian;

public class GameLoop implements Screen,SimulationListener  {
	/** the simulation **/
	private final Simulation simulation;
	/** the renderer **/
	private final Renderer renderer;
	Tyrian game;

	public GameLoop (Tyrian game) {
		this.game=game;
		simulation = new Simulation();
		renderer =new RendererGL10();

	}

	@Override
	public void dispose () {
		renderer.dispose();
	}


	public void draw (float delta) {
		renderer.render(simulation, delta);
	}

	public void update (float delta) {

	}



	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}


		
	
}
