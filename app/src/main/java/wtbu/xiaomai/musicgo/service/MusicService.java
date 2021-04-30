package wtbu.xiaomai.musicgo.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import wtbu.xiaomai.musicgo.MainActivity;
import wtbu.xiaomai.musicgo.MusicPlayerActivity;
import wtbu.xiaomai.musicgo.R;

public class MusicService extends Service {

    private MediaPlayer mediaPlayer;
    private Timer timer;

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化音乐播放器
        mediaPlayer = MediaPlayer.create(this, R.raw.elektronomia_limitless);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    public MusicService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    Uri musicUri;
    public void setMusicUri(Uri musicUri) {
        this.musicUri = musicUri;
        //设置音频文件到MediaPlayer对象中
        try {
            mediaPlayer.setDataSource(getApplicationContext(),musicUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //让MediaPlayer对象准备，用这个方法防止加载时耗时导致anr
        mediaPlayer.prepareAsync();
    }

    public void playMusic(){
        try{
            mediaPlayer.reset();//重置音乐播放器
            //加载多媒体文件
            mediaPlayer = MediaPlayer.create(this, R.raw.elektronomia_limitless);
            mediaPlayer.start();//播放
            addTimer();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void pauseMusic(){
        mediaPlayer.pause();
    }

    public void continuePlay(){
        mediaPlayer.start();
    }
    public void seekTo(int progress){
        //设置音乐的播放位置
        mediaPlayer.seekTo(progress);
    }

    public void addTimer(){
        if(timer == null){
            timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    if(mediaPlayer == null)return;
                    //获取歌曲总时长
                    int duration = mediaPlayer.getDuration();
                    //获取播放进度
                    int currentPosition = mediaPlayer.getCurrentPosition();
                    //创建消息对象
                    Message msg = MainActivity.handler.obtainMessage();
                    //将音乐的总时长和播放进度封装至消息对象中
                    Bundle bundle = new Bundle();
                    bundle.putInt("duration",duration);
                    bundle.putInt("currentPosition",currentPosition);
                    msg.setData(bundle);
                    //将消息发送到主线程的消息列表中
                    MainActivity.handler.sendMessage(msg);
                }
            };
            //调用Timer对象的schedule()方法执行TimerTask任务
            //该方法有三个参数：
            // 1，要执行的任务；
            // 2，开始执行计时任务的5毫秒后第一梯次执行task任务；
            // 3，每隔500毫秒执行一次
            timer.schedule(task,5,500);
        }
    }

    public class MyBinder extends Binder{
        public MusicService getService(){
            return MusicService.this;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //释放mediaPlayer
        mediaPlayer.release();
    }
}
