package com.sw14_xp_05.pinkee;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

/**
 * Created by ASUS on 14.05.2014.
 */
public class InfoActivity extends ActionBarActivity {

    public static final String MyPreferences = "MyPrefs";
    public static final String Mycolor = "Mycolor";
    public static final String Mytheme = "Mytheme";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

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
