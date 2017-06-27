package com.example.android.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhrasesFragment extends Fragment
{
    // global variable to hold media resource
    private MediaPlayer mMediaPlayer;

    // handles the audio focus
    private AudioManager mAudioManager;

    // triggered when media file stop playing
    private MediaPlayer.OnCompletionListener mCompletionListener =
            new MediaPlayer.OnCompletionListener()
            {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer)
                {
                    // release the MediaPlayer
                    releaseMediaPlayer();
                }
            };

    // This listener is triggered whenever audio focus changes
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener()
            {
                @Override
                public void onAudioFocusChange(int focusChange)
                {
                    if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK)
                    {
                        // pause the playback and then reset to the beginning
                        mMediaPlayer.pause();
                        mMediaPlayer.seekTo(0);
                    }

                    else if(focusChange == AudioManager.AUDIOFOCUS_GAIN)
                        //focus is regained and auido continues
                        mMediaPlayer.start();

                    else if(focusChange == AudioManager.AUDIOFOCUS_LOSS)
                        //focus is lost and resources are cleaned up
                        releaseMediaPlayer();
                }
            };

    public PhrasesFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        // needed to request audio focus
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        // Aray list of custom Word objects
        // Must be final for inline function OnItemClickListener()

        final ArrayList<Word> phrases = new ArrayList<Word>();
        phrases.add(new Word("Thank you", "Gracias", R.raw.thank_you));
        phrases.add(new Word("Oh my god", "Oh Dios mío", R.raw.oh_my_god));
        phrases.add(new Word("Good morning", "Buenos días", R.raw.good_morning));
        phrases.add(new Word("Good afternoon", "Buenas tardes", R.raw.good_afternoon));
        phrases.add(new Word("Good evening", "Buenas noches", R.raw.good_evening));
        phrases.add(new Word("What's your name?", "¿Cómo se llama usted?", R.raw.whats_your_name));
        phrases.add(new Word("Nice to meet you", "Mucho gusto", R.raw.nice_to_meet_you));
        phrases.add(new Word("How are you?", "¿Cómo está usted?", R.raw.how_are_you));



        WordAdapter itemsAdapter = new WordAdapter(getActivity(),phrases, R.color.category_phrases);

        // find the ListView object.
        ListView listView = (ListView) rootView.findViewById(R.id.list);

        // display the listView
        listView.setAdapter(itemsAdapter);

        // set custom onclicklistener for list item
        // this is an asynchronous callback
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                // release if there is currently something in media player
                // so new sound can be played
                releaseMediaPlayer();

                // get the word object at the given position that the user clicked on
                Word word = phrases.get(position);

                // request auido focus
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
                {
                    // create the mediaplayer with the audio source associated with current word
                    mMediaPlayer = MediaPlayer.create(getActivity(), word.getSoundResourceID());

                    // start media player
                    mMediaPlayer.start();

                    // set up a listener on the mediaPlayer
                    // this way it can be released once it is done playing
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });

        return rootView;
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer()
    {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            // abandon auido focus once the playback is over
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

    @Override
    public void onStop()
    {
        super.onStop();

        // when activity is stopped, release media player resources
        releaseMediaPlayer();
    }
}
