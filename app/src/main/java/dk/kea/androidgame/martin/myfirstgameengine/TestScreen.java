package dk.kea.androidgame.martin.myfirstgameengine;

import android.graphics.Bitmap;
import android.graphics.Color;

import dk.kea.androidgame.martin.myfirstgameengine.core.GameEngine;
import dk.kea.androidgame.martin.myfirstgameengine.core.Screen;

public class TestScreen extends Screen
{
    private int x = 0;
    private int y = 0;
    private Bitmap bitmap;

    protected TestScreen(GameEngine gameEngine)
    {
        super(gameEngine);
        bitmap = gameEngine.loadBitmap("bob.png");
    }

    @Override
    public void update(float deltaTime)
    {
        gameEngine.clearFrameBuffer(Color.GREEN);
        if (gameEngine.isTouchDown(0))
        {
            x = gameEngine.getTouchX(0);
            y = gameEngine.getTouchY(0);
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
