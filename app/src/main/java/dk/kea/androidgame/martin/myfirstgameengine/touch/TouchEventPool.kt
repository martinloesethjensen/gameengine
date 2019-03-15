package dk.kea.androidgame.martin.myfirstgameengine.touch

import dk.kea.androidgame.martin.myfirstgameengine.core.Pool

class TouchEventPool : Pool<TouchEvent>() {
    override fun newItem(): TouchEvent {
        return TouchEvent()
    }
}
