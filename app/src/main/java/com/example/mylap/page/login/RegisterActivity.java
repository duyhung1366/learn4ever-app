package com.example.mylap.page.login;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import com.example.mylap.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etEmail;
    private EditText etUser;
    private EditText etNumber;
    private EditText etPassword;
    private EditText etRePassword;
    private RadioGroup radioGroupGender;
    private TextView textViewError;
    private Button btnRegister;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register);

            etUsername = findViewById(R.id.etUsername);
            etEmail = findViewById(R.id.etEmail);
            etUser = findViewById(R.id.etUser);
            etNumber = findViewById(R.id.etNumber);
            etPassword = findViewById(R.id.etPassword);
            etRePassword = findViewById(R.id.etRePassword);
            radioGroupGender = findViewById(R.id.radioGioiTinh);
            btnRegister = findViewById(R.id.btnRegister);
            textViewError = findViewById(R.id.textViewError);

            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String username = etUsername.getText().toString();
                    String email = etEmail.getText().toString();
                    String user = etUser.getText().toString();
                    String number = etNumber.getText().toString();
                    String password = etPassword.getText().toString();
                    String repassword = etRePassword.getText().toString();

                    if (!password.equals(repassword)) {
                        textViewError.setText("Mật khẩu không khớp");
                        return;
                    }

                    int selectedGenderId = radioGroupGender.getCheckedRadioButtonId();
                    RadioButton selectedGenderRadioButton = findViewById(selectedGenderId);
                    String gender = selectedGenderRadioButton.getTag().toString();

                    // Kiểm tra dữ liệu và xử lý đăng ký tại đây
                    Log.d("TAG", "username : " + username + " pwd: " + password + " email : " + email  + "use ; " + user + " number : " + number + " gender : " + gender);
                }
            });
        }
    }


