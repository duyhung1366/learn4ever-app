package com.example.mylap.page.home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mylap.R;
import com.example.mylap.api.ConfigApi;
import com.example.mylap.models.Category;
import com.example.mylap.page.auth.LoginActivity;
import com.example.mylap.page.auth.RegisterActivity;
import com.example.mylap.responsive.GetCategory;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    ConfigApi configApi = new ConfigApi();
    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private List<Category> categoryList;

    @Override
    protected void onCreate(Bundle savedInstantState) {
        super.onCreate(savedInstantState);
        setContentView(R.layout.home_page);
        configApi = new ConfigApi();
        categoryList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        adapter = new CustomAdapter(categoryList, this);
        recyclerView.setAdapter(adapter);

        // api get category
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        progressDialog.show();

        configApi.getApiService().getCategorys(1).enqueue(new Callback<GetCategory>() {
            @Override
            public void onResponse(Call<GetCategory> call, Response<GetCategory> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    GetCategory data = response.body();
//                    Log.d("TAG", "Dữ liệu của bạn: " + data.getData().get(0).getName());
                    // Cập nhật dữ liệu vào Adapter và cập nhật ListView
                    for (int i = 0; i < data.getData().size(); i++) {
                        categoryList.add(data.getData().get(i));
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    progressDialog.dismiss();
                    Log.d("TAG", "error");
                    // Xử lý lỗi khi response không thành
                }
            }

            @Override
            public void onFailure(Call<GetCategory> call, Throwable t) {
                // Xử lý lỗi khi request thất bại
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "server bị lỗi", Toast.LENGTH_SHORT).show();
                Log.d("TAG", "error api:  " + t);
            }
        });

        // api get total leanr and exam in course
//        configApi.getApiService().countTopicInCourse("63ae88a2fe74a345583ff56e").enqueue(new Callback<CountLearnRes>() {
//            @Override
//            public void onResponse(Call<CountLearnRes> call, Response<CountLearnRes> response) {
//                if (response.isSuccessful()) {
//                    CountLearnRes data = response.body();
//                    Log.d("TAG", "data count : " + data.getData().getTotalLearn() + " total exam : " + data.getData().getTotalExam());
//
//                } else {
//                    Log.d("TAG", "error");
//                    // Xử lý lỗi khi response không thành
//                }
//            }
//
//            @Override
//            public void onFailure(Call<CountLearnRes> call, Throwable t) {
//                // Xử lý lỗi khi request thất bại
//                Toast.makeText(getApplicationContext(), "server bị lỗi", Toast.LENGTH_SHORT).show();
//                Log.d("TAG", "error api:  " + t);
//            }
//        });

        Button btn_Login = findViewById(R.id.btnLogin);

        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        Button btn_register = findViewById(R.id.btnRegister);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
}
