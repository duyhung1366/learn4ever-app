package com.example.mylap.page.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mylap.R;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        // Ánh xạ các thành phần trong giao diện
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        // Đăng ký bắt sự kiện khi nút đăng nhập được nhấn
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy tên đăng nhập và mật khẩu từ EditText tương ứng
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                Log.d("TAG", "username : " + username + " pwd: " + password);

            }
        });
    }
}