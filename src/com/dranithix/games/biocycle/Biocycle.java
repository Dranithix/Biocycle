package com.dranithix.games.biocycle;

import it.marteEngine.ResourceManager;

import java.io.IOException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

public class Biocycle extends StateBasedGame {
	public static final int MENU_WORLD = 0;
	public static final int DIAGRAM_WORLD = 1;
	public static final int STORY_WORLD = 2;
	public static final int GAME_WORLD = 3;
	private static boolean resourceInit;

	public Biocycle(String name) {
		super(name);
	}

	public static void main(String[] args) {
		try {
			AppGameContainer container = new AppGameContainer(new Biocycle(
					"Biocycle"));
			container.setDisplayMode(800, 600, false);
			container.setTargetFrameRate(60);
			container.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		initResources();

		addState(new MenuWorld(MENU_WORLD));
		addState(new DiagramWorld(DIAGRAM_WORLD));
		addState(new StoryWorld(STORY_WORLD));
		addState(new GameWorld(GAME_WORLD));
	}

	public static void initResources() throws SlickException {
		if (resourceInit)
			return;
		try {
			ResourceManager.loadResources("data/resources.xml");
		} catch (IOException e) {
			Log.error("failed to load ressource file 'data/resources.xml': "
					+ e.getMessage());
			throw new SlickException("Resource loading failed!");
		}

		resourceInit = true;
	}
}
