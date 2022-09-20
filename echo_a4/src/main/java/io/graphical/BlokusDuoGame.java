/**
 * echo
 * 20201034
 */
package io.graphical;

import java.io.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.SynchronousQueue;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import controller.*;
import gameLogic.*;
import gameLogic.interfaces.*;
import io.interfaces.*;
import io.graphical.screens.*;

/**
 * @author agbod
 *
 */
public class BlokusDuoGame extends Game {

    public final float DEFAULT_PADDING = 10;
    public final float MUSIC_VOLUME = 0.25f;

    public final static int BANNER_HEIGHT = 48;
    public final static int WIDTH = 768;
    public final static int HEIGHT = 576 + BANNER_HEIGHT;

    private GameController gameController;
    public PrintStream ioStream;
    public SynchronousQueue<CoordinateInterface> coordinateQueue;
    public SynchronousQueue<PieceInterface> pieceQueue;

    public OrthographicCamera camera;
    public Stage stage;
    public Skin skin;
    public Viewport viewport;
    public Sound gameOverSound;
    public Music startMusic;
    public Music gameMusic;

    public StartScreen startScreen;
    public RulesScreen rulesScreen;
    public SetupScreen setupScreen;
    public GameScreen gameScreen;
    public ResultsScreen resultsScreen;
    public GameScreenInputProcessor gameScreenInputProcessor;
    public ArrayList<Screen> screens;

    public BlokusDuoGame(GameController gameController, UI ui) {
        GUI gui = (GUI)ui;
        this.gameController = gameController;

        gui.setGame(this); // for posting runnables into libGDX game loop
        this.ioStream = new PrintStream(gui.getPipedOutputStream());  // for sending text to control
        this.coordinateQueue = gui.getCoordinateQueue();
        this.pieceQueue = gui.getPieceQueue();
        this.screens = new ArrayList<Screen>();
    }

    @Override
    public void create() {
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false);
        this.viewport = new ScreenViewport(camera);
        // this.viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        this.stage = new Stage(viewport);
        this.skin = new Skin(Gdx.files.internal("myskin.json"));
        Gdx.graphics.setTitle("Blokus Duo");

        this.startScreen = new StartScreen(this);
        this.screens.add(this.startScreen);
        this.rulesScreen = new RulesScreen(this);
        this.screens.add(this.rulesScreen);
        this.setupScreen = new SetupScreen(this);
        this.screens.add(this.setupScreen);
        this.gameScreen = new GameScreen(this);
        this.screens.add(this.gameScreen);
        this.resultsScreen = new ResultsScreen(this);
        this.screens.add(this.resultsScreen);
        this.gameScreenInputProcessor = new GameScreenInputProcessor(this);
        this.gameOverSound = Gdx.audio.newSound(Gdx.files.internal("gameoversound2.mp3"));
        this.startMusic = Gdx.audio.newMusic(Gdx.files.internal("BackgroundSound.mp3"));
        this.startMusic.setLooping(true);
        this.startMusic.setVolume(MUSIC_VOLUME);
        this.startMusic.play();
        this.gameMusic = Gdx.audio.newMusic(Gdx.files.internal("Fluffing-a-Duck.mp3"));
        this.gameMusic.setLooping(true);
        this.gameMusic.setVolume(MUSIC_VOLUME);
        activateStartScreen();
    }

    @Override
    public void render() {
        super.render(); // important!
    }

    @Override
    public void dispose() {
        super.dispose();
        if (this.stage != null) {
            this.stage.dispose();
        }
        if (this.skin != null) {
            this.skin.dispose();
        }
        if (this.gameController != null) {
            this.gameController.stop();
        }
        if (this.gameOverSound != null) {
            this.gameOverSound.stop();
        }
        if (this.startMusic != null) {
            this.startMusic.dispose();
        }
        if (this.gameMusic != null) {
            this.gameMusic.dispose();
        }
        for (Screen screen: this.screens) {
            if (screen != null) {
                screen.dispose();
            }
        }
    }

    public void activateStartScreen() {
        Gdx.input.setInputProcessor(this.stage);
        hideScreens();
        setScreen(this.startScreen);
    }

    public void activateRulesScreen() {
        Gdx.input.setInputProcessor(this.stage);
        hideScreens();
        setScreen(this.rulesScreen);
    }

    public void activateSetupScreen() {
        Gdx.input.setInputProcessor(this.stage);
        hideScreens();
        setScreen(this.setupScreen);
    }

    public void activateGameScreen() {
        Gdx.input.setInputProcessor(this.gameScreenInputProcessor);
        this.startMusic.stop();
        this.gameMusic.play();
        hideScreens();
        setScreen(this.gameScreen);
    }

    public void activateResultsScreen() {
        Gdx.input.setInputProcessor(this.stage);
        this.gameMusic.stop();
        this.gameOverSound.play();
        hideScreens();
        setScreen(this.resultsScreen);
    }

    public void postRunnable(Runnable r) {
        Gdx.app.postRunnable(r);
    }

    public void showMessage(String title, String message) {
        Dialog dialog = new Dialog(title, this.skin);
        dialog.text(message);
        dialog.button("OK");
        dialog.getContentTable().pad(DEFAULT_PADDING);
        dialog.getButtonTable().pad(DEFAULT_PADDING);
        dialog.show(this.stage);
    }

    public void displayFirstPlayer(String message) {
        Dialog dialog = new Dialog("", this.skin);
        dialog.text(message);
        TextButton textButton = new TextButton("OK", skin);
        textButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                activateGameScreen();
            };
        });
        dialog.button(textButton);
        dialog.getContentTable().pad(DEFAULT_PADDING);
        dialog.getButtonTable().pad(DEFAULT_PADDING);
        dialog.show(this.stage);
    }

    public void displayResults(String message) {
        Dialog dialog = new Dialog("Results", this.skin);
        dialog.text(message);
        TextButton textButton = new TextButton("OK", skin);
        textButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stop();
            };
        });
        dialog.button(textButton);
        dialog.getContentTable().pad(DEFAULT_PADDING);
        dialog.getButtonTable().pad(DEFAULT_PADDING);
        dialog.show(this.stage);
    }

    public void setActivePlayerNumber(int playerNumber) {
        this.gameScreenInputProcessor.setActivePlayerNumber(playerNumber);
    }

    public void updateGraphicalBoard(BoardInterface board) {
        this.gameScreenInputProcessor.updateGraphicalBoard(board);
    }

    public void setBanner(String message) {
        this.gameScreen.setBanner(message);
    }

    public void hint(ArrayList<MoveInterface> moves) {
        this.gameScreenInputProcessor.hint(moves);
    }

    public void playInvalidMoveSound() {
        this.gameScreenInputProcessor.invalidMoveSound.play();
    }

    private void hideScreens() {
        for (Screen screen: this.screens) {
            screen.hide();
        }
    }

    private void stop() {
        this.gameController.gameOver = true;
        this.gameController.stop();
    }

}