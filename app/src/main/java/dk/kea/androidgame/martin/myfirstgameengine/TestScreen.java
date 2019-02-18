package dk.kea.androidgame.martin.myfirstgameengine;

import android.graphics.Bitmap;
import android.graphics.Color;

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
        if (gameEngine.isTouchDown(0))
        {
            x = gameEngine.getTouchX(0);
            y = gameEngine.getTouchY(0);
        }
        gameEngine.clearFrameBuffer(Color.BLUE);
        gameEngine.drawBitmap(bitmap, x, y);
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
