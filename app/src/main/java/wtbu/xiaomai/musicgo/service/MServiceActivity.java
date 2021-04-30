package wtbu.xiaomai.musicgo.service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import wtbu.xiaomai.musicgo.R;

public class MServiceActivity extends AppCompatActivity {

    private MusicService musicService;
    private boolean isPlaying = false;

    private final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MyBinder myBinder = (MusicService.MyBinder) service;
            musicService = myBinder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_service);
        /**
         * 传入3个参数
         *
         *
         *  flags:声明了绑定Service的一些配置
         *  BIND_AUTO_CREATE：若绑定服务时服务未启动，则会自动启动服务。注意，这种情况下服务的
         *  onStartCommand仍未被调用（它只会在显式调用startService时才会被调用）
         * */
        bindService(
                new Intent(this,MusicService.class),
                connection,
                Context.BIND_AUTO_CREATE
        );
/*
        findViewById(R.id.btn_play).setOnClickListener(
                v -> {
                    if(musicService != null){
                        if(isPlaying){
                            musicService.pauseMusic();
                            v.setBackgroundResource(R.drawable.control_play);
                        }else {
                            musicService.playMusic();
                            v.setBackgroundResource(R.drawable.control_pause_dark);
                        }
                        isPlaying = !isPlaying;
                    }
                }
        );*/
    }
}