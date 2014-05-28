package com.sw14_xp_05.pinkee;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.android.gms.common.AccountPicker;

import java.util.ArrayList;

/**
 * Created by ASUS on 07.05.2014.
 */
public class SettingsActivity extends ActionBarActivity {

    private Button buttonInfo;
    private Button buttonBackground;
    private Button buttonShare;
    private Button buttontheme;
    private Button buttonSavechat;

    public static final String MyPreferences = "MyPrefs";
    public static final String Mycolor = "Mycolor";
    public static final String Mytheme = "Mytheme";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        this.buttonInfo = (Button) this.findViewById(R.id.ButtonInfo);
        this.buttonInfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent newActivity0 = new Intent(SettingsActivity.this, InfoActivity.class);
                startActivity(newActivity0);
            }
        });

        this.buttonBackground = (Button) this.findViewById(R.id.ButtonBackground);
        this.buttonBackground.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent newActivity0 = new Intent(SettingsActivity.this, BackgroundActivity.class);
                startActivity(newActivity0);
            }
        });

        this.buttonShare = (Button) this.findViewById(R.id.ButtonShare);
        this.buttonShare.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent newActivity0 = new Intent(SettingsActivity.this, ShareActivity.class);
                startActivity(newActivity0);
            }
        });

        this.buttontheme = (Button) this.findViewById(R.id.ButtonTheme);
        this.buttontheme.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent newActivity0 = new Intent(SettingsActivity.this, ThemeActivity.class);
                startActivity(newActivity0);
            }
        });

        this.buttonSavechat = (Button) this.findViewById(R.id.ButtonSavechat);
        this.buttonSavechat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent newActivity0 = new Intent(SettingsActivity.this, SavechatActivity.class);
                startActivity(newActivity0);
            }
        });
       /*if(!utils.isLoggenIn())
        {
            Intent intent = AccountPicker.newChooseAccountIntent(null, null,
                    new String[]{"com.google"}, false, null, null, null, null);
            startActivityForResult(intent, 2);
        }*/

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


    /**
     * A placeholder fragment containing a simple view.
     */
    /*
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container,
                    false);
            return rootView;
        }
    }*/


}
