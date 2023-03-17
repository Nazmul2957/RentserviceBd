package com.example.my_application.Screens;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.session.MediaSession;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my_application.Adaptar.DistrictListAdaptar;
import com.example.my_application.Adaptar.DivisionSpinnerAdaptar;
import com.example.my_application.Adaptar.PoliceStationAdaptar;
import com.example.my_application.Data_Model.DistrictModel.District;
import com.example.my_application.Data_Model.DistrictModel.DistrictContainer;
import com.example.my_application.Data_Model.Division.Division;
import com.example.my_application.Data_Model.Division.DivisionContainer;
import com.example.my_application.Data_Model.InsertPost.InsertPostResponseContainer;
import com.example.my_application.Data_Model.PoliceStation.Police;
import com.example.my_application.Data_Model.PoliceStation.PoliceStationContainer;
import com.example.my_application.Network.Api;
import com.example.my_application.Network.RetrofitClient;
import com.example.my_application.R;
import com.example.my_application.Util.Constant;
import com.example.my_application.Util.MySharedPreference;
import com.example.my_application.Util.RealPathUtil;
import com.google.gson.JsonObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddPostScreen extends AppCompatActivity {
    Api api;
    ProgressDialog progressDialog;
    Spinner DivisionList, DistrictList, Police_Station;
    Bitmap bitmap;
    List<Division> data;
    List<District> datas;
    List<Police> datass;
    EditText Title, Description, Price, Address;
    Button POSTDATA;
    ImageView ImageOne;
    TextView ImageSelect;
    String path;
    String Token;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private static final int SELECT_REQUEST_CODE = 1;

    Uri imageUriNid = null, imageUriNid2 = null, imageUriTrade = null, imageUriTin = null;
    File f1nid, f2nid, f3trade, f4tin;
    boolean isNid1 = false, isNid2 = false, isTrade = false, isTin = false;
    Uri uri = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post_screen);

        DivisionList = findViewById(R.id.dibision);
        DistrictList = findViewById(R.id.districts);
        Police_Station = findViewById(R.id.policestation);
        Title = findViewById(R.id.insert_title);
        Description = findViewById(R.id.insert_description);
        Price = findViewById(R.id.insert_price);
        Address = findViewById(R.id.insert_address);
        POSTDATA = findViewById(R.id.Post);
        ImageOne = findViewById(R.id.imageshow);
        ImageSelect = findViewById(R.id.select_image);


        api = RetrofitClient.difBaseUrle().create(Api.class);
       Token = MySharedPreference.getInstance(getApplicationContext()).getString(Constant.TOKEN, "not found");

        progressDialog = new ProgressDialog(AddPostScreen.this);
        progressDialog.setMessage("Please Wait......");
        progressDialog.setCancelable(false);
        getdivision();
        zilla();
        police();


        POSTDATA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
