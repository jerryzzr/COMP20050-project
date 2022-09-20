package ui.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import static com.badlogic.gdx.scenes.scene2d.Touchable.disabled;

public class StartScreen extends ScreenAdapter {

    private Skin skin;
    private Stage stage;
    private BlokusGame blokusGame;
    private Table table;

    public StartScreen(BlokusGame blokusGame) {

        this.blokusGame = blokusGame;
        this.skin = blokusGame.getSkin();
        this.stage = blokusGame.getStage();

        table = new Table();
        table.setFillParent(true);

        Label label = new Label("Blokus Duo", skin);
        table.add(label);

        table.row();
        VerticalGroup verticalGroup = new VerticalGroup();
        verticalGroup.pad(10.0f);
        verticalGroup.space(10.0f);

        HorizontalGroup horizontalGroup = new HorizontalGroup();
        horizontalGroup.align(Align.left);
        horizontalGroup.padLeft(10.0f);
        horizontalGroup.padRight(11.0f);
        horizontalGroup.space(10.0f);

        label = new Label("Enter the name of Player 1", skin);
        horizontalGroup.addActor(label);

        Image image = new Image(skin, "game_square_black");
        horizontalGroup.addActor(image);

        TextField player1Name = new TextField(null, skin);
        player1Name.setMessageText("John");
        horizontalGroup.addActor(player1Name);
        verticalGroup.addActor(horizontalGroup);

        horizontalGroup = new HorizontalGroup();
        horizontalGroup.align(Align.left);
        horizontalGroup.padLeft(10.0f);
        horizontalGroup.padRight(10.0f);
        horizontalGroup.space(10.0f);

        label = new Label("Enter the name of Player 2", skin);
        horizontalGroup.addActor(label);

        image = new Image(skin, "game_square_white");
        horizontalGroup.addActor(image);

        TextField player2Name = new TextField(null, skin);
        player2Name.setMessageText("Mary");
        horizontalGroup.addActor(player2Name);
        verticalGroup.addActor(horizontalGroup);
        table.add(verticalGroup);

        table.row();
        TextButton textButton = new TextButton("Start Game", skin);
        table.add(textButton);
        textButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String p1Name = player1Name.getText();
                String p2Name = player2Name.getText();
                if (p1Name.isEmpty()) {
                    p1Name = player1Name.getMessageText();

                }
                if (p2Name.isEmpty()) {
                    p2Name = player2Name.getMessageText();
                }
                blokusGame.getPipe().println(p1Name);
                blokusGame.getPipe().println(p2Name);
            };
        });
    }

    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0.349f, 0.792f, 0.956f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    public void hide() {
        stage.clear();
        super.hide();
    }

    public void show() {
        stage.addActor(table);
        super.show();
    }

    public void resize(int width, int height) {
        super.resize(width,height);
        stage.getViewport().update(width, height, true);
    }

}
