package com.game.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.handlers.GameStateManager;

public class Game implements ApplicationListener {
	public static final String TAG = "Bunny";
	public static final String TITLE = "Bunny";
	public static final int V_WIDTH = 320;
	public static final int V_HEIGHT = 240;
	public static final int SCALE = 2;
	
	private SpriteBatch batch;
	private OrthographicCamera cam;
	private OrthographicCamera hudcam;
	
	private GameStateManager gsm;
	
	public static final float STEP = 1/60f;
	private float accum;

	@Override
	public void create() {
		batch = new SpriteBatch();
		cam = new OrthographicCamera();
		cam.setToOrtho(false,V_WIDTH,V_HEIGHT);
		
		hudcam = new OrthographicCamera();
		hudcam.setToOrtho(false,V_WIDTH,V_HEIGHT);
		
		gsm = new GameStateManager(this);
	}

	@Override
	public void dispose() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void render() {
		accum += Gdx.graphics.getDeltaTime();
		if(accum >= STEP){
			accum -= STEP;
			gsm.update(STEP);
			gsm.render();
		}
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void resume() {

	}

	public SpriteBatch getSpriteBatch() {
		return batch;
	}

	public OrthographicCamera getCamera() {
		return cam;
	}

	public OrthographicCamera getHudCamera() {
		return hudcam;
	}
}
