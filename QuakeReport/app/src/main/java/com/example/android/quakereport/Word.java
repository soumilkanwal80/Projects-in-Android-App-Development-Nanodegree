package com.example.android.quakereport;


public class Word {
    double mMagnitude;
    private String mLocation;
    long mTime;
    String mURL;

    Word(double magnitude, String location, long sTime, String URL){
        mMagnitude = magnitude;
        mLocation = location;
        mTime = sTime;
        mURL = URL;
    }

    public double getMagnitude(){ return mMagnitude; }
    public String getLocation(){ return mLocation; }
    public long getTime(){ return mTime; }
    public String getURL(){ return mURL; }
}
