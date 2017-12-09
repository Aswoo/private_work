package com.aswoo.android.bleex;

import android.location.Location;

/**
 * Created by seungwoo on 2017-10-25.
 */

public class Application extends android.app.Application {

    private static String degreeStatus;
    private static String currentTemp;
    private static Location currentLocation;

    public static Location getCurrentLocation() {
        return currentLocation;
    }

    public static void setCurrentLocation(Location currentLocation) {
        Application.currentLocation = currentLocation;
    }

    public static String getWriteCharicteristic() {
        return writeCharicteristic;
    }

    public static void setWriteCharicteristic(String writeCharicteristic) {
        Application.writeCharicteristic = writeCharicteristic;
    }

    private static String writeCharicteristic;

    public static String getCurrentTemp() {
        return currentTemp;
    }

    public static void setCurrentTemp(String currentTemp) {
        Application.currentTemp = currentTemp;
    }

    public static String getDegreeStatus() {
        return degreeStatus;
    }

    public static void setDegreeStatus(String degreeStatus) {
        Application.degreeStatus = degreeStatus;
    }
}
