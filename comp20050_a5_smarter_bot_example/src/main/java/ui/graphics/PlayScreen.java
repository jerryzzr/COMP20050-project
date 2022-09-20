package ui.graphics;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import ui.graphics.actors.CustomLabel;
import ui.graphics.actors.GraphicalBoard;
import ui.graphics.actors.GraphicalGamePiece;

public class PlayScreen extends ScreenAdapter {

    private Skin skin;
    private Stage stage;
    private BlokusGame blokusGame;
    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;
    private OrthographicCamera camera;
    private Array<GraphicalGamePiece> graphicalGamePieces;

    public PlayScreen(BlokusGame blokusGame) {

        this.blokusGame = blokusGame;
        this.skin = blokusGame.getSkin();
        this.stage = blokusGame.getStage();
        this.tiledMap = blokusGame.getTiledMap();
        this.tiledMapRenderer = blokusGame.getTiledMapRenderer();
        this.camera = blokusGame.getCamera();
        this.graphicalGamePieces = blokusGame.getGraphicalGamePieces();
    }

    public void render(float delta) {
        super.render(delta);
        ScreenUtils.clear(0.349f, 0.792f, 0.956f, 1.0f);

        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        stage.act();
        stage.draw();
    }

    public void hide() {
        super.hide();
        stage.clear();
    }

    public void show() {
        super.show();
        for (GraphicalGamePiece graphicalGamePiece : graphicalGamePieces) {
            stage.addActor(graphicalGamePiece);
        }
        CustomLabel customLabel = blokusGame.getCustomLabel();
        customLabel.setVisible(true);
        stage.addActor(customLabel);
        GraphicalBoard graphicalBoard = blokusGame.getGraphicalBoard();
        graphicalBoard.setVisible(true);
        stage.addActor(graphicalBoard);
    }

    public void resize(int width, int height) {
        super.resize(width,height);
        stage.getViewport().update(width, height, true);
    }

}
