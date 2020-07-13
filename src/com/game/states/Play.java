package com.game.states;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.game.handlers.GameStateManager;

public class Play extends GameState{
	private BitmapFont font = new BitmapFont();
	
	public Play(GameStateManager gsm){
		super(gsm);
	}

	@Override
	public void handleInput() {
		
	}

	@Override
	public void update(float dt) {
		
	}

	@Override
	public void render() {
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		font.draw(batch, "hello", 100, 100);
		batch.end();
	}

	@Override
	public void dispose() {
		
	}
}
