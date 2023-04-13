package com.example.my_application.Screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.AnimationTypes;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.my_application.Adaptar.CommentListAdaptar;
import com.example.my_application.Data_Model.CommentList.CommentContainer;
import com.example.my_application.Data_Model.SinglePost.SinglePostContainer;
import com.example.my_application.Network.Api;
import com.example.my_application.Network.RetrofitClient;
import com.example.my_application.R;
import com.example.my_application.Util.Constant;
import com.example.my_application.Util.MySharedPreference;
import com.google.gson.JsonObject;
import com.skydoves.expandablelayout.ExpandableAnimation;
import com.skydoves.expandablelayout.ExpandableLayout;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SinglePostViewScreen extends AppCompatActivity {
    Api api;
    ProgressDialog progressDialog;
    TextView textView, Price, Post_Description, Post_Address;
    ImageView Send_Comments;
    Button CallButton;
    Boolean isExpended = false;
    String PhoneNumber;
    ExpandableLayout expandableLayout;
    RecyclerView recyclerView, CommentList_Show;
    EditText Write_comments;
    String PostId;
    String Token;
    ImageSlider imageSlider;


    ArrayList<SlideModel> slideImage = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_post_view_screen);

        textView = findViewById(R.id.text_show);

        Price = findViewById(R.id.price_post);
        Post_Description = findViewById(R.id.post_description);
        recyclerView = findViewById(R.id.dash_board_list);
        CallButton = findViewById(R.id.call_button);
        expandableLayout = findViewById(R.id.expand);
        PostId = getIntent().getStringExtra(Intent.EXTRA_UID);
        imageSlider = findViewById(R.id.image_slide);
        Post_Address = findViewById(R.id.post_address);


        expandableLayout.setExpandableAnimation(ExpandableAnimation.ACCELERATE);
        Write_comments = expandableLayout.secondLayout.findViewById(R.id.insert_comments);
        Send_Comments = expandableLayout.secondLayout.findViewById(R.id.send_comments);
        CommentList_Show = findViewById(R.id.comment_list);


        expandableLayout.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpended) {
                    expandableLayout.collapse();
                    isExpended = false;
                } else {
                    expandableLayout.expand();
                    isExpended = true;
                }

            }
        });

        api = RetrofitClient.difBaseUrle().create(Api.class);
        Token = MySharedPreference.getInstance(getApplicationContext()).getString(Constant.TOKEN, "not found");

        progressDialog = new ProgressDialog(SinglePostViewScreen.this);
        progressDialog.setMessage("Please Wait......");
        progressDialog.setCancelable(false);
        progressDialog.show();

        api.getallComments(PostId).enqueue(new Callback<CommentContainer>() {
            @Override
            public void onResponse(Call<CommentContainer> call, Response<CommentContainer> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    progressDialog.dismiss();
                    CommentList_Show.setHasFixedSize(true);
                    CommentList_Show.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                            LinearLayoutManager.VERTICAL, false));
                    CommentListAdaptar commentadaptar = new CommentListAdaptar(response.body().getComments(), getApplicationContext());
                    CommentList_Show.setAdapter(commentadaptar);
                }
            }

            @Override
            public void onFailure(Call<CommentContainer> call, Throwable t) {

            }
        });

        api.getsinglepost(PostId).enqueue(new Callback<SinglePostContainer>() {
            @Override
            public void onResponse(Call<SinglePostContainer> call, Response<SinglePostContainer> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {

                    if (String.valueOf(response.body().getPost().getImage1()) != null) {
                        slideImage.add(new SlideModel("https://rentservicebd.com/public/api/image/" + String.valueOf(response.body().getPost().getImage1()), ScaleTypes.CENTER_CROP));
                    }
                    if (String.valueOf(response.body().getPost().getImage2()) != null) {
                        slideImage.add(new SlideModel("https://rentservicebd.com/public/api/image/" + String.valueOf(response.body().getPost().getImage2()), ScaleTypes.CENTER_CROP));

                    }
                    if (String.valueOf(response.body().getPost().getImage3()) != null) {
                        slideImage.add(new SlideModel("https://rentservicebd.com/public/api/image/" + String.valueOf(response.body().getPost().getImage3()), ScaleTypes.CENTER_CROP));

                    }
                    if (String.valueOf(response.body().getPost().getImage4()) != null) {
                        slideImage.add(new SlideModel("https://rentservicebd.com/public/api/image/" + String.valueOf(response.body().getPost().getImage4()), ScaleTypes.CENTER_CROP));
                    }
                    if (String.valueOf(response.body().getPost().getImage5()) != null) {
                        slideImage.add(new SlideModel("https://rentservicebd.com/public/api/image/" + String.valueOf(response.body().getPost().getImage5()), ScaleTypes.CENTER_CROP));

                    }

                    imageSlider.setImageList(slideImage);
                    imageSlider.setSlideAnimation(AnimationTypes.ZOOM_OUT);
                    Log.d("slider", String.valueOf(slideImage));

                    imageSlider.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onItemSelected(int i) {
                            String Postid = String.valueOf(response.body().getPost().getId());
                            Intent intent = new Intent(getApplicationContext(), Image_Preview.class);
                            intent.putExtra(Intent.EXTRA_UID, Postid);
                            startActivity(intent);
                        }

                        @Override
                        public void doubleClick(int i) {

                        }
                    });

                    textView.setText(response.body().getPost().getTitle());
                    Price.setText(response.body().getPost().getPrice());
                    Post_Description.setText(response.body().getPost().getDescription());
                    PhoneNumber = String.valueOf(response.body().getPost().getMobile());
                    Post_Address.setText(String.valueOf(response.body().getPost().getAddress()));
                    CallButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d("phone_number", PhoneNumber);
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:" + PhoneNumber));
                            startActivity(intent);
                        }
                    });
                    // Toast.makeText(getApplicationContext(), "Post Successfull", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SinglePostContainer> call, Throwable t) {

            }
        });


        Comments();

    }

    public void Comments() {
        Send_Comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                api.insert_comments(PostId, Write_comments.getText().toString(), Token).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        progressDialog.show();
                        if (response.isSuccessful() && response.body() != null) {
                            Write_comments.getText().clear();
                            progressDialog.dismiss();
                            String mes = String.valueOf(response.message().toString());
                            Toast.makeText(getApplicationContext(), mes, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}