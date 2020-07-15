package com.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.game.handlers.GameStateManager;
import com.game.handlers.MyContactListener;
import com.game.handlers.MyInput;
import com.game.main.Game;

import static com.game.handlers.B2DVars.*;

public class Play extends GameState{
	private World world;
	private Box2DDebugRenderer b2dr;
	
	private OrthographicCamera b2dCam;
	
	private MyContactListener cl;
	
	private TiledMap map;
	private OrthogonalTiledMapRenderer render;
	private float tileSize;
	
	private Body playerBody;
	
	public Play(GameStateManager gsm){
		super(gsm);
		
		world = new World(new Vector2(0,-9.81f), true);
		b2dr = new Box2DDebugRenderer();
		cl = new MyContactListener();
		
		world.setContactListener(cl);
		
		//创建地板
		BodyDef bdef = new BodyDef();
		bdef.position.set(160/PPM,120/PPM);
		bdef.type = BodyType.StaticBody;
		playerBody = world.createBody(bdef);
	
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(50/PPM, 5/PPM);
		FixtureDef fdef = new FixtureDef();
		//fdef.shape = shape;
		//fdef.filter.categoryBits = BIT_RED;
		//fdef.filter.maskBits = BIT_PLAYER;
		//body.createFixture(fdef);
		
		//创建下落物体
		bdef.position.set(160/PPM,200/PPM);
		bdef.type = BodyType.DynamicBody;
		playerBody = world.createBody(bdef);
		shape.setAsBox(5/PPM, 5/PPM);
		fdef.shape = shape;
		//fdef.restitution = 0.5f;//反弹力
		fdef.filter.categoryBits = BIT_PLAYER;
		fdef.filter.maskBits = BIT_RED;
		playerBody.createFixture(fdef);
		//创建传感器 foot
		shape.setAsBox(2/PPM, 2/PPM,new Vector2(0, -5/PPM),0);
		fdef.shape = shape;
		fdef.filter.categoryBits = BIT_PLAYER;
		fdef.filter.maskBits = BIT_RED;
		fdef.isSensor = true;
		playerBody.createFixture(fdef).setUserData("foot");
		
		b2dCam = new OrthographicCamera();
		b2dCam.setToOrtho(false, Game.V_WIDTH/PPM, Game.V_HEIGHT/PPM);
		
		//////////////
		map = new TmxMapLoader().load("res/maps/level1.tmx");
		render = new OrthogonalTiledMapRenderer(map);
		
		TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get("red");
		tileSize = layer.getTileWidth();
		
		for(int row=0;row<layer.getHeight();row++){
			for(int col=0;col<layer.getWidth();col++){
				Cell cell = layer.getCell(col, row);
				if(cell == null || cell.getTile() == null) continue;
				
				bdef.type = BodyType.StaticBody;
				bdef.position.set(
						(col + 0.5f) * tileSize / PPM,
						(row + 0.5f) * tileSize / PPM
						);
				
				ChainShape cs = new ChainShape();
				Vector2 v[] = new Vector2[3];
				v[0] = new Vector2(-tileSize/2/PPM,-tileSize/2/PPM);
				v[1] = new Vector2(-tileSize/2/PPM,tileSize/2/PPM);
				v[2] = new Vector2(tileSize/2/PPM,tileSize/2/PPM);
				cs.createChain(v);
				
				fdef.friction = 0;
				fdef.shape = cs;
				fdef.filter.categoryBits = BIT_RED;
				fdef.filter.maskBits = BIT_PLAYER;
				fdef.isSensor = false;
				world.createBody(bdef).createFixture(fdef);
			}
		}
	}

	@Override
	public void handleInput() {
		if(MyInput.isPressed(MyInput.BUTTON1)){
			if(cl.isPlayerOnGround()){
				playerBody.applyForceToCenter(0, 200, true);
			}
		}
	}

	@Override
	public void update(float dt) {
		Gdx.gl10.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		handleInput();
		
		world.step(dt, 6, 2);
	}

	@Override
	public void render() {
		render.setView(cam);
		render.render();
		
		b2dr.render(world, b2dCam.combined);
	}

	@Override
	public void dispose() {
		
	}
}
