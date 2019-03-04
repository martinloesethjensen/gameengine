package dk.kea.androidgame.martin.myfirstgameengine.touch;

public interface TouchHandler
{
    boolean isTouchDown(int pointer);
    int getTouchX(int pointer);
    int getTouchY(int pointer);
}
