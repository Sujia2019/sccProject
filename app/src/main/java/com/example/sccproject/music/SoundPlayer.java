package com.example.sccproject.music;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import com.example.sccproject.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alienware on 2020/4/10.
 */

public class SoundPlayer {
    private static MediaPlayer music;

    private static SoundPool soundPool;

    private static boolean musicSt = true;//音乐开关
    private static boolean soundSt = true;//音效开关

    private static Context context;

    private static final int[] musicId = {R.raw.dance};
    private static Map<Integer,Integer> soundMap ;
    //音效资源id与加载过后的音源id的映射关系表

    public static void init(Context c){
        context = c ;

    }

    @SuppressLint("UseSparseArrays")
    private static void initSound(){
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC,100);

        soundMap = new HashMap<>();
        soundMap.put(R.raw.dance,soundPool.load(context,R.raw.dance,1));
//        soundMap.put()
    }

    private static void initMusic(){
        music = MediaPlayer.create(context,musicId[0]);
        music.setLooping(true);
    }

    public static void playSound(int resId){
        if(soundSt == false){
            return;
        }

        Integer soundId = soundMap.get(resId);
        if(soundId!=null)
            soundPool.play(soundId,1,1,1,0,1);
    }

    public static void pauseMusic(){
        if(music.isPlaying())
            music.pause();
    }

    public static void startMusic(){
        if(musicSt)
            music.start();
    }

    /**
     * 设置音乐开关
     * @param musicSt
     */
    public static void setMusicSt(boolean musicSt){
        SoundPlayer.musicSt = musicSt;
        if(musicSt)
            music.start();
        else
            music.stop();
    }

    /**
     * 获得音乐开关状态
     * @return
     */
    public static boolean isMusicSt(){
        return musicSt;
    }

    public static boolean isSoundSt(){
        return soundSt;
    }

}
