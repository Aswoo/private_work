package com.aswoo.android.bleex.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by seungwoo on 2017-11-03.
 */

public class Repo {
    Main main;

    private List<Weather> weather = new ArrayList<Weather>();

    public List<Weather> getWeather() {
        return weather;
    }
    public Main getMain(){
        return main;
    }
}
