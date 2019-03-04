package dk.kea.androidgame.martin.myfirstgameengine.touch;

/**
 * Class with responsibilities a touch needs
 */
public class TouchEvent
{
    TouchEventType type; //The type of the event
    int x; //The x-coordinate of the event
    int y; //The y-coordinate of the event
    int pointer; //The pointer id (from the Android system)

    /***
     * Enum of touch event types
     */
    public enum TouchEventType
    {
        DOWN,
        UP,
        DRAGGED
    }
}
