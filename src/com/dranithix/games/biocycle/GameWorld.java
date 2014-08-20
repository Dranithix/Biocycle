package com.dranithix.games.biocycle;

import it.marteEngine.Camera;
import it.marteEngine.ResourceManager;
import it.marteEngine.World;
import it.marteEngine.actor.StaticActor;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class GameWorld extends World {
	public int levelIndex = 0;
	private Image hudBase;
	private Image healthBar;

	private Player player;

	public StateBasedGame game;

	public GameWorld(int id) {
		super(id);
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.init(container, game);
		this.game = game;
		hudBase = ResourceManager.getImage("hudBase");
		healthBar = ResourceManager.getImage("healthBar");
	}

	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.enter(container, game);
		nextLevel(true);
	}

	public void nextLevel(boolean increaseLevel) throws SlickException {
		if (increaseLevel)
			levelIndex += 1;

		TiledMap map = ResourceManager.getMap("level" + levelIndex);

		setWidth(map.getWidth() * 40);
		setHeight(map.getHeight() * 40);

		loadEntities(map);
		player = (Player) loadPlayer(map);

		setCamera(new Camera(this, player, container.getWidth(),
				container.getHeight(), 550, 170, player.maxSpeed));

		add(new Background(0, 0), BELOW);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		g.clear();
		super.render(container, game, g);
		g.drawImage(hudBase, 10, 10);
		if (player != null)
			healthBar.draw(77, 39, (217 / Player.MAX_HEALTH) * player.health,
					healthBar.getHeight());
		if (player != null && player.health <= 0) {
			String gameOver = "GAME OVER! PRESS R TO RESTART!";
			g.setColor(new Color(0, 0, 0, 0.5f));
			g.fillRect(0, 0, container.getWidth(), container.getHeight());
			g.setColor(Color.white);
			g.drawString(gameOver, container.getWidth() / 2
					- g.getFont().getWidth(gameOver) / 2, container.getHeight()
					/ 2 - g.getFont().getHeight(gameOver) / 2);
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		super.update(container, game, delta);
		if (camera != null)
			camera.cameraX += 5;
		if (player != null && player.health <= 0
				&& container.getInput().isKeyPressed(Input.KEY_R)) {
			clear();
			nextLevel(false);
		}
	}

	private Player loadPlayer(TiledMap map) throws SlickException {
		int layerIndex = map.getLayerIndex("PLAYER");
		for (int w = 0; w < map.getWidth(); w++) {
			for (int h = 0; h < map.getHeight(); h++) {
				Image img = map.getTileImage(w, h, layerIndex);
				if (img != null) {
					int x = w * img.getWidth();
					int y = h * img.getHeight();
					Player player = new Player(x, y);
					add(player);
					return player;
				}
			}
		}
		return null;
	}

	private void loadEntities(TiledMap map) throws SlickException {
		int platforms = map.getLayerIndex("PLATFORMS");
		int spikes = map.getLayerIndex("SPIKES");
		int toxics = map.getLayerIndex("TOXIC");
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
				Image spike = map.getTileImage(w, h, spikes);
				if (spike != null) {
					int x = w * spike.getWidth();
					int y = h * spike.getHeight();
					add(new Spike(x, y, spike));
				}
				Image toxic = map.getTileImage(w, h, toxics);
				if (toxic != null) {
					int x = w * toxic.getWidth();
					int y = h * toxic.getHeight();
					add(new Toxic(x, y));
				}
			}
		}
	}
}
