package com.example.mylap.page.userInfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mylap.R;
import com.example.mylap.api.ConfigApi;
import com.example.mylap.page.auth.LoginActivity;
import com.example.mylap.page.auth.RegisterActivity;
import com.example.mylap.page.home.HomeActivity;

public class UserInfoActivity extends AppCompatActivity {

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
                setContentView(R.layout.user_info_page);
                this.userinfoContext = this;

                tvUser = findViewById(R.id.tvUser);
                tvEmail = findViewById(R.id.tvEmail);
                tvUsername = findViewById(R.id.tvUsername);
                tvNumber = findViewById(R.id.tvNumber);
                tvGender = findViewById(R.id.tvGender);
                btnFix = findViewById(R.id.btnFix);
                btnExit = findViewById(R.id.btnExit);

                // Nhận dữ liệu đã truyền từ intent
                Intent intent = getIntent();
                String username = intent.getStringExtra("username");
                String email = intent.getStringExtra("email");
                String user = intent.getStringExtra("user");
                String phoneNumber = intent.getStringExtra("phoneNumber");
                String gender = intent.getStringExtra("gender");

                // Hiển thị thông tin người dùng
                tvUser.setText("Tên Người Dùng: " + user);
                tvEmail.setText("Email: " + email);
                tvUsername.setText("Tên Đăng Nhập: " + username);
                tvNumber.setText("Số điện thoại: " + phoneNumber);
                tvGender.setText("Giới tính: " + gender);

                btnFix.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                Intent intent = new Intent(UserInfoActivity.this, RegisterActivity.class);
                                startActivity(intent);
                        }
                });

                btnExit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                Intent intent = new Intent(UserInfoActivity.this, HomeActivity.class);
                                startActivity(intent);
                        }
                });
        }



}
