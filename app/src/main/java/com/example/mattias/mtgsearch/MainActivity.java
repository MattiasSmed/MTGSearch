package com.example.mattias.mtgsearch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button searchActivityButton;
    CheckBox white, blue, black, red, green;

    EditText nameField, textField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchActivityButton = (Button) findViewById(R.id.searchActivityButton);
        nameField = (EditText) findViewById(R.id.cardName);
        nameField.setHint("Enter cardname ex. Blasphemous Act");
        textField = (EditText) findViewById(R.id.cardText);
        textField.setHint("Enter card text ex. \"Deal 3 damage\"");
        white = (CheckBox) findViewById(R.id.white);
        blue = (CheckBox) findViewById(R.id.blue);
        black = (CheckBox) findViewById(R.id.black);
        red = (CheckBox) findViewById(R.id.red);
        green = (CheckBox) findViewById(R.id.green);
        searchActivityButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(!TextUtils.isEmpty(nameField.getText()) ||!TextUtils.isEmpty(textField.getText())) {
                    Bundle b = new Bundle();
                    b.putString("Name",nameField.getText().toString());
                    if(!TextUtils.isEmpty(textField.getText())){
                        b.putString("Text",textField.getText().toString());
                    }
                    if(white.isChecked())
                        b.putString("White","{W}");
                    if(blue.isChecked())
                        b.putString("Blue","{U}");
                    if(black.isChecked())
                        b.putString("Black","{B}");
                    if(red.isChecked())
                        b.putString("Red","{R}");
                    if(green.isChecked())
                        b.putString("Green","{G}");
                    Intent intent = new Intent(view.getContext(), SearchActivity.class);
                    intent.putExtras(b);
                    startActivity(intent);
                }
            }
        });
    }
}
