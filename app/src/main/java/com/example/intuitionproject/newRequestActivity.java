package com.example.intuitionproject;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.intuitionproject.databinding.ContentNewRequestActivityBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

    private ContentNewRequestActivityBinding binding;

    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ContentNewRequestActivityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        SetUpUI();
        uri = Uri.parse("android.resource://com.example.intuitionproject/drawable/question_mark");
    }

    private void SetUpUI(){
        binding.meetupRegion.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, SingaporeTowns));
        binding.destinationRegion.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, SingaporeTowns));


        binding.itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickSetup setup = new PickSetup().setTitle("Upload Image").setSystemDialog(true).setProgressText("Hello");

                PickImageDialog.build(setup).show(newRequestActivity.this);
            }
        });

        binding.makeRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) UploadImage();
            }
        });
    }

    private boolean validateInput() {
        if (binding.requestNameEditText.getText().toString().isEmpty()){
            binding.requestNameEditText.setError("Please fill in request name");
            binding.requestNameEditText.requestFocus();
            return false;
        }

        if (binding.requestDetailsEditText.getText().toString().isEmpty()){
            binding.requestDetailsEditText.setError("Please fill in request details name");
            binding.requestDetailsEditText.requestFocus();
            return false;
        }

        if (binding.deliveryPrice.getText().toString().isEmpty()){
            binding.deliveryPrice.setError("Please fill in the price");
            binding.deliveryPrice.requestFocus();
            return false;
        }

        return true;
    }

    private void UploadImage() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading");
        pd.show();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
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
                            long unixTime = System.currentTimeMillis();
                            test.put("userid", user.getEmail());
                            test.put("title",binding.requestNameEditText.getText().toString());
                            test.put("details", binding.requestDetailsEditText.getText().toString());
                            test.put("meetup-region", binding.meetupRegion.getSelectedItem().toString());
                            test.put("dest-region", binding.destinationRegion.getSelectedItem().toString());
                            test.put("payment", binding.deliveryPrice.getText().toString());
                            test.put("timestamp", String.valueOf(unixTime));
                            test.put("picture-url", url);
                            test.put("request-accepted", "false");


                            FirebaseFirestore.getInstance().collection("requests").document().set(test).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(newRequestActivity.this, "Uploaded!", Toast.LENGTH_SHORT);
                                    pd.dismiss();
                                    finish();
                                }
                            });
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
            binding.itemImage.setImageBitmap(r.getBitmap());
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