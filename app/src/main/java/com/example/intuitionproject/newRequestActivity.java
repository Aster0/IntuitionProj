package com.example.intuitionproject;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

public class newRequestActivity extends AppCompatActivity implements IPickResult {

    public String[] SingaporeTowns = { "None", "Ang Mo Kio", "Bedok", "Bishan", "Bukit Batok", "Red Hill",
            "Bukit Panjang", "Choa Chu Kang", "Clementi", "Geylang", "Hougang", "Jurong East", "Jurong West",
    "Kallang", "Pasir Ris", "Punggol", "Queenstown", "Sembawanag", "Sengkang", "Serangoon", "Tampines", "Toa Payoh", "Woodlands", "Yishun"};

    EditText requestName;
    EditText requestDetails;
    Spinner meetupRegion;
    Spinner destinationRegion;
    EditText deliveryPrice;
    ImageButton captureImage;
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
        captureImage = findViewById(R.id.item_image);

        captureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickSetup setup = new PickSetup().setTitle("Upload Image").setSystemDialog(true).setProgressText("Hello");

                PickImageDialog.build(setup).show(newRequestActivity.this);
            }
        });
    }


    @Override
    public void onPickResult(PickResult r) {
        if (r.getError() == null) {
            //If you want the Uri.
            //Mandatory to refresh image from Uri.
            //getImageView().setImageURI(null);

            //Setting the real returned image.
            //getImageView().setImageURI(r.getUri());

            //If you want the Bitmap.
           captureImage.setImageBitmap(r.getBitmap());

            //Image path
            //r.getPath();
        } else {
            //Handle possible errors
            //TODO: do what you have to do with r.getError();
            Toast.makeText(this, r.getError().getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}