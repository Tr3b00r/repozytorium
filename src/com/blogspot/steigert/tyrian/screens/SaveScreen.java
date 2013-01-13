package com.blogspot.steigert.tyrian.screens;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ActorEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.projektandroid.Tyrian;
import com.blogspot.steigert.tyrian.services.SoundManager.TyrianSound;
import com.blogspot.steigert.tyrian.utils.DefaultActorListener;

public class SaveScreen
implements
Screen 
{Tyrian game;
Stage stage;
Float pozx=0.0f;
Float pozz=0.0f;
Float index=0.0f;
String data1="1";
String data2="2";
String data3="3";
String data4="4";
String data5="5";
FileHandle datafh1=Gdx.files.local("data1.txt");
FileHandle datafh2=Gdx.files.local("data2.txt");
FileHandle datafh3=Gdx.files.local("data3.txt");
FileHandle datafh4=Gdx.files.local("data4.txt");
FileHandle datafh5=Gdx.files.local("data5.txt");
private Skin skin;
private Table table;
public static final int GAME_VIEWPORT_WIDTH = 140, GAME_VIEWPORT_HEIGHT = 400;
public static final int MENU_VIEWPORT_WIDTH = 300, MENU_VIEWPORT_HEIGHT = 300;
public SaveScreen(
Tyrian game,Float x,Float z,Float indexior )
{
this.game=game;
int width = ( isGameScreen() ? GAME_VIEWPORT_WIDTH : MENU_VIEWPORT_WIDTH );
int height = ( isGameScreen() ? GAME_VIEWPORT_HEIGHT : MENU_VIEWPORT_HEIGHT );
this.stage = new Stage( width, height, true );
this.index=indexior;
this.pozx=x;
this.pozz=z;
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

final TextButton saveSpot1;
final TextButton saveSpot2;
final TextButton saveSpot3;
final TextButton saveSpot4;
final TextButton saveSpot5;
TextButton exitButton=new TextButton("Back",getSkin());
if(!datafh1.exists())
	saveSpot1= new TextButton("1", getSkin());
else {
	data1=Odczyt("data1.txt");
	saveSpot1= new TextButton(data1, getSkin());}
if(!datafh2.exists())
	saveSpot2= new TextButton("2", getSkin());
else {
	data2=Odczyt("data2.txt");
	saveSpot2= new TextButton(data2, getSkin());}
if(!datafh3.exists())
	saveSpot3= new TextButton("3", getSkin());
else {
	data3=Odczyt("data3.txt");
	saveSpot3= new TextButton(data3, getSkin());}
if(!datafh4.exists())
	saveSpot4= new TextButton("4", getSkin());
else {
	data4=Odczyt("data4.txt");
	saveSpot4= new TextButton(data4, getSkin());}
if(!datafh5.exists())
	saveSpot5= new TextButton("5", getSkin());
else {
	data5=Odczyt("data5.txt");
	saveSpot5= new TextButton(data5, getSkin());}
Texture backgroundTexture = new Texture(Gdx.files.internal("kulki1.jpg"));
Image backImage = new Image(backgroundTexture);
saveSpot1.addListener( new DefaultActorListener() {
    @Override
    public void touchUp(
        ActorEvent event,
        float x,
        float y,
        int pointer,
        int button )
    {
        super.touchUp( event, x, y, pointer, button );
        FileHandle fh=Gdx.files.local("zapis1.txt");
		fh.writeString(String.valueOf(pozx)+" "+String.valueOf(pozz)+" "+String.valueOf(index),false);
		data1=getDateTime();
		saveSpot1.setText(data1);
		 datafh1.writeString(data1,false);

    }
} );
saveSpot2.addListener( new DefaultActorListener() {
    @Override
    public void touchUp(
        ActorEvent event,
        float x,
        float y,
        int pointer,
        int button )
    {
        super.touchUp( event, x, y, pointer, button );
        FileHandle fh=Gdx.files.local("zapis2.txt");
    	fh.writeString(String.valueOf(pozx)+" "+String.valueOf(pozz)+" "+String.valueOf(index),false);
		data2=getDateTime();
		saveSpot2.setText(data2);
		 datafh2.writeString(data2,false);
    }
} );
saveSpot3.addListener( new DefaultActorListener() {
    @Override
    public void touchUp(
        ActorEvent event,
        float x,
        float y,
        int pointer,
        int button )
    {
        super.touchUp( event, x, y, pointer, button );
        FileHandle fh=Gdx.files.local("zapis3.txt");
    	fh.writeString(String.valueOf(pozx)+" "+String.valueOf(pozz)+" "+String.valueOf(index),false);
		data3=getDateTime();
		saveSpot3.setText(data3);
		 datafh3.writeString(data3,false);
    }
} );
saveSpot4.addListener( new DefaultActorListener() {
    @Override
    public void touchUp(
        ActorEvent event,
        float x,
        float y,
        int pointer,
        int button )
    {
        super.touchUp( event, x, y, pointer, button );
        FileHandle fh=Gdx.files.local("zapis4.txt");
    	fh.writeString(String.valueOf(pozx)+" "+String.valueOf(pozz)+" "+String.valueOf(index),false);
		data4=getDateTime();
		saveSpot4.setText(data4);
		 datafh4.writeString(data4,false);
    }
} );
saveSpot5.addListener( new DefaultActorListener() {
    @Override
    public void touchUp(
        ActorEvent event,
        float x,
        float y,
        int pointer,
        int button )
    {
        super.touchUp( event, x, y, pointer, button );
        FileHandle fh=Gdx.files.local("zapis5.txt");
    	fh.writeString(String.valueOf(pozx)+" "+String.valueOf(pozz)+" "+String.valueOf(index),false);
		data5=getDateTime();
		saveSpot5.setText(data5);
		 datafh5.writeString(data5,false);
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
        game.setScreen(new MenuScreen(game));
    }
} );
System.out.println(pozx);
System.out.println(pozz);
table.setFillParent(true);
table.add( "Save your game" ).colspan( 3 );
table.row();
table.add(saveSpot1).width(200).height(40).padTop(10);
table.row();
table.add(saveSpot2).width(200).height(40).padTop(10);
table.row();
table.add(saveSpot3).width(200).height(40).padTop(10);
table.row();
table.add(saveSpot4).width(200).height(40).padTop(10);
table.row();
table.add(saveSpot5).width(200).height(40).padTop(10);
table.row();
table.add(exitButton).width(200).height(40).padTop(10);
stage.addActor(backImage);
stage.addActor(table);
}
private String getDateTime() {
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date date = new Date();
    System.out.println(dateFormat.format(date));
  return dateFormat.format(date);
}
public String Odczyt(String name){
	

	FileHandle fh=Gdx.files.local(name);
	String data = fh.readString();
	return data;
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

