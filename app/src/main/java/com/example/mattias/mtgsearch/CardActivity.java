package com.example.mattias.mtgsearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CardActivity extends AppCompatActivity {
    TextView name, manaCost, cardText, powerAndToughness;
    String power;
    String toughness;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_card2);
        name = (TextView) findViewById(R.id.name);
        manaCost = (TextView) findViewById(R.id.manaCost);
        cardText = (TextView) findViewById(R.id.text);
        powerAndToughness = (TextView) findViewById(R.id.strength);
        name.setTextSize(20);
        manaCost.setTextSize(20);
        cardText.setTextSize(20);
        powerAndToughness.setTextSize(20);
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            name.setText(extras.getString("name"));
            manaCost.setText(extras.getString("manaCost"));
            cardText.setText(extras.getString("text"));
            power = extras.getString("power");
            toughness = extras.getString("toughness");
            if(power != null) {
                powerAndToughness.setText(toughness + "/" + power);
            }else
                powerAndToughness.setText("");

        }
    }
}
