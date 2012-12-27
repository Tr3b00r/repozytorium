package com.blogspot.steigert.tyrian.screens;

import com.badlogic.gdx.scenes.scene2d.ActorEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.projektandroid.Tyrian;
import com.blogspot.steigert.tyrian.services.SoundManager.TyrianSound;
import com.blogspot.steigert.tyrian.utils.DefaultActorListener;

public class SaveScreen
    extends
        AbstractScreen
{
    public SaveScreen(
        Tyrian game )
    {
        super( game );
    }

    @Override
    public void show()
    {
        super.show();

        // retrieve the default table actor
        Table table = super.getTable();
        table.add("Zapis");
        table.row();

        TextButton zapis1 = new TextButton( "Zapis 1", getSkin() );
        table.add( zapis1 ).size( 300, 60 ).uniform().spaceBottom( 10 );
        table.row();
        TextButton zapis2 = new TextButton( "Zapis 2", getSkin() );
        table.add( zapis2 ).size( 300, 60 ).uniform().spaceBottom( 10 );
        table.row();
        TextButton zapis3 = new TextButton( "Zapis 3", getSkin() );
        table.add( zapis3 ).uniform().fill().spaceBottom( 10 );
        table.row();
        TextButton zapis4 = new TextButton( "Zapis 4", getSkin() );
        table.add( zapis4 ).uniform().fill().spaceBottom( 10 );
        table.row();
        TextButton zapis5 = new TextButton( "Zapis 5", getSkin() );
        table.add( zapis5 ).uniform().fill().spaceBottom( 10 );
        table.row();
        TextButton backButton = new TextButton( "Wroc", getSkin() );
        table.add( backButton ).uniform().fill().spaceBottom( 20 );
        backButton.addListener( new DefaultActorListener() {
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
                game.setScreen( new MenuScreen( game ) );
            }
        } );
      
       
     
       
    }
}
