package dk.kea.androidgame.martin.myfirstgameengine;

import android.graphics.Bitmap;

public class TestScreen extends Screen
{
    int x = 0;
    int y = 0;
    Bitmap bitmap;

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

        }
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
