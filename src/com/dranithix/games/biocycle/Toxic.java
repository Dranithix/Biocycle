package com.dranithix.games.biocycle;

import it.marteEngine.ResourceManager;
import it.marteEngine.entity.Entity;

public class Toxic extends Entity {

	public static final String TOXIC = "toxic";

	public Toxic(float x, float y) {
		super(x, y);
		duration = 50;
		addType(TOXIC);
		addAnimation(ResourceManager.getSpriteSheet("toxic"), "toxic", true, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14);
		setAnim("toxic");
		setHitBox(0, 0, 32, 32);
		
	}

}