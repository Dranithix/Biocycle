package com.dranithix.games.biocycle;

import it.marteEngine.ResourceManager;
import it.marteEngine.World;
import it.marteEngine.actor.StaticActor;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.tiled.TiledMap;

public class MenuWorld extends World {
	public static boolean go = false;
	private SpaceEntity blah;

	public MenuWorld(int id) {
		super(id);
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.enter(container, game);
		ResourceManager.getMusic("background").loop();
		TiledMap map = ResourceManager.getMap("main");

		setWidth(map.getWidth() * 40);
		setHeight(map.getHeight() * 40);
		
		int platforms = map.getLayerIndex("GREAT");
		for (int w = 0; w < map.getWidth(); w++) {
			for (int h = 0; h < map.getHeight(); h++) {
				Image img = map.getTileImage(w, h, platforms);
				if (img != null) {
					int x = w * img.getWidth();
					int y = h * img.getHeight();
					StaticActor platform = new StaticActor(x, y,
							img.getWidth(), img.getHeight(), img);
					platform.addType("tile");
					add(platform);
				}
			}
		}

		add(new Background(0, 0), BELOW);
		add(blah = new SpaceEntity(350, 850, true));
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		super.render(container, game, g);
		if (go) {
			game.enterState(Biocycle.DIAGRAM_WORLD, new FadeOutTransition(), new FadeInTransition());
			go = false;
		}
	}

}
