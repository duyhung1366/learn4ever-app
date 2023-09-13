package com.example.mylap.page.userInfo;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mylap.R;
import com.example.mylap.api.ConfigApi;
import com.example.mylap.models.User;
import com.example.mylap.responsive.LoginRes;
import com.example.mylap.services.ApiService;
import com.example.mylap.utils.ProgressDialogUtils;
import com.example.mylap.utils.SharedPreferencesUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserInfo extends AppCompatActivity {

    private TextView tvUsername;
    private TextView tvEmail;
    private TextView tvUser;
    private TextView tvNumber;
    private TextView tvGender;

    private Button btnFix;
    private Button btnExit;
    ConfigApi configApi = new ConfigApi();
    private Context userinfoContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        this.userinfoContext = this;

        tvUser = findViewById(R.id.tvUser);
        tvEmail = findViewById(R.id.tvEmail);
        tvUsername = findViewById(R.id.tvUsername);
        tvNumber = findViewById(R.id.tvNumber);
        tvGender = findViewById(R.id.tvGender);
        btnFix = findViewById(R.id.btnFix);
        btnExit = findViewById(R.id.btnExit);


        String token = SharedPreferencesUtils.getStringToSharedPreferences(this, "token");
        // api get category
        ProgressDialog progressDialog = ProgressDialogUtils.createProgressDialog(userinfoContext);
        progressDialog.show();

        // Tạo Retrofit client
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("/api-mobile/user")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Tạo instance của UserService từ Retrofit client
        ApiService userService = retrofit.create(ApiService.class);

        // Gửi yêu cầu lấy thông tin người dùng
        Call<User> call = userService.getUserInfo("Bearer " + token);
        call.enqueue(new Callback<User>() {

            // Gửi yêu cầu lấy thông tin người dùng
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        User user = response.body();
                        if (user != null) {
                            // Gán thông tin người dùng lên các thành phần hiển thị
                            tvUsername.setText("UserName: " + user.getUserName());
                            tvEmail.setText("Email: " + user.getEmail());
                            tvUser.setText("User: " + user.getName());
                            tvNumber.setText("Phone: " + user.getPhoneNumber());
                            tvGender.setText("Gender: " + user.getGender());

                    }

                    progressDialog.dismiss();
                } else {
                    progressDialog.dismiss();
                    Log.d("TAG", "error");
                    Toast.makeText(getApplicationContext(), "server bị lỗi", Toast.LENGTH_SHORT).show();
                    // Xử lý lỗi khi response không thành
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Xử lý lỗi khi request thất bại
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "server bị lỗi", Toast.LENGTH_SHORT).show();
                Log.d("TAG", "error api:  " + t);
            }
        });

        btnFix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                // trở về activity trước đó
            }
        });
    }
}