package com.example.mylap.page.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.mylap.R;
import com.example.mylap.ViewModel.ViewModelMain;
import com.example.mylap.api.ConfigApi;
import com.example.mylap.page.home.HomeActivity;
import com.example.mylap.responsive.LoginRes;
import com.example.mylap.utils.ProgressDialogUtils;
import com.example.mylap.utils.SharedPreferencesUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    ConfigApi configApi = new ConfigApi();
    private Context registerContext;
    private ViewModelMain viewModelMain;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register);
            this.registerContext = this;

            viewModelMain = new ViewModelProvider(this).get(ViewModelMain.class);

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
                    String phoneNumber = etNumber.getText().toString();
                    String password = etPassword.getText().toString();
                    String repassword = etRePassword.getText().toString();

                    int selectedGenderId = radioGroupGender.getCheckedRadioButtonId();
                    RadioButton selectedGenderRadioButton = findViewById(selectedGenderId);
                    String gender = "";
                    if(selectedGenderRadioButton != null) {
                        gender = selectedGenderRadioButton.getTag().toString();
                    }

                    if(username.equals("")) {
                        return;
                    }
                    if(email.equals("")) {
                        return;
                    }
                    if(user.equals("")){
                        return;
                    }
                    if(phoneNumber.equals("")) {
                        return;
                    }
                    if(gender.equals("")) {
                        return;
                    }
                    if(password.equals("")) {
                        return;
                    }
                    if(repassword.equals("")) {
                        return;
                    }
                    if (!password.equals(repassword)) {
                        textViewError.setText("Mật khẩu không khớp");
                        return;
                    }

                    // Kiểm tra dữ liệu và xử lý đăng ký tại đây
//                    Log.d("TAG", "username : " + username + " pwd: " + password + " email : " + email  + "use ; " + user + " number : " + phoneNumber + " gender : " + gender);
                    ProgressDialog progressDialog = ProgressDialogUtils.createProgressDialog(registerContext);
                    progressDialog.show();
                    configApi.getApiService().register(username, password, user, email, phoneNumber, Integer.parseInt(gender)).enqueue(new Callback<LoginRes>() {
                        @Override
                        public void onResponse(Call<LoginRes> call, Response<LoginRes> response) {
                            if (response.isSuccessful()) {
                                int status = response.body().getStatus();
                                if(status == 0) {
                                    String token = response.body().getToken();

                                    if(!token.equals("")) {
                                        SharedPreferencesUtils.saveStringToSharedPreferences(registerContext,"token", token);

//                                        viewModelMain.setIsLogin(true);

                                        Toast.makeText(getApplicationContext(), "Đăng kí thành công", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(registerContext, HomeActivity.class);
                                        registerContext.startActivity(intent);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "server có lỗi , vui lòng thao tác lại", Toast.LENGTH_SHORT).show();
                                    }

                                } else if(status == 1) {
                                    Toast.makeText(getApplicationContext(), "email hoặc tên đăng nhập đã tồn tại", Toast.LENGTH_SHORT).show();
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
                        public void onFailure(Call<LoginRes> call, Throwable t) {
                            // Xử lý lỗi khi request thất bại
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "server bị lỗi", Toast.LENGTH_SHORT).show();
                            Log.d("TAG", "error api:  " + t);
                        }
                    });

                }
            });
        }
    }


