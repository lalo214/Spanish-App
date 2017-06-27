package com.example.android.miwok;

/**
 * Created by Eduardo on 5/25/2017.
 */

public class Word {

    //the english word
    private String mDefaultTranslation;
    // miwok word
    private String mMiwokTranslation;

    // holds the resource ID for the iamge
    // initialized to NO_IMAGE_PROVIDED
    // to later check if there is an image or not
    private int mImageResourceID = NO_IMAGE_PROVIDED;

    // set to -1 because -1 is out of scope of possible values
    private static final int NO_IMAGE_PROVIDED = -1;

    // holds resource ID for the sound file
    private int mSoundResourceId;

    // Constructor for word objects with no image from the phrases activity
    public Word(String defaultTranslation, String miwokTranslation, int soundID)
    {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mSoundResourceId = soundID;
    }

    public Word(String defaultTranslation, String miwokTranslation, int resourceId, int soundID)
    {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mImageResourceID = resourceId;
        mSoundResourceId = soundID;
    }

    public String getDefaultTranslation() { return mDefaultTranslation; }

    public String getMiwokTranslation()
    {
        return mMiwokTranslation;
    }


    public int getImageResourceID()
    {
        return mImageResourceID;
    }

    public boolean hasImage()
    {
        // return true if mImageResourceID does not equal NO_IMAGE_PROVIDED
        return mImageResourceID != NO_IMAGE_PROVIDED;
    }

    public int getSoundResourceID()
    {
        return mSoundResourceId;
    }

    // returns readable format of object to be viewed in log for debugging purposes
    @Override
    public String toString() {
        return "Word{" +
                "mDefaultTranslation='" + mDefaultTranslation + '\'' +
                ", mMiwokTranslation='" + mMiwokTranslation + '\'' +
                ", mImageResourceID=" + mImageResourceID +
                ", mSoundResourceId=" + mSoundResourceId +
                '}';
    }
}
