package com.coinbattle.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.*;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Random;

public class UltimateBattle extends Game {
	SpriteBatch batch;
	public int V_WIDTH = 720,V_HEIGHT = 1280,gameOverTimer = 0;
	boolean bombHit,grenadeHit,missileHit;
	Rectangle collisonRectange;
	long score = 0,moneyCollected = 0;
	BitmapFont showLives,showScore,showMoneyCollected;
	Random random;
	com.badlogic.gdx.math.Rectangle manR;
	Texture man[],coin,coinxl,money,grenade,bomb,missile,background,dizzy;
	ArrayList<Rectangle> coinR,coinxlR,moneyR,grenadeR,bombR,missileR;
	ArrayList<Integer> coinX,coinY,coinxlX,coinxlY,moneyX,moneyY,grenadeX,grenadeY,bombX,bombY,missileX,missileY;
	int ms = 0,loop = 0,gameState = 0;float gravity = 0.5f,velocity = 0, manY,coinCount = 0,coinxlCount = 0,moneyCount = 0,grenadeCount = 0,bombCount = 0,missileCount = 0,lives = 5,pos = -1;


	@Override
	public void create () {
		batch = new SpriteBatch();manY = (float) (Gdx.graphics.getHeight() / 2.75);random = new Random();
		man = new Texture[4];
		background = new Texture("bg.png");
		man[0] = new Texture("frame1.png");man[1] = new Texture("frame2.png");man[2] = new Texture("frame3.png");man[3] = new Texture("frame4.png");
		dizzy = new Texture("dizzy1.png");
		//Initilaizing coins and bombs texture
		coin = new Texture("coin.png");coinxl = new Texture("coinxl.png");money = new Texture("money.png");
		grenade = new Texture("grenade.png");bomb = new Texture("bomb.png");missile = new Texture("missile.png");

		//Initiliazing ArrayList of width and height of each component
		coinX = new ArrayList<>();coinY = new ArrayList<>();coinxlX = new ArrayList<>();coinxlY = new ArrayList<>();moneyX = new ArrayList<>();moneyY = new ArrayList<>();
		grenadeX = new ArrayList<>();grenadeY = new ArrayList<>();bombX = new ArrayList<>();bombY = new ArrayList<>();missileX = new ArrayList<>();missileY = new ArrayList<>();

		//Initiliazing ArrayList of Rectangle
		coinR = new ArrayList<>();coinxlR = new ArrayList<>();moneyR = new ArrayList<>();grenadeR = new ArrayList<>();bombR = new ArrayList<>();missileR = new ArrayList<>();
		//Setting up Fonts
		showLives = new BitmapFont();showLives.setColor(Color.RED);showLives.getData().setScale(3,3);
		showScore = new BitmapFont();showScore.setColor(Color.WHITE);showScore.getData().setScale(3,3);
		showMoneyCollected = new BitmapFont();showMoneyCollected.setColor(Color.WHITE);showMoneyCollected.getData().setScale(3,3);


	}

