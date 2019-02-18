package dk.kea.androidgame.martin.myfirstgameengine;

public abstract class Screen
{
    protected final GameEngine gameEngine;

    protected Screen(GameEngine gameEngine)
    {
        this.gameEngine = gameEngine;
    }

    public abstract void update(float deltaTime);

    public abstract void pause();

    public abstract void resume();
}
