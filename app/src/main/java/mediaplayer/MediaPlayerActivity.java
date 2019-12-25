package mediaplayer;

import com.tianchi.myapplication.R;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ProgressBar;

import java.io.IOException;


/**
 * MediaPlayer 视频播放 sample
 * 播放气状态流程介绍：https://developer.android.com/reference/android/media/MediaPlayer
 * 参考文章：https://www.cnblogs.com/yxx123/p/5720907.html
 */

public class MediaPlayerActivity extends AppCompatActivity {

    private SurfaceView surfaceView;
    private MediaPlayer player;
    private SurfaceHolder holder;
    private ProgressBar progressBar;
    //视频连结可能失效
    String url_1 = "http://demo2-live.tianchiapi.com/hls/5c77d04ae5dad946cff88d6f.m3u8";
    String url_2 = "http://demo2-live.tianchiapi.com/hls/5c78a8a1e5dad94294c3cf23.m3u8";
    String url_3 = "http://demo2-live.tianchiapi.com/hls/5c77d0b3e5dad94294c3c3e3.m3u8";
    String url_4 = "http://demo2-live.tianchiapi.com/hls/5c77d8b0e5dad946cff88d72.m3u8";
    String currUrl = url_1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mediaplayer);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

    }

    @Override
    protected void onPause() {
        super.onPause();
        player.stop();
        player.reset();
        player.release();
    }

    @Override
    protected void onResume() {
        super.onResume();

        initPlayer();
    }

    private void initPlayer(){
        player = new MediaPlayer();
        try {
            player.setDataSource(this, Uri.parse(currUrl));
            holder = surfaceView.getHolder();
            holder.addCallback(new MyCallBack());
            player.prepareAsync();
//            player.prepare();
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    progressBar.setVisibility(View.INVISIBLE);
                    player.start();
                    player.setLooping(true);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void changeChannel(String url){
        currUrl = url;
        player.stop();
        player.reset();
//        player.release();
        try {
            player.setDataSource(this, Uri.parse(currUrl));
            holder = surfaceView.getHolder();
//            holder.addCallback(new MyCallBack());
            player.prepareAsync();
//            player.prepare();
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    progressBar.setVisibility(View.INVISIBLE);
                    player.start();
                    player.setLooping(true);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(KeyEvent.ACTION_DOWN == event.getAction()){
            switch (event.getKeyCode()){
                case KeyEvent.KEYCODE_DPAD_UP:
                    changeChannel(url_1);
                    break;
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    changeChannel(url_2);
                    break;
                case KeyEvent.KEYCODE_DPAD_DOWN:
                    changeChannel(url_3);
                    break;
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    changeChannel(url_4);
                    break;
            }
        }
        return super.dispatchKeyEvent(event);
    }

    private class MyCallBack implements SurfaceHolder.Callback {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            player.setDisplay(holder);
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, MediaPlayerActivity.class);
        activity.startActivity(intent);
    }
}
