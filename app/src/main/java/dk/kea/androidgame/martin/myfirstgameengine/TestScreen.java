package dk.kea.androidgame.martin.myfirstgameengine;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.util.Log;

import dk.kea.androidgame.martin.myfirstgameengine.core.GameEngine;
import dk.kea.androidgame.martin.myfirstgameengine.core.Screen;
import dk.kea.androidgame.martin.myfirstgameengine.sound.Music;
import dk.kea.androidgame.martin.myfirstgameengine.sound.Sound;

public class TestScreen extends Screen
{
    private float x = 0;
    private int y = 100;
    private Bitmap bitmap;
    private Sound sound;
    private final float FULL_SOUND = 1;
    private Music backgroundMusic;
    private boolean isPlaying = false;

    protected TestScreen(GameEngine gameEngine)
    {
        super(gameEngine);
        bitmap = gameEngine.loadBitmap("bob.png");
        sound = gameEngine.loadSound("blocksplosion.wav");
        backgroundMusic = gameEngine.loadMusic("music.ogg");
        isPlaying = true;
    }

    @Override
    public void update(float deltaTime)
    {

        System.out.println("Delta time: " + deltaTime);
//        Log.d("GameLoop", "FPS: " + gameEngine.getFramePerSecond());
        gameEngine.clearFrameBuffer(Color.GREEN);

        x = x + 250 * deltaTime;
        if (x > 320 + bitmap.getWidth())
        {
            x = 0 - bitmap.getWidth();
        }

        if (gameEngine.isTouchDown(0))
        {
            x = gameEngine.getTouchX(0);
            y = gameEngine.getTouchY(0);
            sound.play(FULL_SOUND);

            if (backgroundMusic.isPlaying())
            {
                backgroundMusic.pause();
                isPlaying = false;
            } else
            {
                backgroundMusic.play();
                isPlaying = true;
            }
        }

        float x_acc = gameEngine.getAccelerometer()[0];
        float y_acc = -1 * gameEngine.getAccelerometer()[1];
        x_acc = (float) (gameEngine.getFrameBufferWidth() / 2) - (((x_acc / 10) * gameEngine.getFrameBufferWidth()) / 2);
        y_acc = (float) (gameEngine.getFrameBufferHeight() / 2) - (((y_acc / 10) * gameEngine.getFrameBufferHeight()) / 2);

        gameEngine.clearFrameBuffer(Color.GREEN);
        gameEngine.drawBitmap(bitmap, x - 64, y - 64);
        gameEngine.drawBitmap(bitmap, (int) x_acc - 64, (int) y_acc - 64);
        //gameEngine.drawBitmap(bitmap, 200, 300, 64, 64, 64, 64);
    }

    @Override
    public void pause()
    {
    }

    @Override
    public void resume()
    {
    }
}
