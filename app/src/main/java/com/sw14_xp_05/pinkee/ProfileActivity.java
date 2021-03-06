package com.sw14_xp_05.pinkee;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Environment;
import java.io.File;
import android.widget.Toast;
import android.graphics.Bitmap.CompressFormat;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.FileOutputStream;

public class ProfileActivity extends ActionBarActivity {

    private Contact contact;

    EditText forename;
    EditText name;
    TextView email;
    ImageView profileImageView;

    public static final String MyPreferences = "MyPrefs";
    public static final String Mycolor = "Mycolor";
    public static final String Mytheme = "Mytheme";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        this.contact =  (Contact) getIntent().getSerializableExtra("contact");


        String path = Environment.getExternalStorageDirectory() + "/pinkeeimages/" + contact.getEmail()+".jpg";

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 5;
        //options.inJustDecodeBounds = true;

        File sddir = new File(path);
        if (!sddir.mkdirs()) {
            if (sddir.exists()) {
            } else {
                Log.e("profile activity", "File Error");
            }
        }

        Bitmap bmap = BitmapFactory.decodeFile(path, options);

        //TextView forename_view = (TextView) findViewById(R.id.forenameView);
        //forename_view.setText(contact.getForename());

        forename = (EditText) findViewById(R.id.editForename);
        forename.setText(contact.getForename());
        name = (EditText) findViewById(R.id.editName);
        name.setText(contact.getName());
        email = (TextView) findViewById(R.id.emailView);
        email.setText(contact.getEmail());
        profileImageView = (ImageView) findViewById(R.id.profilePictureView);
        if (bmap != null)
            profileImageView.setImageBitmap(bmap);
        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 999);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            final Uri imageUri = data.getData();
            Log.d("#uri", imageUri.toString());

            String path = Environment.getExternalStorageDirectory() + "/pinkeeimages/";
            File sddir = new File(path);
            if (!sddir.mkdirs()) {
                if (sddir.exists()) {
                } else {
                    Toast.makeText(getApplicationContext(), "Folder error", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            final InputStream imageStream = getContentResolver().openInputStream(imageUri);
            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

            try {
                FileOutputStream fos = new FileOutputStream(path + contact.getEmail() + ".jpg");
                selectedImage.compress(CompressFormat.JPEG, 10, fos);
                Toast.makeText(getApplicationContext(), "Image saved.", Toast.LENGTH_SHORT).show();
                fos.flush();
                fos.close();
            } catch (Exception e) {
                Log.e("profile activity", e.toString());
            }
            profileImageView.setImageBitmap(selectedImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void onPause() {
        super.onPause();
        Log.i("ProfileActivity", "onPause");
        contact.setForename(forename.getText().toString());
        contact.setName(name.getText().toString());
        SQLiteStorageHelper dbhelper = SQLiteStorageHelper.getInstance(getBaseContext());
        dbhelper.saveContact(contact);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent newActivity0 = new Intent(ProfileActivity.this, SettingsActivity.class);
            startActivity(newActivity0);
            return true;
        }
        return super.onOptionsItemSelected(item);
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
