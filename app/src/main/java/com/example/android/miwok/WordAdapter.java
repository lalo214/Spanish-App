package com.example.android.miwok;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Eduardo on 5/25/2017.
 */

public class WordAdapter extends ArrayAdapter<Word> {

    // used to set the color on the list items
    private int mResourceID;

    // Constructor
    //list of Word objects to display in a list
    public WordAdapter(Activity context, ArrayList<Word> numbers, int resourceId)
    {
        //calls constructor of superclass with a 0 because the views
        // will be manually inflated
        super(context, 0, numbers);
        mResourceID = resourceId;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // check if exisitng view is being reused, otherswise inflate the view
        View listItemView = convertView;
        if(listItemView == null)
        {
            listItemView = LayoutInflater.from
                    (getContext()).inflate(R.layout.list_items, parent, false);

        }

        // get like Word object located at this position in the list
        Word currentWord = getItem(position);

        // Find the TextView in the list.items.xml layout with the ID miwok_text_view
        TextView miwokTextView = (TextView) listItemView.findViewById(R.id.miwok_text_view);

        // Get the current miwok word and set it to the current textView
        miwokTextView.setText(currentWord.getMiwokTranslation());

        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.default_text_view);
        defaultTextView.setText(currentWord.getDefaultTranslation());

        // ImageView for picture resource
        ImageView picture = (ImageView) listItemView.findViewById(R.id.image);

        // only create the ImageView in the adapter if there is an image passed into Word
        if(currentWord.hasImage())
        {
            picture.setImageResource(currentWord.getImageResourceID());
        }
        else
            // Important to use GONE and not INVISIBLE so the view does not take up space
            picture.setVisibility(View.GONE);

        // this will set the color to the list view
        View textContainer = listItemView.findViewById(R.id.text_container);
        textContainer.setBackgroundResource(mResourceID);

        return listItemView;
    }
}
