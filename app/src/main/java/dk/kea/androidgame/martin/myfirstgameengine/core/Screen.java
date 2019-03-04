package dk.kea.androidgame.martin.myfirstgameengine.core;

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
