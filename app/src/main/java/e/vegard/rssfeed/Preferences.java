package e.vegard.rssfeed;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Preferences extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // init the different elements that will be needed
    private Button btnSave;
    private EditText urlLink;
    private EditText rssAmount;
    private Spinner rssFrequency;

    // constants for different purposes
    public final static String URL = "URL";
    private final static String[] FREQUENCY_LIST = {"10min", "60min", "once a day"};

    // values to keep data
    private int frequency;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        // textviews from preference activity
        urlLink = findViewById(R.id.rssInput);
        rssAmount = findViewById(R.id.rssAmount);

        // buttons in this activity
        btnSave = findViewById(R.id.btn_save);

        // Deal with spinner and add items to dropdown menu
        rssFrequency = findViewById(R.id.rssDropdown);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(Preferences.this,
                android.R.layout.simple_spinner_item,FREQUENCY_LIST);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rssFrequency.setAdapter(adapter);
        rssFrequency.setOnItemSelectedListener(this);


        //when twe change the text
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (position) {
            case 0:
                // this is 10 min we will set an int to 10
                frequency = 10;
                break;

            case 1:
                // this is 1 hour we will set int to 60
                frequency = 60;
                break;

            case 2:
                // this is 1 day we will set int to 1440
                frequency = 1440;
                break;

            default:
                frequency = 0;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
