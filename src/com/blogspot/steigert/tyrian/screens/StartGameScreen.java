
package com.blogspot.steigert.tyrian.screens;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ActorEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import com.blogspot.steigert.tyrian.domain.FrontGun;
import com.blogspot.steigert.tyrian.domain.Item;
import com.blogspot.steigert.tyrian.domain.Profile;
import com.blogspot.steigert.tyrian.domain.Shield;
import com.blogspot.steigert.tyrian.domain.Ship;
import com.blogspot.steigert.tyrian.domain.ShipModel;
import com.blogspot.steigert.tyrian.services.MusicManager.TyrianMusic;
import com.blogspot.steigert.tyrian.services.SoundManager.TyrianSound;
import com.blogspot.steigert.tyrian.utils.DefaultActorListener;
import com.projektandroid.Tyrian;

public class StartGameScreen
implements Screen
{
    private Profile profile;
    private Ship ship;
    public static final int GAME_VIEWPORT_WIDTH = 540, GAME_VIEWPORT_HEIGHT = 700;
    public static final int MENU_VIEWPORT_WIDTH = 1500, MENU_VIEWPORT_HEIGHT = 1700;

    protected final Tyrian game;
    protected Stage stage;
     protected final int selectedIndex=indexofship();
    private BitmapFont font;
    private SpriteBatch batch;
    private Skin skin;
    private TextureAtlas atlas;
    private Table table;
    private TextButton episode1Button;
    private TextButton episode2Button;
    private TextButton episode3Button;
    private TextButton episode4Button;
    private TextButton episode5Button;
    private TextButton episode6Button;
    private SelectBox shipModelSelectBox;
    private SelectBox frontGunSelectBox;
    private SelectBox shieldSelectBox;
    private Label creditsLabel;
    private Image shipModelImage;
    private Image frontGunImage;
    private Image shieldImage;


    private ItemSelectionListener itemSelectionListener;

    public StartGameScreen(
        Tyrian game )
    {
        this.game = game;
        int width = ( isGameScreen() ? GAME_VIEWPORT_WIDTH : MENU_VIEWPORT_WIDTH );
        int height = ( isGameScreen() ? GAME_VIEWPORT_HEIGHT : MENU_VIEWPORT_HEIGHT );
        this.stage = new Stage( 600, 100, true );
        // create the listeners
        itemSelectionListener = new ItemSelectionListener();
    }
    protected String getName()
    {
        return getClass().getSimpleName();
    }
    protected Skin getSkin()
    {
        if( skin == null ) {
            FileHandle skinFile = Gdx.files.internal( "skin/uiskin.json" );
            skin = new Skin( skinFile );
        }
        return skin;
    }
    protected boolean isGameScreen()
    {
        return false;
    }
    @Override
    public void show()
    {
    	stage=new Stage();
        Gdx.app.log( Tyrian.LOG, "Showing screen: " + getName() );
        
        // set the stage as the input processor
        Gdx.input.setInputProcessor( stage );

        // start playing the menu music (the player might be returning from the
        // level screen)
        game.getMusicManager().play( TyrianMusic.MENU );

        // retrieve the default table actor
    	table = new Table( getSkin() );
    	table.setFillParent(true);
        table.defaults().spaceBottom( 10 );

        table.add( "Start Game" ).colspan(7);

        // retrieve the table's layout
        profile = game.getProfileManager().retrieveProfile();
        ship = profile.getShip();
        // create the level buttons
        table.row();
        table.add( "Episodes" ).colspan( 7 );
        table.row();
        table.add( "                     " );
        table.add( "        " );
        episode1Button = new TextButton( "Episode 1", getSkin() );
  
        table.add(episode1Button).padRight(30).padLeft(40);
        episode2Button = new TextButton( "Episode 2", getSkin() );

       // table.add(episode2Button).padRight(10).padLeft(10);
        episode3Button = new TextButton( "Episode 3", getSkin() );
 
        table.add(episode2Button).padRight(150);
        table.add( "        " );
        table.add( "        " );
        table.row();
        episode4Button = new TextButton( "Episode 4", getSkin() );
        table.add( "        " );
        table.add( "        " );
        table.add( episode3Button ).padRight(30).padLeft(40);

        episode5Button = new TextButton( "Episode 5", getSkin() );

      //  table.add( episode5Button ).padRight(10).padLeft(10);

        episode6Button = new TextButton( "Episode 5", getSkin() );
        episode1Button.addListener( new DefaultActorListener() {
            public void touchUp(
                ActorEvent event,
                float x,
                float y,
                int pointer,
                int button )
            {
                game.getSoundManager().play( TyrianSound.CLICK );
                try {
					game.setScreen( new LevelScreen( game,Float.valueOf(shipModelSelectBox.getSelectionIndex()),28.0f,-8.0f,-4.0f ) );
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        } );
        episode2Button.addListener( new DefaultActorListener() {
            public void touchUp(
                ActorEvent event,
                float x,
                float y,
                int pointer,
                int button )
            {
                game.getSoundManager().play( TyrianSound.CLICK );
                try {
					game.setScreen( new LevelScreen1( game,Float.valueOf(shipModelSelectBox.getSelectionIndex()),-16.0f,-8.0f,-13.0f ) );
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        } );
        table.add( episode4Button ).padRight(150);
        table.add( "        " );
        table.add( "        " );
        // create the item select boxes
        shipModelSelectBox = new SelectBox( ShipModel.values(), getSkin() );
        shipModelSelectBox.addListener( itemSelectionListener );
        shipModelImage = new Image();

        table.row();
        table.add( "        " );
        table.add( "                         " );
        table.add( shipModelSelectBox );
        table.add( shipModelImage );
        table.add( "        " );
        Texture backgroundTexture = new Texture(Gdx.files.internal("kulki1.jpg"));
		//Texture backgroundTexture1 = new Texture(Gdx.files.internal("White Button1.jpg"));
		Image backImage = new Image(backgroundTexture);

     //   shieldSelectBox = new SelectBox( Shield.values(), getSkin() );
       // shieldSelectBox.addListener( itemSelectionListener );
      //  shieldImage = new Image();

        table.row();
     //   table.add( "        " );
      //  table.add( "        " );
      //  table.add( "Difficulty Level" );
      //  table.add( shieldSelectBox ).padRight(120);

        // create the credits label
        creditsLabel = new Label( profile.getCreditsAsText(), getSkin() );
        table.row();

        // register the back button
        TextButton backButton = new TextButton( "Back to main menu", getSkin() );
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
        table.add( backButton ).size( 150, 40 ).colspan( 5 );

        // set the select boxes' initial values
        updateValues();
        stage.addActor(backImage);
		stage.addActor(table);
    }
    public TextureAtlas getAtlas()
    {
        if( atlas == null ) {
            atlas = new TextureAtlas( Gdx.files.internal( "image-atlases/pages.atlas" ) );
        }
        return atlas;
    }
    private void updateValues()
    {
        // select boxes
        shipModelSelectBox.setSelection( ship.getShipModel().ordinal() );
     // shieldSelectBox.setSelection( ship.getShield().ordinal() );

        // drawables
        String prefix = "start-game-screen/";
        TextureRegion shipModel = getAtlas().findRegion(
            prefix + ship.getShipModel().getSimpleName() );
        TextureRegion frontGun = getAtlas().findRegion( prefix + ship.getFrontGun().getSimpleName() );
        TextureRegion shield = getAtlas().findRegion( prefix + ship.getShield().getSimpleName() );

        // images
        shipModelImage.setDrawable( new TextureRegionDrawable( shipModel ) );
    
     //   shieldImage.setDrawable( new TextureRegionDrawable( shield ) );
    }

 

    /**
     * Listener for all the item select boxes.
     */
    private class ItemSelectionListener
        extends
            ChangeListener
    {
        @Override
        public void changed(
            ChangeEvent event,
            Actor actor )
        {
            // find the selected item
            Item selectedItem = null;
            int selectedIndex = ( (SelectBox) actor ).getSelectionIndex();
            if( actor == shipModelSelectBox ) {
                selectedItem = ShipModel.values()[ selectedIndex ];
            } else if( actor == frontGunSelectBox ) {
                selectedItem = FrontGun.values()[ selectedIndex ];
            } else if( actor == shieldSelectBox ) {
                selectedItem = Shield.values()[ selectedIndex ];
            } else {
                return;
            }

            // if the ship already contains the item, there is no need to buy it
            if( ship.contains( selectedItem ) ) return;

            // try and buy the item
            if( profile.buy( selectedItem ) ) {
                creditsLabel.setText( profile.getCreditsAsText() );
            }

            // update the widgets
            updateValues();
        }
    }

public int indexofship(){
	   int selectedIndex = 0;
	   
	return selectedIndex;
}
    public void render(
        float delta )
    {
        // (1) process the game logic

        // update the actors
        stage.act( delta );

        // (2) draw the result

        // clear the screen with the given RGB color (black)
        Gdx.gl.glClearColor( 0f, 0f, 0f, 1f );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );

        // draw the actors
        stage.draw();

        // draw the table debug lines
        Table.drawDebug( stage );
    }

    public void hide()
    {
        Gdx.app.log( Tyrian.LOG, "Hiding screen: " + getName() );

        // dispose the screen when leaving the screen;
        // note that the dipose() method is not called automatically by the
        // framework, so we must figure out when it's appropriate to call it
        dispose();
    }

    public void pause()
    {
        Gdx.app.log( Tyrian.LOG, "Pausing screen: " + getName() );
    }

    public void resume()
    {
        Gdx.app.log( Tyrian.LOG, "Resuming screen: " + getName() );
    }

    public void dispose()
    {
        Gdx.app.log( Tyrian.LOG, "Disposing screen: " + getName() );

        // the following call disposes the screen's stage, but on my computer it
        // crashes the game so I commented it out; more info can be found at:
        // http://www.badlogicgames.com/forum/viewtopic.php?f=11&t=3624
        // stage.dispose();

        // as the collaborators are lazily loaded, they may be null
        if( font != null ) font.dispose();
        if( batch != null ) batch.dispose();
        if( skin != null ) skin.dispose();
        if( atlas != null ) atlas.dispose();
    }
    public void resize(
            int width,
            int height )
        {
            Gdx.app.log( Tyrian.LOG, "Resizing screen: " + getName() + " to: " + width + " x " + height );
        }
    public SelectBox getShipModelBox(){
    	
    	return shipModelSelectBox;
    }

}
