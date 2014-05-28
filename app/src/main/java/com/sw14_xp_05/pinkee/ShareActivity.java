package com.sw14_xp_05.pinkee;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by ASUS on 21.05.2014.
 */
public class ShareActivity extends ActionBarActivity {

    Intent mail = new Intent(Intent.ACTION_SEND);

    private Button buttonSend;

    private EditText mail_t;
    private EditText body_t;

    public static final String MyPreferences = "MyPrefs";
    public static final String Mycolor = "Mycolor";
    public static final String Mytheme = "Mytheme";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        this.buttonSend = (Button) this.findViewById(R.id.ButtonSend);

        this.mail_t = (EditText) this.findViewById(R.id.editEmail);
        this.body_t = (EditText) this.findViewById(R.id.Body);

        this.buttonSend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String mail_s = mail_t.getText().toString();
                String body_s = body_t.getText().toString();

                mail.setType("message/rfc822");
                mail.putExtra(Intent.EXTRA_EMAIL , new String[]{mail_s});
                mail.putExtra(Intent.EXTRA_SUBJECT, "Pinkee");
                mail.putExtra(Intent.EXTRA_TEXT, body_s);

                try {
                    startActivity(Intent.createChooser(mail, "Send Mail..."));
                }
                catch (ActivityNotFoundException ex)
                {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

    };


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
