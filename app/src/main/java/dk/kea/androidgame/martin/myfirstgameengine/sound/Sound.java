package dk.kea.androidgame.martin.myfirstgameengine.sound;

import android.media.SoundPool;

public class Sound
{
    private SoundPool soundPool;
    private int soundId;

    public Sound(SoundPool soundPool, int soundId)
    {
        this.soundPool = soundPool;
        this.soundId = soundId;
    }

    public void play(float volume)
    {
        soundPool.play(soundId, volume, volume, 0, 0, 1);
    }

    public void dispose()
    {
        soundPool.unload(soundId);
    }
}
