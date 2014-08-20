package com.dranithix.games.biocycle;

import it.marteEngine.actor.StaticActor;

import org.newdawn.slick.Image;

public class Spike extends StaticActor {

	public static final String SPIKE = "spike";

	public Spike(float x, float y, Image img) {
		super(x, y, img.getWidth(), img.getHeight(), img);
		addType(SPIKE);
	}

}
