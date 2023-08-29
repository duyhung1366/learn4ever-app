package com.example.mylap.page.DetailCategory;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.mylap.R;
import com.example.mylap.api.ConfigApi;
import com.example.mylap.models.Category;
import com.example.mylap.responsive.GetCategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailCategory extends AppCompatActivity {

    ConfigApi configApi = new ConfigApi();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_category);

        configApi.getApiService().getCategoryBySlug("lop-1").enqueue(new Callback<GetCategory>() {
            @Override
            public void onResponse(Call<GetCategory> call, Response<GetCategory> response) {
                if (response.isSuccessful()) {
                    GetCategory data = response.body();
//                    Log.d("TAG", "Dữ liệu của bạn: " + data.getData().get(0).getName());

                } else {
                    Log.d("TAG", "error");
                    // Xử lý lỗi khi response không thành
                }
            }

            @Override
            public void onFailure(Call<GetCategory> call, Throwable t) {
                // Xử lý lỗi khi request thất bại
                Log.d("TAG", "error api:  " + t);
            }
        });
    }
}