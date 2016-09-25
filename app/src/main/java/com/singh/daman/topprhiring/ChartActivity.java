package com.singh.daman.topprhiring;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.echo.holographlibrary.PieGraph;
import com.echo.holographlibrary.PieSlice;

import java.util.ArrayList;

public class ChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        int hiring = 0, hackathon = 0, bot = 0;

        ArrayList<String> category = getIntent().getStringArrayListExtra("DATA");

        for (String events: category) {
            switch (events) {
                case "HACKATHON":
                    hackathon++;
                    break;
                case "BOT":
                    bot++;
                    break;
                case "HIRING":
                    hiring++;
                    break;
            }
        }
        PieGraph pg = (PieGraph) findViewById(R.id.graph);

        PieSlice slice = new PieSlice();
        slice.setColor(getResources().getColor(R.color.color1));
        slice.setSelectedColor(getResources().getColor(R.color.colorAccent));
        slice.setValue(hackathon);
        slice.setTitle("Hackathon");
        pg.addSlice(slice);
        slice = new PieSlice();
        slice.setColor(getResources().getColor(R.color.color2));
        slice.setValue(hiring);
        slice.setTitle("Hiring");
        pg.addSlice(slice);
        slice = new PieSlice();
        slice.setColor(getResources().getColor(R.color.color3));
        slice.setValue(bot);
        slice.setTitle("Bot");
        pg.addSlice(slice);

    }
}
