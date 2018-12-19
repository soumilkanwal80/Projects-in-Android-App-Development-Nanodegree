package com.example.android.miwok;

public class Word {
    private String defaultTranslation, miwokTranslation;
    private int imageResourceID;
    private int musicResourceID;
    // Initializing Class
    Word(String sDefaultTranslation, String sMiwokTranslation, int sMusicResourceID){
        defaultTranslation = sDefaultTranslation;
        miwokTranslation = sMiwokTranslation;
        imageResourceID = 0;
        musicResourceID = sMusicResourceID;
    }
    //Again ;(
    Word(String sDefaultTranslation, String sMiwokTranslation, int sImageResourceID, int sMusicResourceID){
        defaultTranslation = sDefaultTranslation;
        miwokTranslation = sMiwokTranslation;
        imageResourceID = sImageResourceID;
        musicResourceID = sMusicResourceID;
    }

    public int getImageResourceID(){ return imageResourceID; }
    public String getTranslation(){
        return miwokTranslation;
    }
    public String getDefaultTranslation(){
        return defaultTranslation;
    }
    public int getMusicResourceID(){ return musicResourceID; }


}
