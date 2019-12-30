package com.kent.player.dkplayer.full;

import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videoplayer.player.VideoView;
import com.kent.player.R;
import com.kent.player.dkplayer.DKPlayerActivity;
import com.kent.player.dkplayer.DebugActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by songzhukai on 2019-12-30.
 */
public class FullDKPlayerActivity extends DebugActivity {

    private static final String VOD_Url = "http://vfx.mtime.cn/Video/2019/03/14/mp4/190314223540373995.mp4";

    TextView mTvCountDown;
    private CountDownTimer mCountDownTimer;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_full_dkplayer;
    }

    @Override
    protected void initView() {
        super.initView();
        mVideoView = findViewById(R.id.player);
        mTvCountDown = findViewById(R.id.tv_countdown);

        StandardVideoController controller = new StandardVideoController(this);
        mVideoView.setVideoController(controller);

//        String url = Environment.getExternalStorageDirectory().getPath() + "/ad/le.mp4";
        String url = "dev/le.mp4";
        Log.d("FullDKPlayerActivity", "url=" + url);
        mVideoView.setUrl(url);
        mVideoView.start();


        mCountDownTimer = new CountDownTimer(5 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTvCountDown.setText("广告 " + millisUntilFinished / 1000 + "秒");
            }

            @Override
            public void onFinish() {
                finish();
                overridePendingTransition(0,R.anim.anim_reduce);
            }
        }.start();

    }

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, FullDKPlayerActivity.class);
        activity.startActivity(intent);
    }
}
