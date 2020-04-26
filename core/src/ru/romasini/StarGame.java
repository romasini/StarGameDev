package ru.romasini;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class StarGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture backScreen;
	TextureRegion region;

	@Override
	public void create () {
		batch = new SpriteBatch();
		backScreen = new Texture("backScreenSpace.jpg");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(backScreen, -200,-200);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
        backScreen.dispose();
	}
}
