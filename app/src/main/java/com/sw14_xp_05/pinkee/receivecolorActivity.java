package com.sw14_xp_05.pinkee;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by ASUS on 04.06.2014.
 */
public class receivecolorActivity extends ActionBarActivity {

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
    public static final String MyRcolor = "MyRcolor";
    public static final String MyScolor = "MyScolor";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receivecolor);

        final SharedPreferences sharedpreferences = getSharedPreferences(MyPreferences, Context.MODE_PRIVATE);

        String Colorchange = "#FFFFFF";

        if(sharedpreferences.contains(Mycolor))
        {
            Colorchange = sharedpreferences.getString(Mycolor, "");
        }

        View background = getWindow().getDecorView();
        background.setBackgroundColor(Color.parseColor(Colorchange));

        this.buttonBlue = (Button) this.findViewById(R.id.buttonRBlue);
        this.buttonLightBlue = (Button) this.findViewById(R.id.buttonRLightBlue);
        this.buttonWhite = (Button) this.findViewById(R.id.buttonRWhite);
        this.buttonRed = (Button) this.findViewById(R.id.buttonRRed);
        this.buttonYellow = (Button) this.findViewById(R.id.buttonRYellow);
        this.buttonLigthGreen = (Button) this.findViewById(R.id.buttonRLightGreen);
        this.buttonGreen = (Button) this.findViewById(R.id.buttonRGreen);
        this.buttonOrange = (Button) this.findViewById(R.id.buttonROrange);
        this.buttonPink = (Button) this.findViewById(R.id.buttonRPink);
        this.buttonPurple = (Button) this.findViewById(R.id.buttonRPurple);

        this.buttonBlue.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(MyRcolor, "#ff1c49ff");
                editor.commit();
                finish();
            }
        });

        this.buttonLightBlue.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(MyRcolor, "#1dd2ff");
                editor.commit();
                finish();
            }
        });

        this.buttonWhite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(MyRcolor, "#ffffff");
                editor.commit();
                finish();
            }
        });

        this.buttonRed.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(MyRcolor, "#FF1C49");
                editor.commit();
                finish();
            }
        });

        this.buttonYellow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(MyRcolor, "#fffc49");
                editor.commit();
                finish();
            }
        });

        this.buttonLigthGreen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(MyRcolor, "#55ff5c");
                editor.commit();
                finish();
            }
        });

        this.buttonGreen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(MyRcolor, "#19aa07");
                editor.commit();
                finish();
            }
        });

        this.buttonOrange.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(MyRcolor, "#ff6a31");
                editor.commit();
                finish();
            }
        });

        this.buttonPink.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(MyRcolor, "#ff1fe9");
                editor.commit();
                finish();
            }
        });

        this.buttonPurple.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(MyRcolor, "#b166ff");
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
