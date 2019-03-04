package dk.kea.androidgame.martin.myfirstgameengine.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dk.kea.androidgame.martin.myfirstgameengine.touch.MultiTouchHandler;
import dk.kea.androidgame.martin.myfirstgameengine.touch.TouchEvent;
import dk.kea.androidgame.martin.myfirstgameengine.touch.TouchEventPool;
import dk.kea.androidgame.martin.myfirstgameengine.touch.TouchHandler;

public abstract class GameEngine extends AppCompatActivity implements Runnable, TouchHandler, SensorEventListener
{
    private Thread mainLoopThread;
    private State state = State.PAUSED;
    private static final List<State> STATE_CHANGES = new ArrayList<>();
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private Canvas canvas = null;
    private Screen screen = null;
    private Bitmap offScreenSurface;
    private MultiTouchHandler touchHandler;
    private TouchEventPool touchEventPool = new TouchEventPool();
    private List<TouchEvent> touchEventBuffer = new ArrayList<>();
    private List<TouchEvent> touchEventCopied = new ArrayList<>();
    private float[] accelerometer = new float[3]; // to hold the g-forces in three dimensions, x, y, and z

    public abstract Screen createStartScreen();

    public void setScreen(Screen screen)
    {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getSupportActionBar()).hide(); // hides the action bar
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // Prepared variables used for drawing on screen
        surfaceView = new SurfaceView(this);
        setContentView(surfaceView); // places view on the physical screen
        surfaceHolder = surfaceView.getHolder();
//        Log.d("GameEngine class", "We just finished the onCreate() method");
        screen = createStartScreen();
        if (surfaceView.getWidth() > surfaceView.getHeight())
        {
            setOffScreenSurface(480, 320);
        } else setOffScreenSurface(320, 480);
        touchHandler = new MultiTouchHandler(surfaceView, touchEventBuffer, touchEventPool);
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() != 0)
        {
            Sensor accelerometer = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    public void setOffScreenSurface(int width, int height)
    {
        if (offScreenSurface != null) offScreenSurface.recycle();
        offScreenSurface = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        canvas = new Canvas(offScreenSurface);
    }

    public Bitmap loadBitmap(String fileName)
    {
        InputStream inputStream = null;
        Bitmap bitmap = null;
        try
        {
            inputStream = getAssets().open(fileName);
            bitmap = BitmapFactory.decodeStream(inputStream);
            if (bitmap == null)
            {
                throw new RuntimeException("Couldn't load bitmap from file " + fileName);
            }
            return bitmap;
        } catch (IOException ioe)
        {
            throw new RuntimeException("Couldn't load bitmap from assets folder: " + fileName);
        } finally
        {
            if (inputStream != null)
            {
                try
                {
                    inputStream.close();
                } catch (IOException ioe)
                {
                    throw new RuntimeException("Couldn't close the inputstream");
                }
            }
        }
    }

    public int getFrameBufferWidth()
    {
        return offScreenSurface.getWidth();
    }

    public int getFrameBufferHeight()
    {
        return offScreenSurface.getHeight();
    }

    public void drawBitmap(Bitmap bitmap, int x, int y)
    {
        if (canvas != null) canvas.drawBitmap(bitmap, x, y, null);
    }

    Rect source = new Rect();
    Rect destination = new Rect();

    public void drawBitmap(Bitmap bitmap, int x, int y, int sourceX, int sourceY, int sourceWidth, int sourceHeight)
    {
        if (canvas != null)
        {
            source.left = sourceX;
            source.top = sourceY;
            source.right = sourceWidth + sourceWidth;
            source.bottom = sourceHeight + sourceHeight;

            destination.left = x;
            destination.top = y;
            destination.right = x + sourceWidth;
            destination.bottom = y + sourceHeight;

            canvas.drawBitmap(bitmap, source, destination, null);
        }
    }


    public void clearFrameBuffer(int color)
    {
        canvas.drawColor(color);
    }

    public boolean isTouchDown(int pointer)
    {
        return touchHandler.isTouchDown(pointer);
    }

    /**
     * @return int as a scaled x
     */
    public int getTouchX(int pointer)
    {
        return (int) (((float) touchHandler.getTouchX(pointer) * (float) offScreenSurface.getWidth()) / (float) surfaceView.getWidth());
    }

    /**
     * @return int as a scaled y
     */
    public int getTouchY(int pointer)
    {
        return (int) (((float) touchHandler.getTouchY(pointer) * (float) offScreenSurface.getHeight()) / (float) surfaceView.getHeight());
    }

    public float[] getAccelerometer()
    {
        return accelerometer;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent)
    {
        System.arraycopy(sensorEvent.values, 0, accelerometer, 0, 3);
    }

    public void run()
    {
        while (true)
        {
            synchronized (STATE_CHANGES)
            {
                for (int i = 0; i < STATE_CHANGES.size(); i++)
                {
                    this.state = STATE_CHANGES.get(i);
                    if (this.state == State.DISPOSED)
                    {
                        Log.d("GameEngine", "State changed to Disposed");
                        return;
                    }
                    if (this.state == State.PAUSED)
                    {
                        Log.d("GameEngine", "State changed to Pause");
                        return;
                    }
                    if (this.state == State.RESUMED)
                    {
                        Log.d("GameEngine", "State changed to Resumed");
                        this.state = State.RUNNING;
                    }
                } // end of for loop
                STATE_CHANGES.clear();
                if (this.state == State.RUNNING)
                {
                    Log.d("GameEngine running", "" + surfaceHolder.getSurface().isValid());
                    if (!surfaceHolder.getSurface().isValid())
                    {
                        continue;
                    }
                    Canvas canvas = surfaceHolder.lockCanvas();
                    // all drawing happens here
                    //canvas.drawColor(Color.rgb(0, 0, 255));
                    if (screen != null) screen.update(0);
                    source.left = 0;
                    source.top = 0;
                    source.right = offScreenSurface.getWidth() - 1;
                    source.bottom = offScreenSurface.getHeight() - 1;
                    destination.left = 0;
                    destination.top = 0;
                    destination.right = surfaceView.getWidth();
                    destination.bottom = surfaceView.getHeight();
                    canvas.drawBitmap(offScreenSurface, source, destination, null);
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        } // end of while loop
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        synchronized (STATE_CHANGES)
        {
            if (isFinishing())
            {
                STATE_CHANGES.add(STATE_CHANGES.size(), State.DISPOSED);
            } else STATE_CHANGES.add(STATE_CHANGES.size(), State.PAUSED);
        }
        if (isFinishing())
        {
            ((SensorManager) getSystemService(Context.SENSOR_SERVICE)).unregisterListener(this);
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mainLoopThread = new Thread(this);
        mainLoopThread.start(); // starts the thread
        synchronized (STATE_CHANGES)
        {
            STATE_CHANGES.add(STATE_CHANGES.size(), State.RESUMED);
        }
    }
}
