package com.example.intuitionproject;

import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Spinner;

public class new_request_activity extends AppCompatActivity {

    public String[] SingaporeTowns = { "None", "Ang Mo Kio", "Bedok", "Bishan", "Bukit Batok", "Red Hill",
            "Bukit Panjang", "Choa Chu Kang", "Clementi", "Geylang", "Hougang", "Jurong East", "Jurong West",
    "Kallang", "Pasir Ris", "Punggol", "Queenstown", "Sembawanag", "Sengkang", "Serangoon", "Tampines", "Toa Payoh", "Woodlands", "Yishun"};

    EditText requestName;
    EditText requestDetails;
    Spinner meetupRegion;
    Spinner destinationRegion;
    EditText deliveryPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_request_activity);
        SetUpUI();

    }

    private void SetUpUI(){
        requestName = findViewById(R.id.requestNameEditText);
        requestDetails = findViewById(R.id.requestDetailsEditText);
        meetupRegion = findViewById(R.id.meetupRegion);
        destinationRegion = findViewById(R.id.destinationRegion);
        deliveryPrice = findViewById(R.id.deliveryPrice);

        meetupRegion.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, SingaporeTowns));
        destinationRegion.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, SingaporeTowns));
    }
}