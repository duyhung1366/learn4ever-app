package com.example.mylap.page.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mylap.R;
import com.example.mylap.api.ConfigApi;
import com.example.mylap.models.Category;
import com.example.mylap.page.auth.LoginActivity;
import com.example.mylap.page.auth.RegisterActivity;
import com.example.mylap.page.userInfo.UserInfo;
import com.example.mylap.responsive.GetCategory;
import com.example.mylap.utils.ProgressDialogUtils;
import com.example.mylap.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    final int checkSessionSuccess = 0;
    final int checkSessionFail = 1;
    final int checkSessionNotMatch = 2;
    final int loadListCategorySuccess = 3;
    final int loadListCategoryFail = 4;
    ConfigApi configApi = new ConfigApi();
    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private List<Category> categoryList;
    private Context activity;
    Button btn_Login;
    Button btn_register;
    Button btn_Logout;
    ImageView showUserinfo;
    ProgressDialog progressDialogLoadSession;
    ProgressDialog progressDialogLoadListCategory;

    Thread threadCheckSession;
    Thread threadLoadListCategory;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case checkSessionSuccess:
                    //check session successful
                    Log.d("TAG", "check session successful! ");
                    btn_Login.setVisibility(View.GONE);
                    btn_register.setVisibility(View.GONE);
                    btn_Logout.setVisibility(View.VISIBLE);
                    showUserinfo.setVisibility(View.VISIBLE);
                    progressDialogLoadSession.dismiss();
                    progressDialogLoadListCategory = new ProgressDialogUtils().createProgressDialog(activity);
                    progressDialogLoadListCategory.show();

                    threadLoadListCategory.start();

                    break;
                case checkSessionNotMatch:
                    // het phien dang nhap
                    Log.d("TAG", "checkSessionNotMatch! ");

                    progressDialogLoadSession.dismiss();
                    SharedPreferencesUtils.removeStringToSharedPreferences(activity, "token");
                    Intent intent = new Intent(activity, LoginActivity.class);
                    activity.startActivity(intent);
                    finish();
                    break;
                case checkSessionFail:
                    Log.d("TAG", "checkSessionFail! ");
                    Toast.makeText(getApplicationContext(), "server bị lỗi", Toast.LENGTH_SHORT).show();
                    progressDialogLoadSession.dismiss();
                    break;
                case loadListCategorySuccess:
                    Log.d("TAG", "loadListCategorySuccess! ");

                    progressDialogLoadListCategory.dismiss();
                    GetCategory getCategory = (GetCategory) msg.obj;
                    for (int i = 0; i < getCategory.getData().size(); i++) {
                        categoryList.add(getCategory.getData().get(i));
                    }
                    adapter.notifyDataSetChanged();
                    break;
                case loadListCategoryFail:
                    Log.d("TAG", "loadListCategoryFail! ");
                    Toast.makeText(getApplicationContext(), "server bị lỗi", Toast.LENGTH_SHORT).show();
                    progressDialogLoadListCategory.dismiss();
                    break;
            }
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstantState) {
        super.onCreate(savedInstantState);
        setContentView(R.layout.activity_home);
        categoryList = new ArrayList<>();
        this.activity = this;

        btn_Login = findViewById(R.id.btnLogin);
        btn_register = findViewById(R.id.btnRegister);
        btn_Logout = findViewById(R.id.btnLogout);
        showUserinfo = findViewById(R.id.userinfo);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        adapter = new CustomAdapter(categoryList, this);
        recyclerView.setAdapter(adapter);

        String token = SharedPreferencesUtils.getStringToSharedPreferences(this, "token");

        progressDialogLoadSession = new ProgressDialogUtils().createProgressDialog(activity);
        progressDialogLoadSession.show();
        // thread
        threadCheckSession = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("TAG", "threadCheckSession ");
                Call<Void> call = configApi.getApiService().session(token);
                try {
                    Response<Void> response = call.execute();
                    int statusCode = response.code();
                    if (statusCode == 200) {
                        handler.sendEmptyMessage(checkSessionSuccess);
                    } else {
                        // het phien dang nhap
                        handler.sendEmptyMessage(checkSessionNotMatch);;
                    }
                } catch (Throwable t) {
                    handler.sendEmptyMessage(checkSessionFail);
                    Log.d("TAG", "error api:  " + t);
                }
            }
        });
        threadLoadListCategory = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("TAG", "threadLoadListCategory ");
                Call<GetCategory> call = configApi.getApiService().getCategorys(1);
                try {
                    Response<GetCategory> response = call.execute();
                    if (response.isSuccessful()) {
                        GetCategory data = response.body();
                        Message message = handler.obtainMessage(loadListCategorySuccess, data);
                        handler.sendMessage(message);
                    } else {
                        handler.sendEmptyMessage(loadListCategoryFail);
                        Log.d("TAG", "error");
                    }
                } catch (Throwable t) {
                    handler.sendEmptyMessage(loadListCategoryFail);
                    Log.d("TAG", "error api:  " + t);
                }
            }
        });

        threadCheckSession.start();

