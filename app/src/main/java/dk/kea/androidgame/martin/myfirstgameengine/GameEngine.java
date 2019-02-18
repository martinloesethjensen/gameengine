package dk.kea.androidgame.martin.myfirstgameengine;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

public abstract class GameEngine extends AppCompatActivity
{
    public abstract Screen createStartScreen();

    public void setScreen(Screen screen)
    {
    }

    public Bitmap loadBitmap(String fileName)
    {
        return null;
    }

    public void clearFrameBuffer(int color)
    {

    }

    public boolean isTouchDown(int pointer)
    {
        return false;
    }

    public int getTouchX(int pointer)
    {
        return 0;
    }

    public int getTouchY(int pointer)
    {
        return 0;
    }

    public void drawBitmap(Bitmap bitmap, int x, int y)
    {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }
}
