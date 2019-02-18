package dk.kea.androidgame.martin.myfirstgameengine;

public class TestGame extends GameEngine
{
    @Override
    public Screen createStartScreen()
    {
        return new TestScreen(this);
    }
}