//                addCustomer(Title.getText().toString(),
//                        Description.getText().toString(),
//                        Price.getText().toString(),
//                        Address.getText().toString(),
//                        String.valueOf(data.get(DivisionList.getSelectedItemPosition()).getId().toString()),
//                        String.valueOf(datas.get(DistrictList.getSelectedItemPosition()).getId().toString()),
//                        String.valueOf(datass.get(Police_Station.getSelectedItemPosition()).getId().toString()), Token);
            }
        });

        ImageSelect.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, Constant.PICK_PHOTO_ONE);
            } else {
                ActivityCompat.requestPermissions(AddPostScreen.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        });


    }

    public void getdivision() {
        progressDialog.show();
        api.getDivision().enqueue(new Callback<DivisionContainer>() {
            @Override
            public void onResponse(Call<DivisionContainer> call, Response<DivisionContainer> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    data = response.body().getData();
                    data.add(0, new Division(0, "Select Division"));
                    DivisionSpinnerAdaptar customeadaptar = new DivisionSpinnerAdaptar(data, getApplicationContext());
                    DivisionList.setAdapter(customeadaptar);
                    Log.d("division", data.toString());

                }

            }

            @Override
            public void onFailure(Call<DivisionContainer> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Something wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void zilla() {
        progressDialog.show();
        api.getDistrict().enqueue(new Callback<DistrictContainer>() {
            @Override
            public void onResponse(Call<DistrictContainer> call, Response<DistrictContainer> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    datas = response.body().getData();
                    datas.add(0, new District(0, "Select District"));
                    DistrictListAdaptar customeadaptar = new DistrictListAdaptar(datas, getApplicationContext());
                    DistrictList.setAdapter(customeadaptar);
                    Log.d("District", datas.toString());

                }

            }

            @Override
            public void onFailure(Call<DistrictContainer> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Something wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void police() {
        progressDialog.show();
        api.getpolice().enqueue(new Callback<PoliceStationContainer>() {
            @Override
            public void onResponse(Call<PoliceStationContainer> call, Response<PoliceStationContainer> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    datass = response.body().getData();
                    datass.add(0, new Police(0, "Select Police Station"));
                    PoliceStationAdaptar adaptar = new PoliceStationAdaptar(datass, getApplicationContext());
                    Police_Station.setAdapter(adaptar);

                }
            }

            @Override
            public void onFailure(Call<PoliceStationContainer> call, Throwable t) {

            }
        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 10 && resultCode == Activity.RESULT_OK) {
//             uri = data.getData();
//            Context context = AddPostScreen.this;
//            path = RealPathUtil.getRealPath(context, uri);
//            Bitmap bitmap = BitmapFactory.decodeFile(path);
//            ImageOne.setImageBitmap(bitmap);
//
//        }
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == Constant.PICK_PHOTO_ONE && data != null) {
            imageUriNid = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUriNid);
                f1nid = new File(getCacheDir(), "Image1");
                bitmap = getResizedBitmap(bitmap, 800);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();

                bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
                // bitmap.compress(Bitmap.CompressFormat.JPEG, 0 /*ignored for PNG*/, bos);
                byte[] bitmapdata = bos.toByteArray();


                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(f1nid);

                } catch (FileNotFoundException e) {
                    Log.e("REQ", e.toString());
                }
                try {
                    fos.write(bitmapdata);
                    fos.flush();
                    fos.close();
                    isNid1 = true;
                } catch (IOException e) {
                    Log.e("REQ", e.toString());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            if (bitmap != null) {
                ImageOne.setImageBitmap(bitmap);
            } else {
                Log.e("REQ", "Bitmap null");
            }


        } else if (resultCode == RESULT_OK && requestCode == Constant.PICK_PHOTO_THREE && data != null) {
            imageUriTrade = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUriTrade);
                f3trade = new File(getCacheDir(), "Image3");
                bitmap = getResizedBitmap(bitmap, 800);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
                // bitmap.compress(Bitmap.CompressFormat.JPEG, 0 /*ignored for PNG*/, bos);
                byte[] bitmapdata = bos.toByteArray();
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(f3trade);

                } catch (FileNotFoundException e) {
                    Log.e("REQ", e.toString());
                }
                try {
                    fos.write(bitmapdata);
                    fos.flush();
                    fos.close();
                    isTrade = true;
                } catch (IOException e) {
                    Log.e("REQ", e.toString());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
//            if (bitmap != null) {
//                binding.tradeLicensePic.setImageBitmap(bitmap);
//                binding.tradePic.setVisibility(View.VISIBLE);
//            } else {
//                Log.e("REQ", "Bitmap null");
//            }

        } else if (resultCode == RESULT_OK && requestCode == Constant.PICK_PHOTO_FOUR && data != null) {
            imageUriTin = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUriTin);
                f4tin = new File(getCacheDir(), "Image4");
                bitmap = getResizedBitmap(bitmap, 800);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();

                bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
                // bitmap.compress(Bitmap.CompressFormat.JPEG, 0 /*ignored for PNG*/, bos);
                byte[] bitmapdata = bos.toByteArray();


                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(f4tin);

                } catch (FileNotFoundException e) {
                    Log.e("REQ", e.toString());
                }
                try {
                    fos.write(bitmapdata);
                    fos.flush();
                    fos.close();
                    isTin = true;
                } catch (IOException e) {
                    Log.e("REQ", e.toString());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
//            if (bitmap != null) {
//                binding.tinLicensePic.setImageBitmap(bitmap);
//                binding.tinPic.setVisibility(View.VISIBLE);
//            } else {
//                Log.e("REQ", "Bitmap null");
//            }

        }
    }


    private void register() {
        progressDialog.show();
        RequestBody requestBody;
        String nidpicName = "";
        RequestBody requestNid1 = null;
        RequestBody attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "");

        if (uri != null) {
            File nidfile = new File(uri.getLastPathSegment().toString());
            nidpicName = nidfile.getName();
            requestNid1 = RequestBody.create(MediaType.parse("multipart/form-data"), f1nid);
        }

        requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("title", Title.getText().toString())
                .addFormDataPart("description", Description.getText().toString())
                .addFormDataPart("price", Price.getText().toString())
                .addFormDataPart("address", Address.getText().toString())
                .addFormDataPart("divisionId", String.valueOf(data.get(DivisionList.getSelectedItemPosition()).getId().toString()))
                .addFormDataPart("districtId", String.valueOf(datas.get(DistrictList.getSelectedItemPosition()).getId().toString()))
                .addFormDataPart("policeStationId", String.valueOf(datass.get(Police_Station.getSelectedItemPosition()).getId().toString()))
                .addFormDataPart("key", Token)
                .addFormDataPart("image1", nidpicName, requestNid1 != null ? requestNid1 : attachmentEmpty)
                .build();


        api.InsertPost(requestBody).enqueue(new Callback<InsertPostResponseContainer>() {
            @Override
            public void onResponse(Call<InsertPostResponseContainer> call, Response<InsertPostResponseContainer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<InsertPostResponseContainer> call, Throwable t) {

            }
        });

//        api.registration(requestBody).enqueue(new Callback<RegisterResponse>() {
//            @Override
//            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
//                progressDialog.dismiss();
//                // Log.e("toos", "from  register " + response.toString());
//                if (response.isSuccessful() && response.body() != null) {
//                    Toast.makeText(Registration_Page.this, "", Toast.LENGTH_LONG).show();
//                    MySharedPreference.getInstance(Registration_Page.this).edit()
//                            .putString(Constant.TOKEN, response.body().getToken())
//                            .putString(Constant.NAME, binding.merchantName.getText().toString()).
//                            putString(Constant.PHONE, binding.phnNumber.getText().toString()).apply();
//                    Intent intent = new Intent(Registration_Page.this, OtpRegistrationConfirm.class);
//                    intent.putExtra(Constant.PHONE, binding.phnNumber.getText().toString());
//                    startActivity(new Intent(Registration_Page.this, OtpRegistrationConfirm.class));
//                    startActivity(intent);
//                    finish();
//                } else {
//                    try {
//                        Log.d("tesst", response.errorBody().string());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    Toast.makeText(Registration_Page.this, "Registration failed", Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//
//            public void onFailure(Call<RegisterResponse> call, Throwable t) {
//                progressDialog.dismiss();
//                Log.d("tesst", t.toString());
//                Toast.makeText(Registration_Page.this, "Registration failed" + t.toString(), Toast.LENGTH_LONG).show();
//
//            }
//        });
    }

//
//    public void addCustomer(String Title, String Description, String Price, String Address, String DivisionId,
//                            String DistrictId, String PolicId, String KEY) {
//
//        File file = new File(path);
//        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//
//        MultipartBody.Part body = MultipartBody.Part.createFormData("image1", file.getName(), requestFile);
//
//        RequestBody title_name = RequestBody.create(MediaType.parse("multipart/form-data"), Title);
//        RequestBody Description_name = RequestBody.create(MediaType.parse("multipart/form-data"), Description);
//        RequestBody Price_name = RequestBody.create(MediaType.parse("multipart/form-data"), Price);
//        RequestBody Address_name = RequestBody.create(MediaType.parse("multipart/form-data"), Address);
//        RequestBody Division_name = RequestBody.create(MediaType.parse("multipart/form-data"), DivisionId);
//        RequestBody DistrictId_name = RequestBody.create(MediaType.parse("multipart/form-data"), DistrictId);
//        RequestBody PolicId_name = RequestBody.create(MediaType.parse("multipart/form-data"), PolicId);
//        RequestBody KEY_name = RequestBody.create(MediaType.parse("multipart/form-data"), KEY);
//
//
//        api.InsertPost(title_name, Description_name, Price_name, Address_name, Division_name, DistrictId_name,
//                PolicId_name, KEY_name, body).enqueue(new Callback<InsertPostResponseContainer>() {
//            @Override
//            public void onResponse(Call<InsertPostResponseContainer> call,
//                                   Response<InsertPostResponseContainer> response) {
//                progressDialog.show();
//                if (response.isSuccessful() && response.body() != null) {
//                    progressDialog.dismiss();
//                    Toast.makeText(getApplicationContext(), "Post Upload Successfull", Toast.LENGTH_SHORT).show();
//
//                } else {
//                    Toast.makeText(getApplicationContext(), "not Added", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<InsertPostResponseContainer> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }

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