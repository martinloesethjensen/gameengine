package dk.kea.androidgame.martin.myfirstgameengine.touch;

import android.view.MotionEvent;
import android.view.View;

import java.util.List;


public class MultiTouchHandler implements TouchHandler, View.OnTouchListener
{
    private boolean[] isTouched = new boolean[20]; // store the first 20 touches
    private int[] touchX = new int[20];
    private int[] touchY = new int[20];
    private List<TouchEvent> touchEventBuffer; // buffer with touch events
    private TouchEventPool touchEventPool;

    public MultiTouchHandler(View view, List<TouchEvent> touchEventBuffer, TouchEventPool touchEventPool)
    {
        view.setOnTouchListener(this);
        this.touchEventBuffer = touchEventBuffer;
        this.touchEventPool = touchEventPool;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event)
    {
        TouchEvent touchEvent = null;
        int action = event.getAction() & MotionEvent.ACTION_MASK;
        int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
        int pointerId = event.getPointerId(pointerIndex);

        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
                touchEvent = touchEventPool.obtains();
                touchEvent.type = TouchEvent.TouchEventType.DOWN;
                touchEvent.pointer = pointerId;
                touchEvent.x = (int) event.getX();
                touchX[pointerId] = touchEvent.x;
                touchEvent.y = (int) event.getY();
                touchY[pointerId] = touchEvent.y;
                isTouched[pointerId] = true;
                synchronized (touchEventBuffer)
                {
                    touchEventBuffer.add(touchEvent);
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_CANCEL:
                touchEvent = touchEventPool.obtains();
                touchEvent.type = TouchEvent.TouchEventType.UP;
                touchEvent.pointer = pointerId;
                touchEvent.x = (int) event.getX();
                touchX[pointerId] = touchEvent.x;
                touchEvent.y = (int) event.getY();
                touchY[pointerId] = touchEvent.y;
                isTouched[pointerId] = false;
                synchronized (touchEventBuffer)
                {
                    touchEventBuffer.add(touchEvent);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int pointerCount = event.getPointerCount();
                synchronized (touchEventBuffer)
                {
                    for (int i = 0; i < pointerCount; i++)
                    {
                        touchEvent = touchEventPool.obtains();
                        touchEvent.type = TouchEvent.TouchEventType.DRAGGED;
                        touchEvent.pointer = pointerId;
                        touchEvent.x = (int) event.getX();
                        touchX[pointerId] = touchEvent.x;
                        touchEvent.y = (int) event.getY();
                        touchY[pointerId] = touchEvent.y;
                        isTouched[pointerId] = true;
                        touchEventBuffer.add(touchEvent);
                    }
                }
                break;
        }
        return true;
    }

    @Override
    public boolean isTouchDown(int pointer)
    {
        return isTouched[pointer];
    }

    @Override
    public int getTouchX(int pointer)
    {
        return touchX[pointer];
    }

    @Override
    public int getTouchY(int pointer)
    {
        return touchY[pointer];
    }
}
