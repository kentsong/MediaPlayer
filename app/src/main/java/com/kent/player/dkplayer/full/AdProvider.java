package com.kent.player.dkplayer.full;

/**
 * Created by songzhukai on 2020-01-06.
 */
public interface AdProvider {

    boolean checkAdEnable();
    String getFilePath();
    long getSkipSecond();
    long getCountDown();

}
