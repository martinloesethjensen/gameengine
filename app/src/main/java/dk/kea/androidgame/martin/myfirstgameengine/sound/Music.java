package dk.kea.androidgame.martin.myfirstgameengine.sound;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.provider.MediaStore;

import java.io.IOException;

public class Music implements MediaPlayer.OnCompletionListener
{
    private MediaPlayer mediaPlayer; // MediaPlayer doing the music playback
    private boolean isPrepared = false; // is the MediaPlayer ready?

    public Music(AssetFileDescriptor assetFileDescriptor)
    {
        this.mediaPlayer = new MediaPlayer();
        try
        {
            mediaPlayer.setDataSource(
                    assetFileDescriptor.getFileDescriptor(),
                    assetFileDescriptor.getStartOffset(),
                    assetFileDescriptor.getLength()
            );
            mediaPlayer.prepare();
            this.isPrepared = true;
            mediaPlayer.setOnCompletionListener(this);
        } catch (IOException e)
        {
            throw new RuntimeException("Could not open the music file descriptor: " + assetFileDescriptor);
        }
    }

    public void dispose()
    {
        if (mediaPlayer.isPlaying())
        {
            mediaPlayer.stop();
        }
        mediaPlayer.release();
    }

    public boolean isPlaying()
    {
        return mediaPlayer.isPlaying();
    }

    public boolean isStopped()
    {
        return !this.isPrepared;
    }

    public void pause()
    {
        if (mediaPlayer.isPlaying()) mediaPlayer.pause();
    }

    public void play()
    {
        if (mediaPlayer.isPlaying()) return;
        try
        {
            synchronized (this)
            {
                if (!isPrepared) mediaPlayer.prepare();
                mediaPlayer.start();
            }
        } catch (IllegalStateException e)
        {
            e.printStackTrace();
            throw new RuntimeException("Music class: You are trying to play from a wrong MediaPlayer.");
        } catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException("MediaPlayer play error.");
        }
    }

    public boolean isLooping()
    {
        return mediaPlayer.isLooping();
    }

    public void setLooping(boolean loop)
    {
        this.mediaPlayer.setLooping(loop);
    }

    public void setVolume(float volume)
    {
        this.mediaPlayer.setVolume(volume, volume);
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer)
    {
        synchronized (this)
        {
            this.isPrepared = false;
        }
    }
}
