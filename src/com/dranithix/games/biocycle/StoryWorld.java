package com.dranithix.games.biocycle;

import it.marteEngine.ME;
import it.marteEngine.World;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class StoryWorld extends World {
	private String text;

	public StoryWorld(int id) {
		super(id);
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.enter(container, game);
		((GameWorld) game.getState(Biocycle.GAME_WORLD)).clear();

		switch (((GameWorld) game.getState(Biocycle.GAME_WORLD)).levelIndex) {
		case 0:
			text = "Welcome fellow controller, I am Bio-CYCLE originating from\n"
					+ "the Canadian company SPAR Aerospace Ltd. My creators have\n"
					+ "sent me to you in order to wipe out the toxins spreading\n"
					+ "around the Earth, and bring homosapians like you into\n"
					+ "proper shelter. As you can see from my wide array of\n"
					+ "components, you may control my arm, specifically the Canadarm\n"
					+ "from 1981, and my body in order to carefully dispose of toxins\n"
					+ "around Earth through my built-in incinerator and rescue a few\n"
					+ "homosapians from this diminishing planet.\n\n"
					+ "Let's get started now, shall we?";
			break;
		case 1:
			text = "Alright that was a great start to controlling me, now lets\n"
					+ "head into the actual toxin danger zone. Head in and\n"
					+ "check out the toxic pools erupting, my creator has\n"
					+ "required us to collect some photos from this zone that\n"
					+ "may lead to a scientific discovery of how these toxins\n"
					+ "came to Earth, and if these toxins actually originated\n"
					+ "from the Big Bang like the other scientists have said.";
			break;
		case 2:
			text = "Great job, I sent over the photos from the danger zone to\n"
					+ "my creator. Now then, theres been issues with broken satellite\n"
					+ "signals due to the radiation from these toxins. Lets go clean\n"
					+ "the toxins over there and clear up the satellite signals over\n"
					+ "that area.";
			break;
		case 3:
			text = "Well then the satellites seem to be up and running again,\n"
					+ "and most of the Earth seems to be cleaned up now. Well,\n"
					+ "my creator has sent us a mission where we have to guide\n"
					+ "a couple of Canadian astronauts into space for research on\n"
					+ "possible new lifeforms.\n\nGo grab the controls and let's get a move on!";
			break;
		case 4:
			text = "Things seem to be going s'well now and your controlling skills\n"
					+ "are magnificent fellow controller! However I just received news\n"
					+ "that we shall do one more final task and you will revoke rights\n"
					+ "of controlling me sadly. Here is the task:\n\n"
					+ "\"Bring building materials to the Hubble Space Telescope.\"";
			break;
		case 5:
			text = "THE END - SNC1D Kenta Iwasaki";
		}

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		super.render(container, game, g);
		if (text != null)
			g.drawString(text,
					container.getWidth() / 2 - g.getFont().getWidth(text) / 2,
					container.getHeight() / 2 - g.getFont().getHeight(text) / 2);
		g.drawString("Press [SPACE] to continue", container.getWidth() / 2
				- g.getFont().getWidth("Press [SPACE] to continue") / 2,
				container.getHeight() - 100);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		super.update(container, game, delta);
		game.getState(Biocycle.GAME_WORLD).update(container, game, delta);

		if (container.getInput().isKeyPressed(Input.KEY_SPACE)
				&& ME.world == this) {
			if (((GameWorld) game.getState(Biocycle.GAME_WORLD)).levelIndex != 5) {
				game.enterState(Biocycle.GAME_WORLD, new FadeOutTransition(),
						new FadeInTransition());
			} else {
				game.enterState(Biocycle.MENU_WORLD, new FadeOutTransition(), new FadeInTransition());
			}

		}
	}
}
