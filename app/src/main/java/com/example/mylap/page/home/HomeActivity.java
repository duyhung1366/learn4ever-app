package com.example.mylap.page.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mylap.R;
import com.example.mylap.api.ConfigApi;
import com.example.mylap.models.Category;
import com.example.mylap.page.login.LoginActivity;
import com.example.mylap.responsive.GetCategory;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    ListView listCategory;
    ConfigApi configApi = new ConfigApi();
    ArrayAdapter adapterListView;
    ArrayList<String> dataView = new ArrayList<>();
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private List<Category> categoryList;

    @Override
    protected void onCreate(Bundle savedInstantState) {
        super.onCreate(savedInstantState);
        setContentView(R.layout.home_page);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_drawer);
        adapterListView = new ArrayAdapter(HomeActivity.this, android.R.layout.simple_list_item_1);
        configApi = new ConfigApi();
        // Khởi tạo danh sách các mục
        categoryList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // Thêm các mục khác vào itemList tại đây

        adapter = new CustomAdapter(categoryList);
        recyclerView.setAdapter(adapter);

        // api get category
        configApi.getApiService().getCategorys(1).enqueue(new Callback<GetCategory>() {
            @Override
            public void onResponse(Call<GetCategory> call, Response<GetCategory> response) {
                if (response.isSuccessful()) {
                    GetCategory data = response.body();
//                    Log.d("TAG", "Dữ liệu của bạn: " + data.getData().get(0).getName());
                    // Cập nhật dữ liệu vào Adapter và cập nhật ListView
                    for (int i = 0; i < data.getData().size(); i++) {
                        dataView.add(data.getData().get(i).getName());
                        categoryList.add(data.getData().get(i));
                    }
                    adapterListView.addAll(dataView);
                    adapterListView.notifyDataSetChanged();
                    adapter.notifyDataSetChanged();
                } else {
                    Log.d("TAG", "error");
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

        Button btn_Login = findViewById(R.id.btnLogin);

        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

//        drawer
//        Button btnToggleDrawer = findViewById(R.id.btnToggleDrawer);
//        btnToggleDrawer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
//                    drawerLayout.closeDrawer(GravityCompat.START);
//                } else {
//                    drawerLayout.openDrawer(GravityCompat.START);
//                }
//            }
//        });
    }
}
