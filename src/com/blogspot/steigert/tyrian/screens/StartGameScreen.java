package com.blogspot.steigert.tyrian.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.scenes.scene2d.ActorEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.projektandroid.Tyrian;

import com.blogspot.steigert.tyrian.domain.Shield;

import com.blogspot.steigert.tyrian.domain.ShipModel;
import com.blogspot.steigert.tyrian.services.MusicManager.TyrianMusic;
import com.blogspot.steigert.tyrian.services.SoundManager.TyrianSound;
import com.blogspot.steigert.tyrian.utils.DefaultActorListener;

public class StartGameScreen  
implements
Screen 
{Tyrian game;
Stage stage;
private Skin skin;
private TextButton episode1Button;
private TextButton episode2Button;
private TextButton episode3Button;
private TextButton episode4Button;
private TextButton episode5Button;
private Table table;
private AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration(); 
private SelectBox frontGunSelectBox;
private SelectBox shipModelSelectBox;
public static final int GAME_VIEWPORT_WIDTH = 140, GAME_VIEWPORT_HEIGHT = 400;
public static final int MENU_VIEWPORT_WIDTH = 300, MENU_VIEWPORT_HEIGHT = 300;
public StartGameScreen(
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
game.getMusicManager().play( TyrianMusic.MENU );
table.defaults().spaceBottom( 10 );
//table.columnDefaults( 0 ).padRight( 10 );
//table.columnDefaults( 4 ).padLeft( 10 );
table.add( "Start Game" ).colspan( 5 );
// create the level buttons

table.row();
table.add( "                                               " );
episode1Button = new TextButton( "Poziom 1", getSkin() );

table.add( episode1Button ).fillX().padRight( 10 );

episode2Button = new TextButton( "Poziom 2", getSkin() );

table.add( episode2Button ).fillX().padRight( 10 );
episode3Button = new TextButton( "Poziom 3", getSkin() );

table.add( episode3Button ).fillX().padRight( 10 );
episode4Button = new TextButton( "Poziom 4", getSkin() );
table.row();
table.add( "Wybierz Poziom" );
table.row();
table.add( "                                               " );
table.add( episode4Button ).fillX().padRight( 10 );
episode5Button = new TextButton( "Poziom 5", getSkin() );

table.add( episode5Button ).fillX().padRight( 10 );


episode5Button.addListener( new DefaultActorListener() {
    public void touchUp(
        ActorEvent event,
        float x,
        float y,
        int pointer,
        int button )
    {
        game.getSoundManager().play( TyrianSound.CLICK );
        game.setScreen( new GameLoop( game ) );
    }
} );

Texture backgroundTexture = new Texture(Gdx.files.internal("kulki1.jpg"));
//Texture backgroundTexture1 = new Texture(Gdx.files.internal("White Button1.jpg"));
Image backImage = new Image(backgroundTexture);
//FileHandle skinFile1 = Gdx.files.internal( "White Button1.jpg" );
//startGameButton.setBackground(@drawable/White Button1);
//startGameButton.

shipModelSelectBox = new SelectBox( ShipModel.values(), getSkin() );
table.row();
table.add( "Kulka" );
table.add( shipModelSelectBox ).fillX().colspan( 3 );
table.row();
frontGunSelectBox = new SelectBox( Shield.values(), getSkin() );
table.add( "Poziom trudnosci" );
table.add( frontGunSelectBox ).fillX().colspan( 3 );
TextButton backButton = new TextButton( "Wroc", getSkin() );
backButton.addListener( new DefaultActorListener() {
    public void touchUp(
        ActorEvent event,
        float x,
        float y,
        int pointer,
        int button )
    {
        game.getSoundManager().play( TyrianSound.CLICK );
        game.setScreen( new MenuScreen( game ) );
    }
} );
table.row();
table.add( backButton ).size( 250, 60 ).colspan( 5 );




table.setFillParent(true);
//table.debug(); 


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
