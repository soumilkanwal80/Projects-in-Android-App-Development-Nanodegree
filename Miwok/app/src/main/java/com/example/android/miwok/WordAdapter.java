package com.example.android.miwok;

import android.app.Activity;
import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {

    private int mColorResourceID;
    public WordAdapter(Activity context, ArrayList<Word> words, int colorResourceID){
        super(context, 0, words);
        mColorResourceID = colorResourceID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Word currWord = getItem(position);
        View listItemView = convertView;

        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent,false);
        }

        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.default_text_view);
        defaultTextView.setText(currWord.getDefaultTranslation());

        TextView miwokTextView = (TextView) listItemView.findViewById(R.id.miwok_text_view);
        miwokTextView.setText(currWord.getTranslation());

        ImageView imageView = listItemView.findViewById(R.id.image_view);
        imageView.setImageResource(currWord.getImageResourceID());

        if(currWord.getImageResourceID() == 0){
            imageView.setVisibility(View.GONE);
        }

        View textContainer = listItemView.findViewById(R.id.text_container);
        int color = ContextCompat.getColor(getContext(), mColorResourceID);
        textContainer.setBackgroundColor(color);

        return listItemView;
    }
}
