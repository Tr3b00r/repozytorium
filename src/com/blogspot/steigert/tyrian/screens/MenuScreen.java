package com.blogspot.steigert.tyrian.screens;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.PrintWriter;

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

public class MenuScreen
    implements
        Screen 
{Tyrian game;
Stage stage;
Float cosiek=0.0f;
Float cosiek1=0.0f;
Float index=0.0f;
private static char zdanie1;
File file=new File("costam.txt");
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
        FileHandle profileDataFile = Gdx.files.local( "data/ala.txt" );
        profileDataFile.writeString("3dsad",false);
        String localDir = Gdx.files.getLocalStoragePath();
    }
    public MenuScreen(
            Tyrian game,int index )
        {
            this.game=game;
            int width = ( isGameScreen() ? GAME_VIEWPORT_WIDTH : MENU_VIEWPORT_WIDTH );
            int height = ( isGameScreen() ? GAME_VIEWPORT_HEIGHT : MENU_VIEWPORT_HEIGHT );
            FileHandle profileDataFile = Gdx.files.local( "data/ala.txt" );
            profileDataFile.writeString("3dsad",false);
            }
    public static void Zapis() {

		FileOutputStream fos = null;
		String str = "Tekst do zapisania w pliku i dwie liczby: 123 i 321";

		try {
		  fos = new FileOutputStream("ala.txt"); //Otwieranie pliku 
		   for(int i = 0; i < str.length(); i++){
		     fos.write((int)str.charAt(i)); //Zapis bajt po bajcie kazdego znaku...
		   }
		} catch(IOException ex){
		   System.out.println("Blad operacji na pliku: "+ex);
		}

		 try {
		  fos.close(); //Zamykanie pliku 
		 } catch (IOException e) {
		   e.printStackTrace();
		} 
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

		TextButton startGameButton = new TextButton("New Game", getSkin());
		TextButton optionsButton = new TextButton("Options", getSkin());
		TextButton continueButton = new TextButton("Continue", getSkin());
		TextButton saveButton = new TextButton("Save", getSkin());
		TextButton loadButton = new TextButton("Load", getSkin());
		TextButton exitButton = new TextButton("Exit", getSkin());

		Texture backgroundTexture = new Texture(Gdx.files.internal("kulki1.jpg"));
		Image backImage = new Image(backgroundTexture);
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
                  game.getSoundManager().play( TyrianSound.CLICK );
             System.exit(0);
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
                game.setScreen( new OptionsScreen( game ) );
            }
        } );	
		loadButton.addListener( new DefaultActorListener() {
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
                game.setScreen( new LoadScreen( game ) );
            }
        } );	
		continueButton.addListener( new DefaultActorListener() {
            @Override
            public void touchUp(
                ActorEvent event,
                float x,
                float y,
                int pointer,
                int button )
            {
                super.touchUp( event, x, y, pointer, button );
Odczyt();
	      try {
				game.setScreen( new LevelScreen( game,index,cosiek,-8.0f,cosiek1 ) );
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            }
        } );
		saveButton.addListener( new DefaultActorListener() {
            @Override
            public void touchUp(
                ActorEvent event,
                float x,
                float y,
                int pointer,
                int button )
            {
            	  super.touchUp( event, x, y, pointer, button );
            	  Odczyt();
                  game.getSoundManager().play( TyrianSound.CLICK );
  					game.setScreen( new SaveScreen(game,cosiek,cosiek1,index) );
 
            }
        } );
		table.setFillParent(true);
//		table.debug(); 
		table.add( "Witaj w KulkoWdziurce!" ).colspan( 3 );
		table.row();
		table.add(startGameButton).width(200).height(40).padTop(10);
		table.row();
		table.add(continueButton).width(200).height(40).padTop(10);
		table.row();
		table.add(optionsButton).width(200).height(40).padTop(10);
		table.row();
		table.add(saveButton).width(200).height(40).padTop(10);
		table.row();
		table.add(loadButton).width(200).height(40).padTop(10);
		table.row();
		table.add(exitButton).width(200).height(40).padTop(10);
		
		//stage.addActor(backImage);
		
		stage.addActor(backImage);
		stage.addActor(table);
    }
public void Odczyt(){
	
	int b=0;
	int c=0;
    float cos1=0.0f;
   float cos2=0.0f;
	FileHandle fh=Gdx.files.local("ala.txt");
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
	  public String readFile(File name) {
			BufferedReader inRead = null;
			StringBuilder outLine = new StringBuilder();
			try {
				inRead = new BufferedReader(new FileReader(name), 4096);
				final char mBufffer[] = new char[1024];
				int mLength;
				while ((mLength = inRead.read(mBufffer)) > 0) {
					outLine.append(mBufffer, 0, mLength);
				}
			} catch (IOException e) {
			} catch (OutOfMemoryError e) {
				
				outLine = null;
			} finally {
				try {
					if (inRead != null) inRead.close();
				} catch (IOException e) {
			
				}
			}
			return outLine.toString();
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
