package com.example.my_application.Screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.session.MediaSession;
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
import com.example.my_application.Adaptar.CommentListAdaptar;
import com.example.my_application.Adaptar.Dashboard_adaptar;
import com.example.my_application.Data_Model.CommentList.CommentContainer;
import com.example.my_application.Data_Model.Dashboard.DashboardContainer;
import com.example.my_application.Data_Model.Profile.ProfileContainer;
import com.example.my_application.Data_Model.SinglePost.SinglePostContainer;
import com.example.my_application.Network.Api;
import com.example.my_application.Network.RetrofitClient;
import com.example.my_application.R;
import com.example.my_application.Util.Constant;
import com.example.my_application.Util.MySharedPreference;
import com.google.gson.JsonObject;
import com.skydoves.expandablelayout.ExpandableAnimation;
import com.skydoves.expandablelayout.ExpandableLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SinglePostViewScreen extends AppCompatActivity {
    Api api;
    ProgressDialog progressDialog;
    TextView textView, Price, Post_Description;
    ImageView imageView, Send_Comments;
    Button CallButton;
    Boolean isExpended = false;
    String PhoneNumber;
    ExpandableLayout expandableLayout;
    RecyclerView recyclerView,CommentList_Show;
    EditText Write_comments;
    String PostId;
    String Token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_post_view_screen);

        textView = findViewById(R.id.text_show);
        imageView = findViewById(R.id.post_image);
        Price = findViewById(R.id.price_post);
        Post_Description = findViewById(R.id.post_description);
        recyclerView = findViewById(R.id.dash_board_list);
        CallButton = findViewById(R.id.call_button);
        expandableLayout = findViewById(R.id.expand);
        PostId = getIntent().getStringExtra(Intent.EXTRA_UID);

        expandableLayout.setExpandableAnimation(ExpandableAnimation.ACCELERATE);
        Write_comments = expandableLayout.secondLayout.findViewById(R.id.insert_comments);
        Send_Comments = expandableLayout.secondLayout.findViewById(R.id.send_comments);
        CommentList_Show=expandableLayout.secondLayout.findViewById(R.id.comment_list);


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
                if (response.isSuccessful()&&response.body()!=null){
                    progressDialog.dismiss();
                    CommentList_Show.setHasFixedSize(true);
                    CommentList_Show.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                            LinearLayoutManager.VERTICAL, false));
                    CommentListAdaptar commentadaptar=new CommentListAdaptar(response.body().getComments(),getApplicationContext());
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
                    Glide.with(getApplicationContext()).load("https://rentservicebd.com/public/api/image/" +
                            response.body().getPost().getImage1()).into(imageView);
                    textView.setText(response.body().getPost().getTitle());
                    Price.setText(response.body().getPost().getPrice());
                    Post_Description.setText(response.body().getPost().getDescription());
                    // Toast.makeText(getApplicationContext(), "Post Successfull", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SinglePostContainer> call, Throwable t) {

            }
        });

        api.getprofile(Token).enqueue(new Callback<ProfileContainer>() {
            @Override
            public void onResponse(Call<ProfileContainer> call, Response<ProfileContainer> response) {
                PhoneNumber = String.valueOf(response.body().getUserInfo().getMobile());
                // CallButton.setText(PhoneNumber);

                CallButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("phone_number", PhoneNumber);
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + PhoneNumber));
                        startActivity(intent);
                    }
                });
                // Log.d("phone_number",PhoneNumber);

            }

            @Override
            public void onFailure(Call<ProfileContainer> call, Throwable t) {

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