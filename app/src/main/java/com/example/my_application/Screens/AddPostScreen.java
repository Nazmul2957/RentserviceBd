package com.example.my_application.Screens;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
    String Token;
    Uri imageUri = null, imageUriNid2 = null, imageUriTrade = null, imageUriTin = null;
    File f1, f2nid, f3trade, f4tin;
    String imgString;


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
                ImageOne.setImageBitmap(bitmap);
            } else {
                Log.e("REQ", "Bitmap null");
            }


        }
//        else if (resultCode == RESULT_OK && requestCode == Constant.PICK_PHOTO_THREE && data != null) {
//            imageUriTrade = data.getData();
//
//            try {
//                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUriTrade);
//                f3trade = new File(getCacheDir(), "Image3");
//                bitmap = getResizedBitmap(bitmap, 800);
//                ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
//                // bitmap.compress(Bitmap.CompressFormat.JPEG, 0 /*ignored for PNG*/, bos);
//                byte[] bitmapdata = bos.toByteArray();
//                FileOutputStream fos = null;
//                try {
//                    fos = new FileOutputStream(f3trade);
//
//                } catch (FileNotFoundException e) {
//                    Log.e("REQ", e.toString());
//                }
//                try {
//                    fos.write(bitmapdata);
//                    fos.flush();
//                    fos.close();
//                   // isTrade = true;
//                } catch (IOException e) {
//                    Log.e("REQ", e.toString());
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
////            if (bitmap != null) {
////                binding.tradeLicensePic.setImageBitmap(bitmap);
////                binding.tradePic.setVisibility(View.VISIBLE);
////            } else {
////                Log.e("REQ", "Bitmap null");
////            }
//
//        } else if (resultCode == RESULT_OK && requestCode == Constant.PICK_PHOTO_FOUR && data != null) {
//            imageUriTin = data.getData();

//            try {
//                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUriTin);
//                f4tin = new File(getCacheDir(), "Image4");
//                bitmap = getResizedBitmap(bitmap, 800);
//                ByteArrayOutputStream bos = new ByteArrayOutputStream();
//
//                bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
//                // bitmap.compress(Bitmap.CompressFormat.JPEG, 0 /*ignored for PNG*/, bos);
//                byte[] bitmapdata = bos.toByteArray();
//
//
//                FileOutputStream fos = null;
//                try {
//                    fos = new FileOutputStream(f4tin);
//
//                } catch (FileNotFoundException e) {
//                    Log.e("REQ", e.toString());
//                }
//                try {
//                    fos.write(bitmapdata);
//                    fos.flush();
//                    fos.close();
//                    //isTin = true;
//                } catch (IOException e) {
//                    Log.e("REQ", e.toString());
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            if (bitmap != null) {
//                binding.tinLicensePic.setImageBitmap(bitmap);
//                binding.tinPic.setVisibility(View.VISIBLE);
//            } else {
//                Log.e("REQ", "Bitmap null");
//            }

    }
    //   }


    public void register() {
        RequestBody requestBody;
        String imageName = "";
        RequestBody requestImage = null;
        RequestBody attachmentEmpty = RequestBody.create(MediaType.parse("image/*"), "");
        // RequestBody attachmentEmpty = RequestBody.Companion.create(MediaType.parse("image/*"), "");

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
                .addFormDataPart("title", Title.getText().toString())
                .addFormDataPart("description", Description.getText().toString())
                .addFormDataPart("price", Price.getText().toString())
                .addFormDataPart("address", Address.getText().toString())
                .addFormDataPart("divisionId", String.valueOf(data.get(DivisionList.getSelectedItemPosition()).getId().toString()))
                .addFormDataPart("districtId", String.valueOf(datas.get(DistrictList.getSelectedItemPosition()).getId().toString()))
                .addFormDataPart("policeStationId", String.valueOf(datass.get(Police_Station.getSelectedItemPosition()).getId().toString()))
                .addFormDataPart("key", Token)
                // .addFormDataPart("image", imageName, requestImage != null ? requestImage : attachmentEmpty)
                .addFormDataPart("image1","data:image/jpeg;base64,"+ String.valueOf(imgString))
                .build();

        progressDialog.show();
        api.InsertPost(requestBody).enqueue(new Callback<InsertPostResponseContainer>() {
            @Override
            public void onResponse(Call<InsertPostResponseContainer> call,
                                   Response<InsertPostResponseContainer> response) {
                //progressDialog.show();
                if (response.isSuccessful() && response.body() != null) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "post added", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getApplicationContext(),DashBoardScreen.class);
                    startActivity(intent);
                    finish();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "post not added", Toast.LENGTH_SHORT).show();
                    //   Log.d("testthree",String.valueOf(response.errorBody().toString()));
                    Log.d("testthree", response.message().toString());
                }
            }

            @Override
            public void onFailure(Call<InsertPostResponseContainer> call, Throwable t) {
                progressDialog.dismiss();
                finish();
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