/**
 * echo
 * 20201034
 */
package io.graphical.screens;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
public class RulesScreen extends ScreenAdapter {

    BlokusDuoGame game;
    Stage stage;
    Skin skin;
    SpriteBatch batch;
    Texture backgroundImage;
    Table table;

    final String RULES;

    public RulesScreen(BlokusDuoGame game) {
        this.game = game;
        this.stage = game.stage;
        this.skin = game.skin;
        this.batch = new SpriteBatch();
        this.backgroundImage = new Texture(Gdx.files.internal("startscreen.jpeg"));
        this.table = new Table();
        RULES = getRules();
        
        table.setFillParent(true);
        TextArea textArea = new TextArea(RULES, skin);
        textArea.setDisabled(true);
        textArea.setPrefRows(36);
        ScrollPane pane = new ScrollPane(textArea, skin);
        pane.setForceScroll(false, true);
        pane.setOverscroll(false, true);

        TextButton textButton = new TextButton("back", skin);
        textButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                activateStartScreen();
            };
        });

        table.add(pane).pad(game.DEFAULT_PADDING).prefSize(400f, 300f);
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

    private String getRules() {
        String result;
        try {
            result = new String(Files.readAllBytes(Paths.get("src/main/resources/rules.txt")));
        } catch (IOException e) {
            result  = "Cannot load rules";
        }
        return result;
    }

    private void activateStartScreen() {
        this.game.activateStartScreen();
    }

}