//        configApi.getApiService().session(token).enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//                int statusCode = response.code();
//                progressDialogLoadSession.dismiss();
//                if (statusCode == 200) {
//                    // oke
//                    btn_Login.setVisibility(View.GONE);
//                    btn_register.setVisibility(View.GONE);
//                    btn_Logout.setVisibility(View.VISIBLE);
//                    showUserinfo.setVisibility(View.VISIBLE);
//                    // api get category
//                    progressDialogLoadListCategory = new ProgressDialogUtils().createProgressDialog(activity);
//
//                    progressDialogLoadListCategory.show();
//
//                    configApi.getApiService().getCategorys(1).enqueue(new Callback<GetCategory>() {
//                        @Override
//                        public void onResponse(Call<GetCategory> call, Response<GetCategory> response) {
//                            progressDialogLoadListCategory.dismiss();
//                            if (response.isSuccessful()) {
//                                GetCategory data = response.body();
////                              Log.d("TAG", "Dữ liệu của bạn: " + data.getData().get(0).getName());
//                                // Cập nhật dữ liệu vào Adapter và cập nhật ListView
//                                for (int i = 0; i < data.getData().size(); i++) {
//                                    categoryList.add(data.getData().get(i));
//                                }
//                                adapter.notifyDataSetChanged();
//                            } else {
//                                progressDialogLoadListCategory.dismiss();
//                                Log.d("TAG", "error");
//                                // Xử lý lỗi khi response không thành
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<GetCategory> call, Throwable t) {
//                            // Xử lý lỗi khi request thất bại
//                            progressDialogLoadListCategory.dismiss();
//                            Toast.makeText(getApplicationContext(), "server bị lỗi", Toast.LENGTH_SHORT).show();
//                            Log.d("TAG", "error api:  " + t);
//                        }
//                    });
//                } else {
//                    // het phien dang nhap
//                    SharedPreferencesUtils.removeStringToSharedPreferences(activity, "token");
//                    Intent intent = new Intent(activity, LoginActivity.class);
//                    activity.startActivity(intent);
//                    finish();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//                progressDialogLoadSession.dismiss();
//                Toast.makeText(getApplicationContext(), "server bị lỗi", Toast.LENGTH_SHORT).show();
//                Log.d("TAG", "error api:  " + t);
//            }
//        });

//        MutableLiveData<Boolean> isLoginLiveData = AuthManager.getInstance().getIsLoginLiveData();
//        isLoginLiveData.observe(this, new Observer<Boolean>() {
//            @Override
//            public void onChanged(Boolean isLogin) {
//                // Đây là nơi bạn xử lý sự thay đổi của trạng thái đăng nhập
//                if (isLogin) {
//                    // Người dùng đã đăng nhập
//
//                } else {
//                    // chua login
//
//                }
//            }
//        });\

        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btn_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesUtils.removeStringToSharedPreferences(activity, "token");
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        showUserinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, UserInfo.class);
                startActivity(intent);
            }
        });

    }
}
