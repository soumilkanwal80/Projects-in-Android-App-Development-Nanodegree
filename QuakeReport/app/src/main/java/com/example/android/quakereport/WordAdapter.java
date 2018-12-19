package com.example.android.quakereport;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;

import android.graphics.drawable.GradientDrawable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {

    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }

    public WordAdapter(Activity context, ArrayList<Word> words){
        super(context, 0, words);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Word currWord = getItem(position);
        View listItemView = convertView;

        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_tem, parent, false);
        }

        double mag = currWord.getMagnitude();

        DecimalFormat formatter = new DecimalFormat("0.0");
        String output = formatter.format(mag);

        TextView magTextView = (TextView) listItemView.findViewById(R.id.mag_text_view);
        magTextView.setText(output);

        GradientDrawable magnitudeCircle = (GradientDrawable) magTextView.getBackground();
        int magnitudeColor = getMagnitudeColor(mag);
        magnitudeCircle.setColor(magnitudeColor);

        String location = currWord.getLocation();
        String offSetLocation, primaryLocation;
        if(location.contains("of ")) {
            String[] parts = location.split("of ");
            offSetLocation = parts[0] + "of";
            primaryLocation = parts[1];
        }

        else{
            offSetLocation = "Near the";
            primaryLocation = location;
        }


        TextView locationTextView = (TextView) listItemView.findViewById(R.id.location_text_view);
        locationTextView.setText(offSetLocation);

        TextView countryTextView = (TextView) listItemView.findViewById(R.id.country_text_view);
        countryTextView.setText(primaryLocation);


        long time = currWord.getTime();

        Date dateObject = new Date(time);
        DateFormat dataFormatter = new SimpleDateFormat("MMM dd, yyyy");
        String dateToDisplay = dataFormatter.format(dateObject);
        SimpleDateFormat timeFormatter = new SimpleDateFormat("h::mm a");
        String timeToDisplay = timeFormatter.format(dateObject);

        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date_text_view);
        dateTextView.setText(dateToDisplay);

        TextView timeTextView = (TextView) listItemView.findViewById(R.id.time);
        timeTextView.setText(timeToDisplay);

        return listItemView;
    }
}
