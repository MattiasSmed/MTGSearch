package com.example.mattias.mtgsearch;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class SearchActivity extends AppCompatActivity {
    JSONObject allCards;
    ArrayList foundCards;
    String searchedString;
    ListView test;
    String searchedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        foundCards = new ArrayList();
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            searchedString = extras.getString("Name");
            if(extras.getString("Text")!=null)
                searchedText = extras.getString("Text");
        }

        searchThread(searchedString,searchedText);

    }

    // Thread that handles searching.

    private void searchThread(final String searchedString, final String searchedText) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                try {
                    allCards = new JSONObject(loadJSONFromAsset("AllCards"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

             //   test = (ListView) findViewById(R.id.list);
                    Iterator<String> iter = allCards.keys();
                    while (iter.hasNext()) {
                        String key = iter.next();
                        try {
                            if (key.toLowerCase().contains(searchedString.toLowerCase())) {
                                JSONObject value = allCards.getJSONObject(key);
                                if (searchedText != null) {
                                    String valueText = value.getString("text");
                                    if (valueText.toLowerCase().contains(searchedText.toLowerCase())) {
                                        foundCards.add(value);
                                    }
                                } else {
                                    foundCards.add(value);
                                }

                            } else if(searchedText!=null){
                            JSONObject value = allCards.getJSONObject(key);
                            if(value.getString("text").contains(searchedText)) {
                                foundCards.add(value);
                            }
                        }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                if(foundCards.isEmpty()) {
                    Toast toast = Toast.makeText(getApplicationContext(),"No cards were found",Toast.LENGTH_LONG);
                    toast.show();
                    finish();
                    // Stops the rest of the code from running
                    return;
                }
                ArrayList<String> sortedCards = new ArrayList<String>();
                for(int i = 0; i < foundCards.size(); i++){
                    sortedCards.add(foundCards.get(i).toString());
                }
                Collections.sort(sortedCards);
                for (int i = 0; i < foundCards.size(); i++) {
                    try {
                        final JSONObject jo = new JSONObject(sortedCards.get(i).toString());

                        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout);

                        LinearLayout row = new LinearLayout(getApplicationContext());
                        row.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                        Button btnTag = new Button(getApplicationContext());
                        btnTag.setLayoutParams(new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT,ActionBar.LayoutParams.FILL_PARENT));
                        btnTag.setText(jo.getString("name"));
                        btnTag.setId(i);

                        row.addView(btnTag);

                        btnTag.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {
                                    Bundle b = new Bundle();
                                try {
                                    b.putString("name", jo.getString("name"));
                                    b.putString("manaCost", jo.getString("manaCost"));
                                    String a = jo.getString("name");
                                    b.putString("text", jo.getString("text"));
                                    b.putString("toughness", jo.getString("toughness"));
                                    b.putString("power", jo.getString("power"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Intent intent = new Intent(view.getContext(), CardActivity.class);
                                    intent.putExtras(b);
                                    startActivity(intent);

                            }
                        });
                        layout.addView(row);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }




                }


            }

        });
    }

    public String loadJSONFromAsset(final String fileName) {


        String json = null;

        try {

            InputStream is = getAssets().open(fileName + ".json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");




        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return json;
    }
}
