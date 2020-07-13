package com.game.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.handlers.GameStateManager;
import com.game.main.Game;

public abstract class GameState {
	protected GameStateManager gsm;
	protected Game game;
	protected SpriteBatch batch;
	protected OrthographicCamera cam;
	protected OrthographicCamera hudcam;
	
	public GameState(GameStateManager gsm){
		this.gsm = gsm;
		this.game = gsm.game();
		this.batch = game.getSpriteBatch();
		this.cam = game.getCamera();
		this.hudcam = game.getHudCamera();
	}
	
	public abstract void handleInput();
	public abstract void update(float dt);
	public abstract void render();
	public abstract void dispose();
}
