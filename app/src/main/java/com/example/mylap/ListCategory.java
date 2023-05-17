package com.example.mylap;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mylap.api.ConfigApi;
import com.example.mylap.models.Category;
import com.example.mylap.payload.Payload;
import com.example.mylap.responsive.GetCategory;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListCategory extends AppCompatActivity {
    ListView listCategory;
    ConfigApi configApi = new ConfigApi();
    Payload payload;
    ArrayAdapter adapterListView;
    ArrayList<String> dataView = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstantState) {
        super.onCreate(savedInstantState);
        setContentView(R.layout.category_list);
        adapterListView = new ArrayAdapter(ListCategory.this, android.R.layout.simple_list_item_1);
        configApi = new ConfigApi();
        payload = new Payload();
        payload.setStatus(1);
        configApi.getApiService().getCategorys(1).enqueue(new Callback<GetCategory>() {
            @Override
            public void onResponse(Call<GetCategory> call, Response<GetCategory> response) {
                if (response.isSuccessful()) {
                    GetCategory data = response.body();
                    Log.d("TAG", "Dữ liệu của bạn: " + data.getData().get(0).getName());
                    // Cập nhật dữ liệu vào Adapter và cập nhật ListView
                    for (int i = 0; i < data.getData().size(); i++) {
                        dataView.add(data.getData().get(i).getName());
                    }
                    adapterListView.addAll(dataView);
                    adapterListView.notifyDataSetChanged();
                } else {
                    // Xử lý lỗi khi response không thành
                    List<Category> data = new ArrayList<>();
                    adapterListView.addAll(data);
                    adapterListView.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<GetCategory> call, Throwable t) {
                // Xử lý lỗi khi request thất bại
                Log.d("TAG", "error api:  " + t);
            }
        });
        listCategory = findViewById(R.id.listCategory);
        listCategory.setAdapter(adapterListView);
    }
}
