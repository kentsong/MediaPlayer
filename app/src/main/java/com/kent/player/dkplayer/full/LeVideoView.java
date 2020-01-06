package com.kent.player.dkplayer.full;

import com.dueeeke.videoplayer.player.VideoView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;

/**
 * Created by songzhukai on 2020-01-06.
 */
public class LeVideoView extends VideoView {

    public LeVideoView(Context context) {
        super(context);
    }

    public LeVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LeVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return super.dispatchKeyEvent(event);
    }
}
