package com.sw14_xp_05.pinkee;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by ASUS on 21.05.2014.
 */
public class ThemeActivity extends ActionBarActivity {

    private ImageButton imagebuttonspace;
    private ImageButton imagebuttonflower;
    private ImageButton imagebuttonconfetti;
    private ImageButton imagebuttonsuperman;

    public static final String MyPreferences = "MyPrefs";
    public static final String Mycolor = "Mycolor";
    public static final String Mytheme = "Mytheme";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);

        final SharedPreferences   sharedpreferences = getSharedPreferences(MyPreferences, Context.MODE_PRIVATE);

        this.imagebuttonspace = (ImageButton) this.findViewById(R.id.imageButtonSpace);
        this.imagebuttonflower = (ImageButton) this.findViewById(R.id.imageButtonFlower);
        this.imagebuttonconfetti = (ImageButton) this.findViewById(R.id.imageButtonConfetti);
        this.imagebuttonsuperman = (ImageButton) this.findViewById(R.id.imageButtonSuperman);

        this.imagebuttonspace.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Editor editor = sharedpreferences.edit();

                Integer theme = R.drawable.space;
                editor.clear();
                editor.putInt(Mytheme, theme);
                editor.commit();
                finish();
            }
        });

        this.imagebuttonflower.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Editor editor = sharedpreferences.edit();

                Integer theme = R.drawable.flower;
                editor.clear();
                editor.putInt(Mytheme, theme);
                editor.commit();
                finish();
            }
        });

        this.imagebuttonconfetti.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Editor editor = sharedpreferences.edit();

                Integer theme = R.drawable.confetti;
                editor.clear();
                editor.putInt(Mytheme, theme);
                editor.commit();
                finish();
            }
        });

        this.imagebuttonsuperman.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Editor editor = sharedpreferences.edit();

                Integer theme = R.drawable.superman;
                editor.clear();
                editor.putInt(Mytheme, theme);
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

        if(sharedpreferences.contains(Mycolor))
        {
            Colorchange = sharedpreferences.getString(Mycolor, "");
        }

        View background = getWindow().getDecorView();
        background.setBackgroundColor(Color.parseColor(Colorchange));
    }
}
