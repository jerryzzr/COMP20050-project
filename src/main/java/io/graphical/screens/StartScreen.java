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
public class StartScreen extends ScreenAdapter {

    BlokusDuoGame game;
    Stage stage;
    Skin skin;
    SpriteBatch batch;
    Texture backgroundImage;
    Table table;

    public StartScreen(BlokusDuoGame game) {
        this.game = game;
        this.stage = game.stage;
        this.skin = game.skin;
        this.batch = new SpriteBatch();
        this.backgroundImage = new Texture(Gdx.files.internal("startscreen.jpeg"));
        this.table = new Table();
        
        table.setFillParent(true);
        TextButton startButton = new TextButton("Start", skin);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                activateSetupScreen();
            };
        });

        TextButton rulesButton = new TextButton("Rules", skin);
        rulesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                activateRulesScreen();
            };
        });

        table.add(startButton).pad(game.DEFAULT_PADDING);
        table.row();
        table.add(rulesButton).pad(game.DEFAULT_PADDING);

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

    private void activateSetupScreen() {
        this.game.activateSetupScreen();
    }

    private void activateRulesScreen() {
        this.game.activateRulesScreen();
    }

}
