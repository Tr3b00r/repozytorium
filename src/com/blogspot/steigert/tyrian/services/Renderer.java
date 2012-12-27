
package com.blogspot.steigert.tyrian.services;

import com.blogspot.steigert.tyrian.services.Simulation;

public interface Renderer {
	public void render (Simulation sim, float delta);

	public void dispose ();
}
