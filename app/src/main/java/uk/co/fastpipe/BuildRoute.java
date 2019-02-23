package uk.co.fastpipe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class BuildRoute extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_build_route);
        Intent intent=getIntent();

        String firstStaion=  intent.getStringExtra(FastPipeActivity.FIRST_STATION);
        String secondStaion=  intent.getStringExtra(FastPipeActivity.SECOND_STATION);
    }
}

