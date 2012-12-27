package com.blogspot.steigert.tyrian.screens;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ActorEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.projektandroid.Tyrian;
import com.blogspot.steigert.tyrian.services.SoundManager.TyrianSound;
import com.blogspot.steigert.tyrian.utils.DefaultActorListener;

public class MenuScreen
    implements
        Screen 
{Tyrian game;
Stage stage;
private Skin skin;
private Table table;
public static final int GAME_VIEWPORT_WIDTH = 140, GAME_VIEWPORT_HEIGHT = 400;
public static final int MENU_VIEWPORT_WIDTH = 300, MENU_VIEWPORT_HEIGHT = 300;
    public MenuScreen(
        Tyrian game )
    {
        this.game=game;
        int width = ( isGameScreen() ? GAME_VIEWPORT_WIDTH : MENU_VIEWPORT_WIDTH );
        int height = ( isGameScreen() ? GAME_VIEWPORT_HEIGHT : MENU_VIEWPORT_HEIGHT );
        this.stage = new Stage( width, height, true );
    }
    protected boolean isGameScreen()
    {
        return false;
    }
    protected Skin getSkin()
    {
        if( skin == null ) {
            FileHandle skinFile = Gdx.files.internal( "skin/uiskin.json" );
            skin = new Skin( skinFile );
        }
        return skin;
    }

    @Override
    public void show()
    {
    	stage=new Stage();
    	Gdx.input.setInputProcessor(stage);
        // retrieve the default table actor
    	table = new Table( getSkin() );

		TextButton startGameButton = new TextButton("Nowa gra", getSkin());
		TextButton optionsButton = new TextButton("Opcje", getSkin());
		TextButton continueButton = new TextButton("Kontynuuj", getSkin());
		TextButton saveButton = new TextButton("Zapisz", getSkin());
		TextButton loadButton = new TextButton("Wczytaj", getSkin());
		TextButton exitButton = new TextButton("Exit", getSkin());

		Texture backgroundTexture = new Texture(Gdx.files.internal("kulki1.jpg"));
		//Texture backgroundTexture1 = new Texture(Gdx.files.internal("White Button1.jpg"));
		Image backImage = new Image(backgroundTexture);
		//FileHandle skinFile1 = Gdx.files.internal( "White Button1.jpg" );
		//startGameButton.setBackground(@drawable/White Button1);
		//startGameButton.
		startGameButton.addListener( new DefaultActorListener() {
            @Override
            public void touchUp(
                ActorEvent event,
                float x,
                float y,
                int pointer,
                int button )
            {
                super.touchUp( event, x, y, pointer, button );
                game.getSoundManager().play( TyrianSound.CLICK );
                game.setScreen( new StartGameScreen( game ) );
            }
        } );
		optionsButton.addListener( new DefaultActorListener() {
            @Override
            public void touchUp(
                ActorEvent event,
                float x,
                float y,
                int pointer,
                int button )
            {
                super.touchUp( event, x, y, pointer, button );
                game.getSoundManager().play( TyrianSound.CLICK );
                try {
					game.setScreen( new LevelScreen( game ) );
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        } );		
		exitButton.addListener( new DefaultActorListener() {
            @Override
            public void touchUp(
                ActorEvent event,
                float x,
                float y,
                int pointer,
                int button )
            {
                super.touchUp( event, x, y, pointer, button );
                System.exit(0);
            }
        } );
	
		table.setFillParent(true);
//		table.debug(); 
		table.add( "Witaj w KulkoWdziurce!" ).colspan( 3 );
		table.row();
		table.add(startGameButton).width(300).height(50);
		table.row();
		table.add(continueButton).width(300).height(50).padTop(10);
		table.row();
		table.add(optionsButton).width(300).height(50).padTop(10);
		table.row();
		table.add(saveButton).width(300).height(50).padTop(10);
		table.row();
		table.add(loadButton).width(300).height(50).padTop(10);
		table.row();
		table.add(exitButton).width(300).height(50).padTop(10);
		
		//stage.addActor(backImage);
		
		stage.addActor(backImage);
		stage.addActor(table);
    }
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		// (1) process the game logic

        // update the actors
    	
        stage.act( delta );
        
        // (2) draw the result

        // clear the screen with the given RGB color (black)
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );

        // draw the actors
        stage.draw();

        // draw the table debug lines
        //Table.drawDebug( stage );
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		stage.setViewport( width, height, true );
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}
