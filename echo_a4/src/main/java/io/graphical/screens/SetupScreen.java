/**
 * echo
 * 20201034
 */
package io.graphical.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;

import io.graphical.BlokusDuoGame;

/**
 * @author agbod
 *
 */
public class SetupScreen extends ScreenAdapter {

    BlokusDuoGame game;
    Stage stage;
    Skin skin;
    SpriteBatch batch;
    Texture backgroundImage;
    Table table;

    TextField player1Field;
    TextField player2Field;

    public SetupScreen(BlokusDuoGame game) {
        this.game = game;
        this.stage = game.stage;
        this.skin = game.skin;
        this.batch = new SpriteBatch();
        this.backgroundImage = new Texture(Gdx.files.internal("startscreen.jpeg"));
        this.table = new Table();
        table.setFillParent(true);

        Label player1Label = new Label("Enter the name of Player 1:", skin);
        Label player2Label = new Label("Enter the name of Player 2:", skin);

        player1Field = new TextField(null, skin);
        player2Field = new TextField(null, skin);
        player1Field.setMessageText("John");
        player2Field.setMessageText("Mary");
        player1Field.addListener(new InputListener() {
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if(keycode == Input.Keys.ENTER || keycode == Input.Keys.DOWN) {
                    stage.setKeyboardFocus(player2Field);
                }
                return false;
            }
        });
        player2Field.addListener(new InputListener() {
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if(keycode == Input.Keys.ENTER) {
                    enterNames();
                }
                if(keycode == Input.Keys.UP) {
                    stage.setKeyboardFocus(player1Field);
                }
                return false;
            }
        });


        TextButton textButton = new TextButton("OK", skin);
        textButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                enterNames();
            };
        });

        table.add(player1Label).pad(game.DEFAULT_PADDING);
        table.add(player1Field).pad(game.DEFAULT_PADDING);
        table.row();
        table.add(player2Label).pad(game.DEFAULT_PADDING);
        table.add(player2Field).pad(game.DEFAULT_PADDING);
        table.row();
        table.add(textButton).pad(game.DEFAULT_PADDING);
    }

    @Override
    public void show() {
        super.show();
        this.stage.addActor(table);
    }

    @Override
    public void hide() {
        super.hide();
        this.stage.clear();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        this.batch.begin();
        this.batch.draw(backgroundImage, 0, 0, (float) BlokusDuoGame.WIDTH, (float) BlokusDuoGame.HEIGHT);
        this.batch.end();
        stage.act(delta);
        stage.draw();
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
        if (this.backgroundImage != null) {
            this.backgroundImage.dispose();
        }
    }

    private void enterNames() {
        String player1Name = player1Field.getText();
        String player2Name = player2Field.getText();
        if (player1Name.isEmpty()) {
            player1Name = player1Field.getMessageText();
        }
        if (player2Name.isEmpty()) {
            player2Name = player2Field.getMessageText();
        }

        // send name to game control thread
        game.ioStream.println(player1Name);
        game.ioStream.println(player2Name);
    };

}
