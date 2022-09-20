/**
 * echo
 * 20201034
 */
package io.graphical.screens;

import java.util.ArrayList;

import gameLogic.Board;
import gameLogic.Piece;
import gameLogic.interfaces.MoveInterface;
import gameLogic.interfaces.PieceInterface;
import gameLogic.interfaces.PieceInterface;
import gameLogic.interfaces.PlayerInterface;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import io.graphical.*;

/**
 * @author agbod
 *
 */
public class GameScreen extends ScreenAdapter {

    public BlokusDuoGame game;
    public Stage stage;
    public Skin skin;
    public SpriteBatch batch;
    public Sprite block;
    public OrthographicCamera camera;

    public TextureRegion blackSquare;
    public TextureRegion whiteSquare;

    public TiledMap tiledMap;
    public TiledMapRenderer tiledMapRenderer;
    public TiledMapImageLayer imageLayer;
    public TiledMapTileLayer tileLayer;
    public MapLayer objectLayer;
    public MapObjects mapObjects;
    public ArrayList<GraphicalPiece> blackPieces;
    public ArrayList<GraphicalPiece> whitePieces;
    public GraphicalBoard graphicalBoard;

    public GraphicalHintPiece hintPiece;
    public GraphicalPiece originalPiece;
    public boolean displayHint;
    public long hintStartTime;

    public int imageWidth;
    public int imageHeight;

    public BitmapFont helvetique;
    public String bannerText;
    public float bannerX;
    public float bannerY;

    public final float SCALE = 0.75f;
    public final long HINT_DISPLAY_TIME = 6000l;
    public final long HINT_BLINK_TIME = 500l;    

    public GameScreen(BlokusDuoGame game) {
        this.game = game;
        this.stage = game.stage;
        this.skin = game.skin;
        this.batch = new SpriteBatch();
        this.camera = game.camera;

        this.blackSquare = this.skin.getRegion("game_square_black");
        this.blackSquare.setRegionWidth((int) (this.blackSquare.getRegionWidth() * SCALE));
        this.blackSquare.setRegionHeight((int) (this.blackSquare.getRegionHeight() * SCALE));
        this.whiteSquare = this.skin.getRegion("game_square_white");
        this.whiteSquare.setRegionWidth((int) (this.whiteSquare.getRegionWidth() * SCALE));
        this.whiteSquare.setRegionHeight((int) (this.whiteSquare.getRegionHeight() * SCALE));

        this.tiledMap = new TmxMapLoader().load("prototype.tmx");
        this.imageLayer = (TiledMapImageLayer) this.tiledMap.getLayers().get("Image Layer");
        this.objectLayer = this.tiledMap.getLayers().get("Object Layer");
        this.mapObjects = this.objectLayer.getObjects();
        createBoard();
        createPieces();
        this.displayHint = false;

        this.helvetique = this.skin.getFont("helvetique");
        this.bannerText = "";
        this.bannerX = 10.0f;
        this.bannerY = (this.imageLayer.getTextureRegion().getRegionHeight() * SCALE) + helvetique.getCapHeight()*1.5f;

        this.tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, SCALE);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        ScreenUtils.clear(1.0f, 1.0f, 1.0f, 1.0f);
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.camera.update();

        this.tiledMapRenderer.setView(camera);
        this.tiledMapRenderer.render();

        this.batch.begin();
        this.graphicalBoard.draw(this.batch);
        drawPieces(this.blackPieces);
        drawPieces(this.whitePieces);
        drawHint();
        this.helvetique.draw(this.batch, this.bannerText, this.bannerX, this.bannerY);
        this.batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        if (this.game != null) {
            this.game.dispose();
        }
        if (this.stage != null) {
            this.stage.dispose();
        }
        if (this.skin != null) {
            this.skin.dispose();
        }
        if (this.batch != null) {
            this.batch.dispose();
        }
        if (this.tiledMap != null) {
            this.tiledMap.dispose();
        }
        if (this.helvetique != null) {
            this.helvetique.dispose();
        }
    }

    private void createBoard() {
        MapObject boardLocation = this.mapObjects.get("Board");
        float boardX = (float) boardLocation.getProperties().get("x") * SCALE;
        float boardY = (float) boardLocation.getProperties().get("y") * SCALE;
        float boardHeight = (float) boardLocation.getProperties().get("height") * SCALE;
        float boardWidth = (float) boardLocation.getProperties().get("width") * SCALE;
        this.graphicalBoard = new GraphicalBoard(new Board(), blackSquare, whiteSquare, boardX, boardY, boardWidth, boardHeight);

    }

    private void createPieces() {
        this.blackPieces = new ArrayList<GraphicalPiece>();
        this.whitePieces = new ArrayList<GraphicalPiece>();
        for (MapObject object: this.mapObjects) {
            if (!object.getName().equals("Board")) {
                String colour = (String) object.getProperties().get("Colour");
                String name = (String) object.getProperties().get("Piece Name");
                float gamepieceX = (float) object.getProperties().get("x");
                float gamepieceY = (float) object.getProperties().get("y");
                if (colour.equals("Black")) {
                    this.blackPieces.add(new GraphicalPiece(new Piece(name), blackSquare, gamepieceX * SCALE, gamepieceY * SCALE));
                } else if (colour.equals("White")) {
                    this.whitePieces.add(new GraphicalPiece(new Piece(name), whiteSquare, gamepieceX * SCALE, gamepieceY * SCALE));
                }
            }
        }
    }

    private void drawPieces(ArrayList<GraphicalPiece> pieces) {
        for (GraphicalPiece p: pieces) {
            p.draw(this.batch);
        }
    }

    private void drawHint() {
        if (this.displayHint && this.hintPiece != null) {
            if (TimeUtils.timeSinceMillis(this.hintStartTime) < HINT_DISPLAY_TIME) {
                if ((TimeUtils.timeSinceMillis(this.hintStartTime) / HINT_BLINK_TIME) % 2 == 0) {
                    this.hintPiece.draw(this.batch, this.graphicalBoard);
                }
            } else {
                this.hintPiece = null;
                this.displayHint = false;
                if (this.originalPiece != null) {
                    this.originalPiece.show();
                    this.originalPiece = null;
                }
            }
        } else if (this.originalPiece != null) {
            this.originalPiece.show();
            this.originalPiece = null;
        }
 
    }

    public void setBanner(String message) {
        this.bannerText = new String(message);
    }

    public void hint(MoveInterface move, int playerNumber) {
        PieceInterface piece = move.getPiece();
        int row = move.getCoordinate().getRow();
        int column = move.getCoordinate().getColumn();
        if (this.originalPiece != null) {
            this.originalPiece.show();
        }
        if (playerNumber == 1) {
            this.originalPiece = getPiece(piece.getShape(), blackPieces);
            this.originalPiece.hide();
            this.hintPiece = new GraphicalHintPiece(piece, blackSquare, row, column);
            this.hintStartTime = TimeUtils.millis();
        } else if (playerNumber == 2) {
            this.originalPiece = getPiece(piece.getShape(), whitePieces);
            this.originalPiece.hide();
            this.hintPiece = new GraphicalHintPiece(piece, whiteSquare, row, column);
            this.hintStartTime = TimeUtils.millis();
        }
        this.displayHint = true;
    }

    public void stopHint() {
        this.displayHint = false;
    }

    private GraphicalPiece getPiece(String pieceName, ArrayList<GraphicalPiece> pieces) {
        for (GraphicalPiece graphicalPiece: pieces) {
            if (graphicalPiece.piece.getShape().equals(pieceName)) {
                return graphicalPiece;
            }
        }
        return null;
    }

}
