package com.sw14_xp_05.pinkee;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by ASUS on 14.05.2014.
 */
public class BackgroundActivity extends ActionBarActivity {

    private Button buttonBlue;
    private Button buttonLightBlue;
    private Button buttonWhite;
    private Button buttonRed;
    private Button buttonYellow;
    private Button buttonLigthGreen;
    private Button buttonGreen;
    private Button buttonOrange;
    private Button buttonPink;
    private Button buttonPurple;

    public static final String MyPreferences = "MyPrefs";
    public static final String Mycolor = "Mycolor";
    public static final String Mytheme = "Mytheme";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_background);

        final SharedPreferences   sharedpreferences = getSharedPreferences(MyPreferences, Context.MODE_PRIVATE);

        String Colorchange = "#FFFFFF";

        if(sharedpreferences.contains(Mycolor))
        {
            Colorchange = sharedpreferences.getString(Mycolor, "");
        }

        View background = getWindow().getDecorView();
        background.setBackgroundColor(Color.parseColor(Colorchange));

        this.buttonBlue = (Button) this.findViewById(R.id.buttonBlue);
        this.buttonLightBlue = (Button) this.findViewById(R.id.buttonLightBlue);
        this.buttonWhite = (Button) this.findViewById(R.id.buttonWhite);
        this.buttonRed = (Button) this.findViewById(R.id.buttonRed);
        this.buttonYellow = (Button) this.findViewById(R.id.buttonYellow);
        this.buttonLigthGreen = (Button) this.findViewById(R.id.buttonLightGreen);
        this.buttonGreen = (Button) this.findViewById(R.id.buttonGreen);
        this.buttonOrange = (Button) this.findViewById(R.id.buttonOrange);
        this.buttonPink = (Button) this.findViewById(R.id.buttonPink);
        this.buttonPurple = (Button) this.findViewById(R.id.buttonPurple);

        this.buttonBlue.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Editor editor = sharedpreferences.edit();
                editor.remove(Mytheme);
                editor.putString(Mycolor, "#ff1c49ff");
                editor.commit();
                finish();
            }
        });

        this.buttonLightBlue.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.putString(Mycolor, "#1dd2ff");
                editor.commit();
                finish();
            }
        });

        this.buttonWhite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Editor editor = sharedpreferences.edit();
                editor.remove(Mytheme);
                editor.putString(Mycolor, "#ffffff");
                editor.commit();
                finish();
            }
        });

        this.buttonRed.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Editor editor = sharedpreferences.edit();
                editor.remove(Mytheme);
                editor.putString(Mycolor, "#FF1C49");
                editor.commit();
                finish();
            }
        });

        this.buttonYellow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Editor editor = sharedpreferences.edit();
                editor.remove(Mytheme);
                editor.putString(Mycolor, "#fffc49");
                editor.commit();
                finish();
            }
        });

        this.buttonLigthGreen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Editor editor = sharedpreferences.edit();
                editor.remove(Mytheme);
                editor.putString(Mycolor, "#55ff5c");
                editor.commit();
                finish();
            }
        });

        this.buttonGreen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Editor editor = sharedpreferences.edit();
                editor.remove(Mytheme);
                editor.putString(Mycolor, "#19aa07");
                editor.commit();
                finish();
            }
        });

        this.buttonOrange.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Editor editor = sharedpreferences.edit();
                editor.remove(Mytheme);
                editor.putString(Mycolor, "#ff6a31");
                editor.commit();
                finish();
            }
        });

        this.buttonPink.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Editor editor = sharedpreferences.edit();
                editor.remove(Mytheme);
                editor.putString(Mycolor, "#ff1fe9");
                editor.commit();
                finish();
            }
        });

        this.buttonPurple.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Editor editor = sharedpreferences.edit();
                editor.putString(Mycolor, "#b166ff");
                editor.remove(Mytheme);
                editor.commit();
                finish();
            }
        });
    }

    protected void onResume()
    {
        super.onResume();

        final SharedPreferences sharedpreferences = getSharedPreferences(MyPreferences, Context.MODE_PRIVATE);
        String Colorchange = "#FFFFFF";
        Integer Themechange = 0;

        View background = getWindow().getDecorView();

        if(sharedpreferences.contains(Mycolor))
        {
            Colorchange = sharedpreferences.getString(Mycolor, "");

            background.setBackgroundColor(Color.parseColor(Colorchange));
        }

        if(sharedpreferences.contains(Mytheme))
        {
            Themechange = sharedpreferences.getInt(Mytheme,0);

            background.setBackgroundResource(Themechange);
        }


    }

}
