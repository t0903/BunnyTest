package com.game.handlers;

import java.util.Stack;

import com.game.main.Game;
import com.game.states.GameState;
import com.game.states.Play;

public class GameStateManager {
	public static final int PLAY = 1;
	
	private Game game;
	
	private Stack<GameState> gameStates;
	
	public GameStateManager(Game game){
		this.game = game;
		gameStates = new Stack<>();
		pushState(PLAY);
	}
	
	public Game game(){
		return game;
	}
	
	public void update(float dt){
		gameStates.peek().update(dt);
	}
	
	public void render(){
		gameStates.peek().render();
	}
	
	private GameState getState(int state){
		if(state == PLAY){
			return new Play(this);
		}
		return null;
	}
	
	public void setState(int state){
		popState();
		pushState(state);
	}
	
	public void pushState(int state){
		gameStates.push(getState(state));
	}
	
	public void popState(){
		GameState gameState = gameStates.pop();
		gameState.dispose();
	}
}
