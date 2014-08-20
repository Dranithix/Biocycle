package com.dranithix.games.biocycle;

import it.marteEngine.ME;
import it.marteEngine.ResourceManager;
import it.marteEngine.entity.PhysicsEntity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class Player extends PhysicsEntity {
	protected static final String CMD_JUMP = "jump";
	protected static final String CMD_SLIDE = "slide";

	public static final int MAX_HEALTH = 100;
	public static boolean MOVEMENT = true;
	private boolean movable = true;
	private boolean onGround = false;

	public int health = MAX_HEALTH;

	private int jumpSpeed = 6;

	public Player(float x, float y) throws SlickException {
		super(x, y);
		duration = 50;
		addAnimation(ResourceManager.getSpriteSheet("toxicWalk"), "toxicWalk",
				true, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14);
		addAnimation(ResourceManager.getSpriteSheet("toxicSlide"),
				"toxicSlide", true, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
				12, 13, 14);

		clearTypes();
		addType(PLAYER);
		name = PLAYER;
		depth = 10;
		maxSpeed = new Vector2f(2f, 6);
		this.setHitBox(0, 0, 75, 125);

		define(CMD_JUMP, Input.KEY_UP, Input.KEY_W);
		define(CMD_SLIDE, Input.KEY_DOWN, Input.KEY_S);
		setAnim("toxicWalk");
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		super.render(container, g);
	}

	@Override
	public void leftWorldBoundaries() {
		if (y > 0) {
			try {
				((GameWorld) world).game.enterState(Biocycle.STORY_WORLD,
						new FadeOutTransition(), new FadeInTransition());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public void damage() {
		health -= 25;
		if (health > 0) {
			jump();
		} else {
			removePlayer();
		}
	}

	private void removePlayer() {
		ResourceManager.getSound("explode").play();
		ME.world.remove(this);
		health = 0;
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		super.update(container, delta);
		onGround = false;
		if (collide(SOLID, x, y + 1) != null) {
			onGround = true;
		}
		if (collide(SOLID, x + 1, y) != null) {
			damage();
		}
		Toxic c = (Toxic) collide(Toxic.TOXIC, x + 1, y);
		if (c != null) {
			world.remove(c);
		}
		this.acceleration.x = 1f;
		if (pressed(CMD_JUMP) && movable) {
			// normal jump
			if (onGround) {
				setAnim("toxicWalk");

				ResourceManager.getSound("jump").play();
				this.setHitBox(0, 0, 75, 125);
				jump();
			}
		}
		if (pressed(CMD_SLIDE) && movable) {
			if (onGround) {
				setAnim("toxicSlide");
				setHitBox(0, 50, 125, 75);
			}
		}
		gravity(delta);
		maxspeed(true, true);
		if (speed.y < 0 && !check(CMD_JUMP)) {
			gravity(delta);
			gravity(delta);
		}
		motion(true, true);

		if (collide(Spike.SPIKE, x, y + 1) != null) {
			damage();
		}
	}

	public void jump() {
		speed.y = -jumpSpeed;
	}
}
