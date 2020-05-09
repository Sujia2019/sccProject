package com.example.sccproject.music;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.sccproject.R;

/**
 * Created by alienware on 2020/4/10.
 */

public class MusicServer extends Service {
    private MediaPlayer mediaPlayer;


    @Override
    public void onDestroy() {
        mediaPlayer.stop();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implement");
    }

    @Override
    public void onCreate(){
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer = MediaPlayer.create(MusicServer.this, R.raw.dance);
        mediaPlayer.start();
        return super.onStartCommand(intent, flags, startId);
    }


}
