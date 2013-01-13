package com.blogspot.steigert.tyrian.screens;

import java.io.IOException;

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

public class LoadScreen implements Screen{
	Tyrian game;
	Stage stage;
	Float cosiek=0.0f;
	Float cosiek1=0.0f;
	Float index=0.0f;
	private static char zdanie1;
	Float pozx=0.0f;
	Float pozz=0.0f;
	private Skin skin;
	private Table table;
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
	public static final int GAME_VIEWPORT_WIDTH = 140, GAME_VIEWPORT_HEIGHT = 400;
	public static final int MENU_VIEWPORT_WIDTH = 300, MENU_VIEWPORT_HEIGHT = 300;
	
	public LoadScreen(Tyrian game){
		this.game=game;
		int width = ( isGameScreen() ? GAME_VIEWPORT_WIDTH : MENU_VIEWPORT_WIDTH );
		int height = ( isGameScreen() ? GAME_VIEWPORT_HEIGHT : MENU_VIEWPORT_HEIGHT );
		this.stage = new Stage( width, height, true );

	}
	
	protected boolean isGameScreen(){
		return false;
	}
	
	protected Skin getSkin(){
		if( skin == null ) {
			FileHandle skinFile = Gdx.files.internal( "skin/uiskin.json" );
			skin = new Skin( skinFile );
		}
		return skin;
	}

	public void show(){
		stage=new Stage();
		Gdx.input.setInputProcessor(stage);
		table = new Table( getSkin() );
		
		final TextButton loadSpot1;
		final TextButton loadSpot2;
		final TextButton loadSpot3;
		final TextButton loadSpot4;
		final TextButton loadSpot5;
		
		TextButton exitButton=new TextButton("Back",getSkin());
		if(!datafh1.exists()){
			loadSpot1= new TextButton("1", getSkin());
			loadSpot1.setVisible(false);
		}
		else {
			data1=Odczyt1("data1.txt");
			loadSpot1= new TextButton(data1, getSkin());
		}
		if(!datafh2.exists()){
			loadSpot2= new TextButton("2", getSkin());
			loadSpot2.setVisible(false);
		}
		else{
			data2=Odczyt1("data2.txt");
			loadSpot2= new TextButton(data2, getSkin());
		}
		if(!datafh3.exists()){
			loadSpot3= new TextButton("3", getSkin());
			loadSpot3.setVisible(false);
		}
		else{
			data3=Odczyt1("data3.txt");
			loadSpot3= new TextButton(data3, getSkin());
		}
		if(!datafh4.exists()){
			loadSpot4= new TextButton("4", getSkin());
			loadSpot4.setVisible(false);
		}
		else{
			data4=Odczyt1("data4.txt");
		loadSpot4= new TextButton(data4, getSkin());
		}
		if(!datafh5.exists()){
			loadSpot5= new TextButton("5", getSkin());
			loadSpot5.setVisible(false);
		}
		else{
			data5=Odczyt1("data5.txt");
			loadSpot5= new TextButton(data5, getSkin());
		}
		Texture backgroundTexture = new Texture(Gdx.files.internal("kulki1.jpg"));
//Texture backgroundTexture1 = new Texture(Gdx.files.internal("White Button1.jpg"));
		Image backImage = new Image(backgroundTexture);
//FileHandle skinFile1 = Gdx.files.internal( "White Button1.jpg" );
//startGameButton.setBackground(@drawable/White Button1);
//startGameButton.
		loadSpot1.addListener( new DefaultActorListener(){
			
    public void touchUp(
        ActorEvent event,
        float x,
        float y,
        int pointer,
        int button )
    {
        super.touchUp( event, x, y, pointer, button );
        Odczyt("zapis1.txt");
	      try {
				game.setScreen( new LevelScreen( game,index,cosiek,-8.0f,cosiek1 ) );
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }
} );
loadSpot2.addListener( new DefaultActorListener() {
    @Override
    public void touchUp(
        ActorEvent event,
        float x,
        float y,
        int pointer,
        int button )
    {
        super.touchUp( event, x, y, pointer, button );
        Odczyt("zapis2.txt");
	      try {
				game.setScreen( new LevelScreen( game,index,cosiek,-8.0f,cosiek1 ) );
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }
} );
loadSpot3.addListener( new DefaultActorListener() {
    @Override
    public void touchUp(
        ActorEvent event,
        float x,
        float y,
        int pointer,
        int button )
    {
        super.touchUp( event, x, y, pointer, button );
        Odczyt("zapis3.txt");
	      try {
				game.setScreen( new LevelScreen( game,index,cosiek,-8.0f,cosiek1 ) );
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }
} );
loadSpot4.addListener( new DefaultActorListener() {
    @Override
    public void touchUp(
        ActorEvent event,
        float x,
        float y,
        int pointer,
        int button )
    {
        super.touchUp( event, x, y, pointer, button );
        Odczyt("zapis4.txt");
	      try {
				game.setScreen( new LevelScreen( game,index,cosiek,-8.0f,cosiek1 ) );
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }
} );
loadSpot5.addListener( new DefaultActorListener() {
    @Override
    public void touchUp(
        ActorEvent event,
        float x,
        float y,
        int pointer,
        int button )
    {
        super.touchUp( event, x, y, pointer, button );
        Odczyt("zapis5.txt");
	      try {
				game.setScreen( new LevelScreen( game,index,cosiek,-8.0f,cosiek1 ) );
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
        game.setScreen(new MenuScreen(game));
    }
} );
System.out.println(pozx);
System.out.println(pozz);
table.setFillParent(true);
//table.debug(); 
table.add( "Witaj w KulkoWdziurce!" ).colspan( 3 );
table.row();
table.add(loadSpot1).width(200).height(40).padTop(10);
table.row();
loadSpot3.setVisible(false);
table.add(loadSpot2).width(200).height(40).padTop(10);
table.row();
table.add(loadSpot3).width(200).height(40).padTop(10);
table.row();
table.add(loadSpot4).width(200).height(40).padTop(10);
table.row();
table.add(loadSpot5).width(200).height(40).padTop(10);
table.row();
table.add(exitButton).width(200).height(40).padTop(10);

//stage.addActor(backImage);

stage.addActor(backImage);
stage.addActor(table);
}
public String Odczyt1(String name){
	

	FileHandle fh=Gdx.files.local(name);
	String data = fh.readString();
	return data;
}
public void Odczyt(String name){
	
	int b=0;
	int c=0;
    float cos1=0.0f;
   float cos2=0.0f;
	FileHandle fh=Gdx.files.local(name);
	//fh.writeString("dsad",false);
	String zdanie = fh.readString();
     zdanie.length();
     for(int i=0;i<zdanie.length();i++){
   	  zdanie1=zdanie.charAt(i);
   	  if(((float)zdanie1)==32) {
   		 // System.out.print("fail"+i);
   		  b=i;
   		  break;
   	  }    	
   	  zdanie1=zdanie.charAt(i);
     }
     String sub=zdanie.substring(0,b);
     for(int j=b+1;j<zdanie.length();j++){
   	  zdanie1=zdanie.charAt(j);
   	  if(((float)zdanie1)==32) {
   		  c=j;
   		  break;
   	  }    	  
   	  zdanie1=zdanie.charAt(j);
     }
     String sub1=zdanie.substring(b+1,c);
     String sub2=zdanie.substring(c+1,zdanie.length());
     cosiek = new Float(sub);
     cosiek1 = new Float(sub1);
     index=new Float(sub2);
     System.out.println(cosiek);
     System.out.println(cosiek1);
     System.out.println(zdanie);
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

