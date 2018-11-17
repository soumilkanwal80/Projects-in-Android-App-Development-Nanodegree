package com.example.android.courtcounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int team_a_score = 0, team_b_score = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayForTeamA(team_a_score);
        displayForTeamB(team_b_score);
    }

    public void teamAPlusTwo(View view){
        team_a_score = team_a_score + 2;
        displayForTeamA(team_a_score);
    }

    public void teamAPlusThree(View view){
        team_a_score = team_a_score + 3;
        displayForTeamA(team_a_score);
    }

    public void teamAFreeThrow(View view){
        team_a_score = team_a_score + 1;
        displayForTeamA(team_a_score);
    }

    public void displayForTeamA(int score){
        TextView scoreView = (TextView) findViewById(R.id.team_a_score_text_view);
        scoreView.setText(String.valueOf(score));
    }

    public void teamBPlusTwo(View view){
        team_b_score = team_b_score + 2;
        displayForTeamB(team_b_score);
    }

    public void teamBPlusThree(View view){
        team_b_score = team_b_score + 3;
        displayForTeamB(team_b_score);
    }

    public void teamBFreeThrow(View view){
        team_b_score = team_b_score + 1;
        displayForTeamB(team_b_score);
    }

    public void displayForTeamB(int score){
        TextView scoreView = (TextView) findViewById(R.id.team_b_score_text_view);
        scoreView.setText(String.valueOf(score));
    }

    public void resetTeams(View view){
        team_a_score = 0;
        team_b_score = 0;
        displayForTeamA(team_a_score);
        displayForTeamB(team_b_score);
    }

}
