package e.vegard.rssfeed;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Preferences extends AppCompatActivity {

    public final static String URL = "URL";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        // we will get the shared

        // buttons in this activity
        final Button btnSave = findViewById(R.id.btn_save);

        // final edit text referes the utl link and will store this in a shared preference
        final EditText urlLink = findViewById(R.id.rssInput);

        urlLink.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (urlLink.getText().toString() != "") {
                    Toast.makeText(Preferences.this, urlLink.getText().toString(), Toast.LENGTH_LONG);
                    SharedPreferences.Editor editor = getSharedPreferences(URL, MODE_PRIVATE).edit();
                    editor.putString("url", urlLink.getText().toString());
                    editor.apply();
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }
}
