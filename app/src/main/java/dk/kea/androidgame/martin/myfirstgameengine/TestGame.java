package dk.kea.androidgame.martin.myfirstgameengine;

import dk.kea.androidgame.martin.myfirstgameengine.core.GameEngine;
import dk.kea.androidgame.martin.myfirstgameengine.core.Screen;

public class TestGame extends GameEngine
{
    @Override
    public Screen createStartScreen()
    {
        return new TestScreen(this);
    }
}