	public void makeCoin()
	{
		float height = random.nextFloat() * Gdx.graphics.getHeight();
		coinY.add((int)height);coinX.add((int)Gdx.graphics.getWidth());
	}
	public void makeCoinxl()
	{
		float height = random.nextFloat() * Gdx.graphics.getHeight();
		coinxlY.add((int)height);coinxlX.add((int)Gdx.graphics.getWidth());
	}public void makeMoney()
	{
		float height = random.nextFloat() * Gdx.graphics.getHeight();
		moneyY.add((int)height);moneyX.add((int)Gdx.graphics.getWidth());
	}public void makeGrenade()
	{
		float height = random.nextFloat() * Gdx.graphics.getHeight();
		grenadeY.add((int)height);grenadeX.add((int)Gdx.graphics.getWidth());
	}public void makeBomb()
	{
		float height = random.nextFloat() * Gdx.graphics.getHeight();
		bombY.add((int)height);bombX.add((int)Gdx.graphics.getWidth());
	}public void makeMissile()
	{
		float height = random.nextFloat() * Gdx.graphics.getHeight();
		missileY.add((int)height);missileX.add((int)Gdx.graphics.getWidth());
	}
	@Override
	public void render () {
		batch.begin();
		batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		if(gameState == 0)
		{
			if(Gdx.input.isTouched())
				gameState = 1;
			batch.draw(man[1],Gdx.graphics.getWidth() / 2 - man[1].getWidth() / 2,(int)manY);
			coinR.clear();coinX.clear();coinY.clear(); coinxlR.clear();coinxlY.clear();coinxlX.clear(); moneyR.clear();moneyX.clear();moneyY.clear();
			grenadeX.clear();grenadeY.clear();grenadeR.clear(); bombR.clear();bombX.clear();bombY.clear(); moneyY.clear();moneyX.clear();moneyR.clear();
			grenadeHit = false;missileHit = false;bombHit = false;pos = -1;
		}
		else if(gameState == 1)
		{
			score++;
			//Adding Coins
			if(coinCount < 150)
				coinCount++;
			else
			{
				makeCoin();
				coinCount = 0;
			}
			coinR.clear();
			for(int x = 0;x < coinX.size();x++)
			{
				batch.draw(coin,coinX.get(x),coinY.get(x));
				coinX.set(x,coinX.get(x) - 4);
				coinR.add(new Rectangle(coinX.get(x),coinY.get(x),coin.getWidth(),coin.getHeight()));
			}

			// Adding CoinXl
			if(coinxlCount < 300)
				coinxlCount++;
			else
			{
				makeCoinxl();
				coinxlCount = 0;
			}
			coinxlR.clear();
			for(int x = 0;x < coinxlX.size();x++)
			{
				batch.draw(coinxl,coinxlX.get(x),coinxlY.get(x));
				coinxlX.set(x,coinxlX.get(x) - 6);
				coinxlR.add(new Rectangle(coinxlX.get(x),coinxlY.get(x),coinxl.getWidth(),coinxl.getHeight()));
			}

			// Adding Money
			if(moneyCount < 1000)
				moneyCount++;
			else
			{
				makeMoney();
				moneyCount = 0;
			}
			moneyR.clear();
			for(int x = 0;x < moneyX.size();x++)
			{
				batch.draw(money,moneyX.get(x),moneyY.get(x));
				moneyX.set(x,moneyX.get(x) - 8);
				moneyR.add(new Rectangle(moneyX.get(x),moneyY.get(x),money.getWidth(),money.getHeight()));
			}

			//Adding Grenade
			if(grenadeCount < 350)
				grenadeCount++;
			else
			{
				makeGrenade();
				grenadeCount = 0;
			}
			grenadeR.clear();
			for(int x = 0;x < grenadeX.size();x++)
			{
				batch.draw(grenade,grenadeX.get(x),grenadeY.get(x));
				grenadeX.set(x,grenadeX.get(x) - 4);
				grenadeR.add(new Rectangle(grenadeX.get(x),grenadeY.get(x),grenade.getWidth(),grenade.getHeight()));
			}

			//Adding Bomb
			if(bombCount < 600)
				bombCount++;
			else
			{
				makeBomb();
				bombCount = 0;
			}
			bombR.clear();
			for(int x = 0;x < bombX.size();x++)
			{
				batch.draw(bomb,bombX.get(x),bombY.get(x));
				bombX.set(x,bombX.get(x) - 8);
				bombR.add(new Rectangle(bombX.get(x),bombY.get(x),bomb.getWidth(),bomb.getHeight()));
			}

			//Adding Missile
			if(missileCount < 1000)
				missileCount++;
			else
			{
				makeMissile();
				missileCount = 0;
			}
			missileR.clear();
			for(int x = 0;x < missileX.size();x++)
			{
				batch.draw(missile,missileX.get(x),missileY.get(x));
				missileX.set(x,missileX.get(x) - 16);
				missileR.add(new Rectangle(missileX.get(x),missileY.get(x),missile.getWidth(),missile.getHeight()));
			}

			//Adding Man

			if(Gdx.input.isTouched())
				velocity = -12;

			velocity += gravity;
			manY -= velocity;


			if(loop <= 5)
				loop++;
			else
			{
				loop = 0;
				ms++;
				if(ms > 3)
					ms = 0;
			}
			if(manY > (float)(Gdx.graphics.getHeight())/1.5)
				manY = (float)(Gdx.graphics.getHeight()/1.5);
			if(manY <= 0)
			{
				manY = 0;
				velocity = 0;
			}
			batch.draw(man[ms],Gdx.graphics.getWidth() / 2 - man[ms].getWidth() ,(int)manY);
			manR = new Rectangle(Gdx.graphics.getWidth() / 2 - man[ms].getWidth(),(int)manY,man[ms].getWidth(),man[ms].getHeight());


			//Coin Collission
			for(int x = 0;x < coinR.size();x++)
			{
				if(Intersector.overlaps(manR,coinR.get(x)))
				{
					Gdx.app.log("rock","Coin Collision!");
					coinX.remove(x);coinY.remove(x);coinR.remove(x);
					moneyCollected++;
					break;
				}

			}

			//CoinXl Collision
			for(int x = 0;x < coinxlR.size();x++)
			{
				if(Intersector.overlaps(manR,coinxlR.get(x)))
				{
					Gdx.app.log("rock","Coinxl Collision!");
					coinxlX.remove(x);coinxlY.remove(x);coinxlR.remove(x);
					moneyCollected += 5;
					break;
				}

			}

			//Money Collission
			for(int x = 0;x < moneyR.size();x++)
			{
				if(Intersector.overlaps(manR,moneyR.get(x)))
				{
					Gdx.app.log("rock","money Collision!");
					moneyX.remove(x);moneyY.remove(x);moneyR.remove(x);
					moneyCollected += 10;
					break;
				}

			}

			//Grenade Collission
			for(int x = 0;x < grenadeR.size();x++)
			{
				if(Intersector.overlaps(manR,grenadeR.get(x)))
				{
					Gdx.app.log("rock","grenade Collision!");

					lives--;
					if(!checkLives()){
						gameState = 2;
						pos = x;
						collisonRectange = grenadeR.get(x);
						grenadeHit = true;
					}
					else
						grenadeX.remove(x);grenadeY.remove(x);grenadeR.remove(x);
					break;
				}

			}

			//Bomb Collsison
			for(int x = 0;x < bombR.size();x++)
			{
				if(Intersector.overlaps(manR,bombR.get(x)))
				{
					Gdx.app.log("rock","bomb Collision!");

					lives -= 2;
					if(!checkLives()){
						gameState = 2;
						bombHit = true;
						collisonRectange = bombR.get(x);
						pos = x;
					}
					else
						bombX.remove(x);bombY.remove(x);bombR.remove(x);
					break;
				}

			}

			//Missile Collssion
			for(int x = 0;x < missileR.size();x++)
			{
				if(Intersector.overlaps(manR,missileR.get(x)))
				{
					Gdx.app.log("rock","missile Collision!");
					//missileX.remove(x);missileY.remove(x);missileR.remove(x);
					lives = 0;
					missileHit = true;
					collisonRectange = missileR.get(x);
					gameState = 2;
					pos = x;
					break;
				}

			}

		}
		else if(gameState == 2)
		{
			batch.draw(dizzy,Gdx.graphics.getWidth() / 2 - man[ms].getWidth(),(int)manY);
			Gdx.app.log("rock","Yout Monneu Cilectec " + moneyCollected);
			Gdx.app.log("rock","Yout score is " + score);
			if(bombHit)
				batch.draw(bomb,collisonRectange.getX(),collisonRectange.getY());
			else if(grenadeHit)
				batch.draw(grenade,collisonRectange.getX(),collisonRectange.getY());
			if(missileHit)
				batch.draw(missile,collisonRectange.getX(),collisonRectange.getY());

			this.setScreen(new GameOverScreen(this));

		}
		if(gameState != 2){
		showLives.draw(batch,"Lives:" + (int)lives,30,100);
		showMoneyCollected.draw(batch,"Money: $" + moneyCollected,30,50);
		int h = (int)(Gdx.graphics.getWidth()/1.5);
		showScore.draw(batch,"Score: " + score, 30,(Gdx.graphics.getHeight()));}
		batch.end();
	}

	public boolean checkLives()
	{
		if(lives < 0)
			return false;
		return true;

	}




	@Override
	public void dispose () {
		batch.dispose();
	}
}
