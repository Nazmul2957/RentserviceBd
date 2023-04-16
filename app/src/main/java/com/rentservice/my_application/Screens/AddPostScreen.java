package com.rentservice.my_application.Screens;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.rentservice.my_application.Adaptar.Category_Spinner_Adaptar;
import com.rentservice.my_application.Adaptar.DistrictShowByDivision;
import com.rentservice.my_application.Adaptar.DivisionSpinnerAdaptar;
import com.rentservice.my_application.Adaptar.PoliceStationShowByDisAdaptar;
import com.rentservice.my_application.Data_Model.Category.CategoryContainer;
import com.rentservice.my_application.Data_Model.Category.CategoryList;
import com.rentservice.my_application.Data_Model.DistrictshowbyDivision.DistrictShow;
import com.rentservice.my_application.Data_Model.DistrictshowbyDivision.DistrictshowContainer;
import com.rentservice.my_application.Data_Model.Division.Division;
import com.rentservice.my_application.Data_Model.Division.DivisionContainer;
import com.rentservice.my_application.Data_Model.InsertPost.InsertPostResponseContainer;
import com.rentservice.my_application.Data_Model.PoliceStationByDistrict.PolicestationByDistrictContainer;
import com.rentservice.my_application.Data_Model.PoliceStationByDistrict.PolicestationShowByDistrict;
import com.rentservice.my_application.Network.Api;
import com.rentservice.my_application.Network.RetrofitClient;
import com.example.my_application.R;
import com.rentservice.my_application.Util.Constant;
import com.rentservice.my_application.Util.MySharedPreference;

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
    Spinner DivisionList, DistrictList, Police_Station, Category;
    Bitmap bitmap;
    List<Division> data;
    List<CategoryList> catgoryList;
    List<DistrictShow> datas;
    List<PolicestationShowByDistrict> datass;
    EditText Title, Description, Price, Address;
    Button POSTDATA;
    ImageView ImageOne, Imagetwo, Imagethree, Imagefour, Imagefive;
    Button ImageSelect, ImageSelectTwo, ImageSelectThree, ImageSelectFour, ImageSelectFive;
    String Token;
    Uri imageUri = null, imageUritwo = null, imageUrithree = null, imageUrifour = null, imageUrifive = null;
    File f1, f2, f3, f4, f5;
    String imgString, iamStringtwo, iamStringthree, iamStringfour, iamStringfive;
    LinearLayout DisHide, PoliceHide;


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
        // ImageShowList = findViewById(R.id.image_show_multipul);
        DisHide = findViewById(R.id.District_hide);
        PoliceHide = findViewById(R.id.Police_hide);
        Category = findViewById(R.id.category);

        ImageOne = findViewById(R.id.imageshow);
        Imagetwo = findViewById(R.id.imageshow_two);
        Imagethree = findViewById(R.id.imageshow_three);
        Imagefour = findViewById(R.id.imageshow_four);
        Imagefive = findViewById(R.id.imageshow_five);


        ImageSelect = findViewById(R.id.select_image);
        ImageSelectTwo = findViewById(R.id.select_image_two);
        ImageSelectThree = findViewById(R.id.select_image_three);
        ImageSelectFour = findViewById(R.id.select_image_four);
        ImageSelectFive = findViewById(R.id.select_image_five);


        api = RetrofitClient.difBaseUrle().create(Api.class);
        Token = MySharedPreference.getInstance(getApplicationContext()).getString(Constant.TOKEN, "not found");

        progressDialog = new ProgressDialog(AddPostScreen.this);
        progressDialog.setMessage("Please Wait......");
        progressDialog.setCancelable(false);
        getdivision();
        categorys();

        // zilla();
        // police();


        POSTDATA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // register();
                //  imagearray();
                datavalidation();

            }
        });

        DivisionList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (DivisionList.getSelectedItemId() != 0) {
                    DisHide.setVisibility(View.VISIBLE);
                    zilla(String.valueOf(data.get(position).getId()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        DistrictList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (DistrictList.getSelectedItemId() != 0) {
                    PoliceHide.setVisibility(View.VISIBLE);
                    police(String.valueOf(datas.get(position).getId()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ImageOne.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent();
                intent.setType("image/*");
                // intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, Constant.PICK_PHOTO_ONE);
            } else {
                ActivityCompat.requestPermissions(AddPostScreen.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        });
        Imagetwo.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, Constant.PICK_PHOTO_TWO);
            } else {
                ActivityCompat.requestPermissions(AddPostScreen.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        });

        Imagethree.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, Constant.PICK_PHOTO_THREE);
            } else {
                ActivityCompat.requestPermissions(AddPostScreen.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        });

        Imagefour.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, Constant.PICK_PHOTO_FOUR);
            } else {
                ActivityCompat.requestPermissions(AddPostScreen.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        });

        Imagefive.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, Constant.PICK_PHOTO_FIVE);
            } else {
                ActivityCompat.requestPermissions(AddPostScreen.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        });
    }

    public void categorys() {
        progressDialog.show();
        api.getcat().enqueue(new Callback<CategoryContainer>() {
            @Override
            public void onResponse(Call<CategoryContainer> call, Response<CategoryContainer> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    catgoryList = response.body().getData();
                    catgoryList.add(0, new CategoryList(0, "Select Category"));
                    Category_Spinner_Adaptar adaptar = new Category_Spinner_Adaptar(catgoryList, getApplicationContext());
                    Category.setAdapter(adaptar);
                }
            }

            @Override
            public void onFailure(Call<CategoryContainer> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
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


                }

            }

            @Override
            public void onFailure(Call<DivisionContainer> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Something wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void zilla(String id) {
        progressDialog.show();
        api.getDistrictbydivision(id).enqueue(new Callback<DistrictshowContainer>() {
            @Override
            public void onResponse(Call<DistrictshowContainer> call, Response<DistrictshowContainer> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    datas = response.body().getData();
                    datas.add(0, new DistrictShow(0, "Select District"));
                    DistrictShowByDivision cusadaptar = new DistrictShowByDivision(datas, getApplicationContext());
                    DistrictList.setAdapter(cusadaptar);
                    Log.d("District", datas.toString());

                }
            }

            @Override
            public void onFailure(Call<DistrictshowContainer> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void police(String id) {
        progressDialog.show();
        api.getpolicebydistrict(id).enqueue(new Callback<PolicestationByDistrictContainer>() {
            @Override
            public void onResponse(Call<PolicestationByDistrictContainer> call, Response<PolicestationByDistrictContainer> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    datass = response.body().getData();
                    datass.add(0, new PolicestationShowByDistrict(0, "Select Police Station"));
                    PoliceStationShowByDisAdaptar policeadaptar = new PoliceStationShowByDisAdaptar(datass, getApplicationContext());
                    Police_Station.setAdapter(policeadaptar);
                }
            }

            @Override
            public void onFailure(Call<PolicestationByDistrictContainer> call, Throwable t) {

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
                ImageOne.setImageBitmap(bitmap);
            } else {
                Log.e("REQ", "Bitmap null");
            }


        } else if (resultCode == RESULT_OK && requestCode == Constant.PICK_PHOTO_TWO && data != null) {
            imageUritwo = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUritwo);
                f2 = new File(getCacheDir(), "Image2");
                bitmap = getResizedBitmap(bitmap, 800);

                ByteArrayOutputStream bos = new ByteArrayOutputStream();

                bitmap.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos);
                byte[] bitmapdata = bos.toByteArray();
                iamStringtwo = Base64.encodeToString(bitmapdata, Base64.NO_WRAP);

                FileOutputStream foss = null;
                try {
                    foss = new FileOutputStream(f2);

                } catch (FileNotFoundException e) {
                    Log.e("REQ", e.toString());
                }
                try {
                    foss.write(bitmapdata);
                    foss.flush();
                    foss.close();
                    // isTrade = true;
                } catch (IOException e) {
                    Log.e("REQ", e.toString());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            if (bitmap != null) {
                Imagetwo.setImageBitmap(bitmap);
            } else {
                Log.e("REQ", "Bitmap null");
            }

        } else if (resultCode == RESULT_OK && requestCode == Constant.PICK_PHOTO_THREE && data != null) {
            imageUrithree = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUrithree);
                f3 = new File(getCacheDir(), "Image3");
                bitmap = getResizedBitmap(bitmap, 800);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();

                bitmap.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos);
                byte[] bitmapdata = bos.toByteArray();
                iamStringthree = Base64.encodeToString(bitmapdata, Base64.NO_WRAP);


                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(f3);

                } catch (FileNotFoundException e) {
                    Log.e("REQ", e.toString());
                }
                try {
                    fos.write(bitmapdata);
                    fos.flush();
                    fos.close();
                    //isTin = true;
                } catch (IOException e) {
                    Log.e("REQ", e.toString());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            if (bitmap != null) {
                Imagethree.setImageBitmap(bitmap);
            } else {
                Log.e("REQ", "Bitmap null");
            }

        } else if (resultCode == RESULT_OK && requestCode == Constant.PICK_PHOTO_FOUR && data != null) {
            imageUrifour = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUrifour);
                f4 = new File(getCacheDir(), "Image4");
                bitmap = getResizedBitmap(bitmap, 800);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();

                bitmap.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos);
                byte[] bitmapdata = bos.toByteArray();
                iamStringfour = Base64.encodeToString(bitmapdata, Base64.NO_WRAP);


                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(f4);

                } catch (FileNotFoundException e) {
                    Log.e("REQ", e.toString());
                }
                try {
                    fos.write(bitmapdata);
                    fos.flush();
                    fos.close();
                    //isTin = true;
                } catch (IOException e) {
                    Log.e("REQ", e.toString());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            if (bitmap != null) {
                Imagefour.setImageBitmap(bitmap);
            } else {
                Log.e("REQ", "Bitmap null");
            }

        } else if (resultCode == RESULT_OK && requestCode == Constant.PICK_PHOTO_FIVE && data != null) {
            imageUrifive = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUrifive);
                f5 = new File(getCacheDir(), "Image5");
                bitmap = getResizedBitmap(bitmap, 800);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();

                bitmap.compress(Bitmap.CompressFormat.JPEG, 100 /*ignored for PNG*/, bos);
                byte[] bitmapdata = bos.toByteArray();
                iamStringfive = Base64.encodeToString(bitmapdata, Base64.NO_WRAP);


                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(f5);

                } catch (FileNotFoundException e) {
                    Log.e("REQ", e.toString());
                }
                try {
                    fos.write(bitmapdata);
                    fos.flush();
                    fos.close();
                    //isTin = true;
                } catch (IOException e) {
                    Log.e("REQ", e.toString());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            if (bitmap != null) {
                Imagefive.setImageBitmap(bitmap);
            } else {
                Log.e("REQ", "Bitmap null");
            }

        }

    }

