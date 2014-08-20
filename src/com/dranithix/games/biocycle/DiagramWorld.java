package com.dranithix.games.biocycle;

import it.marteEngine.ResourceManager;
import it.marteEngine.World;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class DiagramWorld extends World {
	long timer;
	Image diagram;

	public DiagramWorld(int id) {
		super(id);
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.enter(container, game);
		timer = System.currentTimeMillis();
		diagram = ResourceManager.getImage("diagram");

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		super.render(container, game, g);
		if (diagram != null)
			g.drawImage(diagram, 0, 0);
		ResourceManager.getFont("font").drawString(350, 250, Long.toString(System.currentTimeMillis() - timer));
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		super.update(container, game, delta);
		if (System.currentTimeMillis() - timer >= 15000) {
			game.enterState(Biocycle.STORY_WORLD, new FadeOutTransition(),
					new FadeInTransition());
		}
	}

}
