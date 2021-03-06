package com.dueeeke.videocontroller;

import com.dueeeke.videocontroller.component.CompleteView;
import com.dueeeke.videocontroller.component.ErrorView;
import com.dueeeke.videocontroller.component.GestureView;
import com.dueeeke.videocontroller.component.LiveControlView;
import com.dueeeke.videocontroller.component.PrepareView;
import com.dueeeke.videocontroller.component.TitleView;
import com.dueeeke.videocontroller.component.VodControlView;
import com.dueeeke.videoplayer.R;
import com.dueeeke.videoplayer.controller.BaseVideoController;
import com.dueeeke.videoplayer.player.VideoView;
import com.dueeeke.videoplayer.util.L;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 直播/点播控制器
 * Created by dueeeke on 2017/4/7.
 */

public class AdVideoController extends BaseVideoController implements View.OnClickListener {

    protected ProgressBar mLoadingProgress;

    private TextView mCountDownTv;
    private CountDownTimer mCountDownTimer;
    private AdVideoViewListener mListener;
    private boolean mSkipEnable;

    public AdVideoController(@NonNull Context context) {
        this(context, null);
    }

    public AdVideoController(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AdVideoController(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.player_ad_controller;
    }

    @Override
    protected void initView() {
        super.initView();
        mLoadingProgress = findViewById(R.id.loading);
        mCountDownTv = findViewById(R.id.tv_countdown);
        setFocusable(true);
    }

    public void setListener(AdVideoViewListener listener) {
        mListener = listener;
    }

    public void setCountDown(int second) {
        mCountDownTimer = new CountDownTimer(second * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long second = millisUntilFinished / 1000;
                if (second <= 3) {
                    mCountDownTv.setText("广告 " + second + "秒 (按Enter可跳過)");
                    mSkipEnable = true;
                } else {
                    mCountDownTv.setText("广告 " + second + "秒");
                }
            }

            @Override
            public void onFinish() {
                if (mListener != null) {
                    mListener.onFinish();
                }
            }
        }
        .start();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.lock) {
            mControlWrapper.toggleLockState();
        }
    }

    @Override
    protected void onPlayerStateChanged(int playerState) {
        super.onPlayerStateChanged(playerState);
        switch (playerState) {
            case VideoView.PLAYER_NORMAL:
                L.e("PLAYER_NORMAL");
                setLayoutParams(new LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                break;
            case VideoView.PLAYER_FULL_SCREEN:
                L.e("PLAYER_FULL_SCREEN");
                break;
        }

    }

    @Override
    protected void onPlayStateChanged(int playState) {
        super.onPlayStateChanged(playState);
        switch (playState) {
            //调用release方法会回到此状态
            case VideoView.STATE_IDLE:
                L.e("STATE_IDLE");
                mLoadingProgress.setVisibility(GONE);
                break;
            case VideoView.STATE_PLAYING:
                L.e("STATE_PLAYING");
                mLoadingProgress.setVisibility(GONE);
                break;
            case VideoView.STATE_PAUSED:
                L.e("STATE_PAUSED");
                mLoadingProgress.setVisibility(GONE);
                break;
            case VideoView.STATE_PREPARING:
                L.e("STATE_PREPARING");
                mLoadingProgress.setVisibility(VISIBLE);
                break;
            case VideoView.STATE_PREPARED:
                L.e("STATE_PREPARED");
                mLoadingProgress.setVisibility(GONE);
                break;
            case VideoView.STATE_ERROR:
                L.e("STATE_ERROR");
                mLoadingProgress.setVisibility(GONE);
                break;
            case VideoView.STATE_BUFFERING:
                L.e("STATE_BUFFERING");
                mLoadingProgress.setVisibility(VISIBLE);
                break;
            case VideoView.STATE_BUFFERED:
                L.e("STATE_BUFFERED");
                mLoadingProgress.setVisibility(GONE);
                break;
            case VideoView.STATE_PLAYBACK_COMPLETED:
                L.e("STATE_PLAYBACK_COMPLETED");
                mLoadingProgress.setVisibility(GONE);
                break;
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        Log.d("ttt", ""+event.getKeyCode());
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_CENTER || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                if(mSkipEnable && mListener!= null){
                    mListener.onFinish();
                }
            }
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean onBackPressed() {
        if (mControlWrapper.isFullScreen()) {
            return stopFullScreen();
        }
        return super.onBackPressed();
    }

    public interface AdVideoViewListener {
        void onFinish();
    }
}
