package dk.kea.androidgame.martin.myfirstgameengine;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public abstract class GameEngine extends AppCompatActivity implements Runnable
{
    private Thread mainLoopThread;
    private State state = State.PAUSED;
    private List<State> stateChanges = new ArrayList<>();

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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    public void run()
    {
        while (true)
        {
            synchronized (stateChanges)
            {
                for (State stateFromList : stateChanges)
                {
                    this.state = stateFromList;
                    if (this.state == State.DISPOSED)
                    {
                        Log.d("GameEngine", "State changed to DISPOSED");
                        return; //Killing the thread
                    }
                    if (this.state == State.PAUSED)
                    {
                        Log.d("GameEngine", "State is Changed to PAUSED");
                        return;
                    }
                    if (this.state == State.RESUME)
                    {
                        Log.d("GameEngine", "State is changed to RESUME");
                    }
                    if (this.state == State.RUNNING)
                    {
                        Log.d("GameEngine", "State is changed to RUNNING");
                    }
                }
                stateChanges.clear();
            }
        }
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
        mainLoopThread = new Thread(this);
        mainLoopThread.start();
        synchronized (stateChanges)
        {

        }
    }
}
