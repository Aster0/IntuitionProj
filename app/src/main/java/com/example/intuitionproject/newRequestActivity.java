package com.example.intuitionproject;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import java.util.HashMap;
import java.util.Map;

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
    Button uploadRequest;

    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_request_activity);
        SetUpUI();
    }

    private void SetUpUI(){

        uploadRequest = findViewById(R.id.makeRequest);
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

        uploadRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadImage();
            }
        });
    }

    private void UploadImage() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading");
        pd.show();

        if (uri != null){
            final StorageReference fileRef = FirebaseStorage.getInstance().getReference()
                    .child("upload").child(System.currentTimeMillis() + "." + getFileExt(uri));


            fileRef.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url = uri.toString();

                            Log.d("DownloadUrl", url);

                            Map<String, String> test = new HashMap<>();

                            test.put("test", "testtt");

                            FirebaseFirestore.getInstance().collection("testing").document("test").set(test);
                            pd.dismiss();
                        }
                    });
                }
            });
        }
    }

    private String getFileExt(Uri uri) {
        ContentResolver cr = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
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
            uri = r.getUri();
            //Image path
            //r.getPath();
        } else {
            //Handle possible errors
            //TODO: do what you have to do with r.getError();
            Toast.makeText(this, r.getError().getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}