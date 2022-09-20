package ui.graphics;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import control.BlokusDuoPlay;
import model.Board;
import model.Gamepiece;
import model.GamepieceSet;
import model.Player;
import ui.graphics.actors.CustomLabel;
import ui.graphics.actors.GraphicalBoard;
import ui.graphics.actors.GraphicalGamePiece;

import java.io.PrintStream;

public class BlokusGame extends Game {

    private static final int VIRTUAL_WIDTH = 1024;
    private static final int VIRTUAL_HEIGHT = 810;

    private StartScreen startScreen;
    private PlayScreen playScreen;
    private BlokusDuoPlay blokusDuoPlay;
    private PrintStream pipe;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Skin skin;
    private Stage stage;
    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;
    private Thread gameplay;
    private Array<GraphicalGamePiece> graphicalGamePieces;
    private TextureRegion whiteSquare;
    private TextureRegion blackSquare;
    private CustomLabel customLabel;
    private GraphicalBoard graphicalBoard;
    private int activePlayerNo;

    public BlokusGame(BlokusDuoPlay blokusDuoPlay) {
        this.blokusDuoPlay = blokusDuoPlay;
    }

    @Override
    public void create() {
        GraphicsUI ui = (GraphicsUI)blokusDuoPlay.getUI();
        ui.setBlokusGame(this);
        pipe = new PrintStream(ui.getPipe());

        camera = new OrthographicCamera(VIRTUAL_WIDTH,VIRTUAL_HEIGHT);
        camera.position.set(VIRTUAL_WIDTH  * 0.5f, VIRTUAL_HEIGHT * 0.5f, 0.0f);
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);

        tiledMap = new TmxMapLoader().load("play.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("blokus_duo.json"));

        whiteSquare = skin.getRegion("game_square_white");
        blackSquare = skin.getRegion("game_square_black");

        graphicalGamePieces = new Array<>();
        Player[] players = blokusDuoPlay.getPlayers();
        addPlayerPieces(players[0], blackSquare);
        addPlayerPieces(players[1], whiteSquare);

        BitmapFont banner = skin.getFont("banner");
        Color bannerColor = skin.getColor("blue");
        Label.LabelStyle bannerStyle = new Label.LabelStyle(banner,bannerColor);
        customLabel = new CustomLabel("", bannerStyle);
        customLabel.setPosition(10,BlokusGame.getVirtualHeight()-banner.getCapHeight());

        graphicalBoard = new GraphicalBoard(ui.getBoard(),blackSquare,whiteSquare);
        MapProperties boardInfo = tiledMap.getLayers().get(2).getObjects().get("Board").getProperties();
        graphicalBoard.setPosition( (float) boardInfo.get("x"), (float) boardInfo.get("y"));
        graphicalBoard.setSize( (float) boardInfo.get("width"), (float) boardInfo.get("height"));
        graphicalBoard.setTouchable(Touchable.disabled);

        startScreen = new StartScreen(this);
        playScreen = new PlayScreen(this);
        setStartScreen();

        gameplay = new Thread(blokusDuoPlay);
        gameplay.start();
    }

    private void addPlayerPieces(Player player, TextureRegion square) {
        GamepieceSet gamepieceSet = player.getGamepieceSet();
        MapObjects objects = tiledMap.getLayers().get(2).getObjects();

        for(String key : gamepieceSet.getPieces().keySet()) {
            MapObject mapObject = objects.get(String.valueOf(player.getPlayerNo())+key);
            Gamepiece gamepiece = (Gamepiece)gamepieceSet.getPieces().get(key);
            float x = (float)(mapObject.getProperties().get("x"));
            float y = (float)(mapObject.getProperties().get("y"));
            GraphicalGamePiece graphicalGamePiece = new GraphicalGamePiece(key,gamepiece,square,player.getPlayerNo(),x,y,this);
            graphicalGamePiece.setVisible(true);
            graphicalGamePiece.setTouchable(Touchable.enabled);
            graphicalGamePieces.add(graphicalGamePiece);
        }
    }

    @Override
    public void dispose() {
        gameplay.stop();
        startScreen.dispose();
        playScreen.dispose();
        skin.dispose();
        stage.dispose();
    }

    public void setStartScreen() {
        setScreen(startScreen);
    }

    public void setPlayScreen() {
        setScreen(playScreen);
    }

    void showDialog(String text) {
        Dialog dialog = new Dialog("Attention", skin, "default");
        dialog.text(text);
        dialog.button("OK");
        dialog.getContentTable().pad(10);
        dialog.getButtonTable().pad(10);
        dialog.show(stage);
    }

    void displayMessage(String text) {
        customLabel.updateText(text);
    }

    // add a single-run code to be executed in GDX game loop between window updates
    public void postRunnable(Runnable runnable) {
        Gdx.app.postRunnable(runnable);
    }

    public static int getVirtualWidth() {
        return VIRTUAL_WIDTH;
    }

    public static int getVirtualHeight() {
        return VIRTUAL_HEIGHT;
    }

    public PrintStream getPipe() {
        return pipe;
    }

    public Skin getSkin() {
        return skin;
    }

    public Stage getStage() {
        return stage;
    }

    public OrthographicCamera getCamera() { return camera; }

    public TiledMap getTiledMap() { return tiledMap; }

    public TiledMapRenderer getTiledMapRenderer() { return tiledMapRenderer; }

    public Array<GraphicalGamePiece> getGraphicalGamePieces() {
        return graphicalGamePieces;
    }

    public CustomLabel getCustomLabel() {
        return customLabel;
    }

    public GraphicalBoard getGraphicalBoard() {
        return graphicalBoard;
    }

    public void setActivePlayerNo(int playerNo) {
        activePlayerNo = playerNo;
    }

    public int getActivePlayerNo() {
        return activePlayerNo;
    }


    public void updateBoard(Board board) {
        graphicalBoard.updateBoard(board);
    }

    public void updateGamepieces(Player[] players) {
        for (GraphicalGamePiece graphicalGamePiece : graphicalGamePieces) {
            boolean found = false;
            for (Player player : players) {
                if (player.getGamepieceSet().getPieces().containsKey(graphicalGamePiece.getGamepieceName()) &&
                        (player.getPlayerNo() == graphicalGamePiece.getPlayerNo())) {
                    found = true;
                }
            }
            if (found) {
                graphicalGamePiece.setDefaultOrientation();
                graphicalGamePiece.setDefaultPosition();
                graphicalGamePiece.setVisible(true);
            } else {
                graphicalGamePiece.setVisible(false);
            }
        }
    }
}