//    public void imagearray() {
//
//        if (image != null) {
//            image.add(imgString);
//            image.add(iamStringtwo);
//            image.add(iamStringthree);
//            image.add(iamStringfour);
//            image.add(iamStringfive);
//            Log.d("setimagearray", image.get(0));
//
//            for (int i = 1; i <= image.size(); i++) {
//                if (i == 1) imageone = image.get(i - 1);
//                if (i == 2) imagetwo = image.get(i - 1);
//                if (i == 3) imagethree = image.get(i - 1);
//                if (i == 4) imagefour = image.get(i - 1);
//                if (i == 5) imagefive = image.get(i - 1);
//            }
//
//            //   Log.d("print1",imageone);
//            //    Log.d("print2",imagetwo);
//            //   Log.d("print3",imagethree);
//            // Log.d("print4",imagefour);
//            //   Log.d("print5",imagefive);
//            //   Log.d("setimagearray1", String.valueOf(imgString));
//        }
//
//
//    }

    public void datavalidation() {
        if (!TextUtils.isEmpty(Title.getText().toString())) {
            if (DivisionList.getSelectedItemPosition() > 0) {
                if (DistrictList.getSelectedItemPosition() > 0) {
                    if (Police_Station.getSelectedItemPosition()>0){
                        if (Category.getSelectedItemPosition()>0){
                            if (!TextUtils.isEmpty(Description.getText().toString())){
                                if (!TextUtils.isEmpty(Price.getText().toString())){
                                    if (!TextUtils.isEmpty(Address.getText().toString())){
                                        if (!TextUtils.isEmpty(imgString)){
                                            register();
                                        }else{
                                            Toast.makeText(getApplicationContext(), "Please select Image", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                    else{
                                        Address.setError("Please Input Address");
                                        Address.requestFocus();
                                    }

                                }else{
                                    Price.setError("Please Input Price");
                                    Price.requestFocus();
                                }

                            }else{
                                Description.setError("Please Input Description Your Post");
                                Description.requestFocus();
                            }

                        }else{
                            Toast.makeText(getApplicationContext(), "Please Select Category", Toast.LENGTH_SHORT).show();

                        }

                    }else{
                        Toast.makeText(getApplicationContext(), "Please Select Police Station", Toast.LENGTH_SHORT).show();

                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Please Select District", Toast.LENGTH_SHORT).show();

                }

            } else {
                Toast.makeText(getApplicationContext(), "Please Select Division", Toast.LENGTH_SHORT).show();
            }

        } else {
            Title.setError("Please Input Title");
            Title.requestFocus();
        }


    }


    public void register() {
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


        //  imagearray();

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
                .addFormDataPart("categoryId", String.valueOf(catgoryList.get(Category.getSelectedItemPosition()).getId().toString()))
                .addFormDataPart("image1", "data:image/jpeg;base64," + imgString)
                .addFormDataPart("image2", "data:image/jpeg;base64," + iamStringtwo)
                .addFormDataPart("image3", "data:image/jpeg;base64," + iamStringthree)
                .addFormDataPart("image4", "data:image/jpeg;base64," + iamStringfour)
                .addFormDataPart("image5", "data:image/jpeg;base64," + iamStringfive)
                .build();

        progressDialog.show();
        api.InsertPost(requestBody).enqueue(new Callback<InsertPostResponseContainer>() {
            @Override
            public void onResponse(Call<InsertPostResponseContainer> call,
                                   Response<InsertPostResponseContainer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    progressDialog.dismiss();
                    String message = response.body().getMessage();
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), DashBoardScreen.class);
                    startActivity(intent);
                    finish();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Post not added", Toast.LENGTH_SHORT).show();

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