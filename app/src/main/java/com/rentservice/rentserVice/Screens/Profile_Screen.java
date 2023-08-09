package com.rentservice.rentserVice.Screens;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.rentservice.rentserVice.Data_Model.Profile.ProfileContainer;
import com.rentservice.rentserVice.Data_Model.Profile.ProfilePicContainer;
import com.rentservice.rentserVice.Network.Api;
import com.rentservice.rentserVice.Network.RetrofitClient;
import com.example.my_application.R;
import com.rentservice.rentserVice.Util.Constant;
import com.rentservice.rentserVice.Util.MySharedPreference;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile_Screen extends AppCompatActivity {

    ShapeableImageView imageView;
    ImageView Edit_image, Edit_Profile;
    Api api;
    ProgressDialog progressDialog;
    String Token;
    TextView Name, Mobile, Address, DivisionName, DistrictName, PoliceStation;
    Uri imageUri = null;
    File f1;
    String imgString;
    Bitmap bitmap;
    Button button;
    String ProId;


    String PName, PAddress, PMobile_Number, Profle_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);

        imageView = findViewById(R.id.get_image);
        Name = findViewById(R.id.profile_name);
        Mobile = findViewById(R.id.profile_number);
        Address = findViewById(R.id.profile_address);
        Edit_image = findViewById(R.id.edit_profile_picture_button);
        button = findViewById(R.id.save_Profile);
        DivisionName = findViewById(R.id.profile_Division);
        DistrictName = findViewById(R.id.profile_district);
        PoliceStation = findViewById(R.id.profile_Police);
        Edit_Profile = findViewById(R.id.edit_option);


        Edit_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Edit_Profile_Screen.class);
                intent.putExtra("name", PName);
                intent.putExtra("Address", PAddress);
                intent.putExtra("Mobile", PMobile_Number);
                intent.putExtra("Pid",Profle_Id);
                startActivity(intent);
                finish();

            }
        });


        api = RetrofitClient.get(getApplicationContext()).create(Api.class);
        Token = MySharedPreference.getInstance(getApplicationContext()).getString(Constant.TOKEN, "not found");

        progressDialog = new ProgressDialog(Profile_Screen.this);
        progressDialog.setMessage("Please Wait......");
        progressDialog.setCancelable(false);
        progressDialog.show();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Profile_Pic_Show_Screen.class);
                startActivity(intent);
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Upload();
            }
        });

        Edit_image.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, Constant.PICK_PHOTO_ONE);
            } else {
                ActivityCompat.requestPermissions(Profile_Screen.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        });


        api.getprofile(Token).enqueue(new Callback<ProfileContainer>() {
            @Override
            public void onResponse(Call<ProfileContainer> call, Response<ProfileContainer> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    Glide.with(getApplicationContext()).load("https://rentservicebd.com/public/api/image/" +
                            response.body().getUserInfo().getAvatar()).into(imageView);
                    Name.setText(String.valueOf(response.body().getUserInfo().getName()));
                    Mobile.setText(String.valueOf(response.body().getUserInfo().getMobile()));
                    Address.setText(String.valueOf(response.body().getUserInfo().getAddress()));
                    ProId = String.valueOf(response.body().getUserInfo().getId());
                    DivisionName.setText(String.valueOf(response.body().getUserInfo().getDivision()));
                    DistrictName.setText(String.valueOf(response.body().getUserInfo().getDistrict()));
                    PoliceStation.setText(String.valueOf(response.body().getUserInfo().getPoliceStation()));
                    PName = String.valueOf(response.body().getUserInfo().getName());
                    PAddress = String.valueOf(response.body().getUserInfo().getAddress());
                    PMobile_Number = String.valueOf(response.body().getUserInfo().getMobile());
                    Profle_Id = String.valueOf(response.body().getUserInfo().getId());
                }


            }

            @Override
            public void onFailure(Call<ProfileContainer> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == Constant.PICK_PHOTO_ONE && data != null) {

            imageUri = data.getData();
            Log.d("testone", String.valueOf(imageUri));

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                f1 = new File(getCacheDir(), "Image1");
                bitmap = getResizedBitmap(bitmap, 800);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();

                bitmap.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos);
                byte[] bitmapdata = bos.toByteArray();
                imgString = Base64.encodeToString(bitmapdata, Base64.NO_WRAP);

//                byte[] decodedString = Base64.decode(imgString, Base64.DEFAULT);
//                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,decodedString.length);
//                ImageOne.setImageBitmap(decodedByte);

                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(f1);

                } catch (FileNotFoundException e) {
                    Log.e("REQ", e.toString());
                }
                try {
                    fos.write(bitmapdata);
                    fos.flush();
                    fos.close();
                    // isNid1 = true;
                } catch (IOException e) {
                    Log.e("REQ", e.toString());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
                button.setVisibility(View.VISIBLE);
            } else {
                Log.e("REQ", "Bitmap null");
            }


        }
    }


    public void Upload() {
        RequestBody requestBody;
        String imageName = "";
        RequestBody requestImage = null;
        //   RequestBody attachmentEmpty = RequestBody.create(MediaType.parse("image/*"), "");

        if (imageUri != null) {
            File nidfile = new File(String.valueOf(imageUri));
            imageName = nidfile.getName();
            Log.d("testwo", imageName);
            //  requestImage = RequestBody.create(MediaType.parse("image/*"), imageName);
            requestImage = RequestBody.create(MediaType.parse("image/*"), imageName);
            Log.d("testthrees", String.valueOf(requestImage));

        }

        requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("id", String.valueOf(ProId))
                .addFormDataPart("avatar", "data:image/jpeg;base64," + imgString)
                .build();

        progressDialog.show();
        api.ProfilePicUpload(requestBody).enqueue(new Callback<ProfilePicContainer>() {
            @Override
            public void onResponse(Call<ProfilePicContainer> call, Response<ProfilePicContainer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    progressDialog.dismiss();
                    button.setVisibility(View.GONE);
                    String message = response.body().getMessage();
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProfilePicContainer> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
}