package com.projektandroid;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.blogspot.steigert.tyrian.screens.LevelScreen;
import com.blogspot.steigert.tyrian.screens.SplashScreen;
import com.blogspot.steigert.tyrian.services.LevelManager;
import com.blogspot.steigert.tyrian.services.MusicManager;
import com.blogspot.steigert.tyrian.services.PreferencesManager;
import com.blogspot.steigert.tyrian.services.ProfileManager;
import com.blogspot.steigert.tyrian.services.SoundManager;

/**
 * The game's main class, called as application events are fired.
 */
public class Tyrian
    extends
        Game
{
    // constant useful for logging
    public static final String LOG = Tyrian.class.getSimpleName();

    // whether we are in development mode
    public static final boolean DEV_MODE = false;

    // a libgdx helper class that logs the current FPS each second
    private FPSLogger fpsLogger;

    // services
    private PreferencesManager preferencesManager;
    private ProfileManager profileManager;
    private LevelManager levelManager;
    private MusicManager musicManager;
    private SoundManager soundManager;

    public Tyrian()
    {
    }

    // Services' getters

    public PreferencesManager getPreferencesManager()
    {
        return preferencesManager;
    }

    public ProfileManager getProfileManager()
    {
        return profileManager;
    }

    public LevelManager getLevelManager()
    {
        return levelManager;
    }

    public MusicManager getMusicManager()
    {
        return musicManager;
    }

    public SoundManager getSoundManager()
    {
        return soundManager;
    }

    // Game-related methods

    @Override
    public void create()
    {
        Gdx.app.log( Tyrian.LOG, "Creating game on " + Gdx.app.getType() );

        // create the preferences manager
        preferencesManager = new PreferencesManager();

        // create the music manager
        musicManager = new MusicManager();
        musicManager.setVolume( preferencesManager.getVolume() );
        musicManager.setEnabled( preferencesManager.isMusicEnabled() );

        // create the sound manager
        soundManager = new SoundManager();
        soundManager.setVolume( preferencesManager.getVolume() );
        soundManager.setEnabled( preferencesManager.isSoundEnabled() );

        // create the profile manager
        profileManager = new ProfileManager();
        profileManager.retrieveProfile();

        // create the level manager
        
        levelManager = new LevelManager();

        // create the helper objects
        fpsLogger = new FPSLogger();
        setScreen(new SplashScreen(this));
    }

}
