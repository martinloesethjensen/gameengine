package dk.kea.androidgame.martin.myfirstgameengine.touch;

import dk.kea.androidgame.martin.myfirstgameengine.core.Pool;

public class TouchEventPool extends Pool<TouchEvent>
{
    @Override
    protected TouchEvent newItem()
    {
        return new TouchEvent();
    }
}
