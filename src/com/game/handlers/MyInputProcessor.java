package com.game.handlers;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

public class MyInputProcessor extends InputAdapter{
	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.Z){
			MyInput.setKey(MyInput.BUTTON1,true);
		}
		if(keycode == Keys.X){
			MyInput.setKey(MyInput.BUTTON2,true);
		}
		return true;
	}
	
	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Keys.Z){
			MyInput.setKey(MyInput.BUTTON1,false);
		}
		if(keycode == Keys.X){
			MyInput.setKey(MyInput.BUTTON2,false);
		}
		return true;
	}
}
