package com.example.mylap.page.listCourse;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mylap.R;
import com.example.mylap.ViewModel.ViewModelMain;
import com.example.mylap.api.ConfigApi;
import com.example.mylap.models.Course;
import com.example.mylap.page.home.CustomAdapter;
import com.example.mylap.responsive.GetCategory;
import com.example.mylap.responsive.GetListCourse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListCourse extends AppCompatActivity {
    private ViewModelMain viewModelMain;
    private ConfigApi configApi = new ConfigApi();
    private List<Course> courseList;
    private ListCourseAdapter courseAdapter;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_course);

        configApi = new ConfigApi();
        courseList = new ArrayList<>();



        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        courseAdapter = new ListCourseAdapter( courseList, this);
        recyclerView.setAdapter(courseAdapter);


        Intent intent = getIntent();
        String categoryId = intent.getStringExtra("categoryId");
        TextView textView = findViewById(R.id.textHeader);
        String receivedData = intent.getStringExtra("categoryName");
        String existingText = textView.getText().toString();
        String combinedText = existingText + " " + receivedData;
        textView.setText(combinedText);
        Log.d("TAG", "categoryId: " + categoryId);

        configApi.getApiService().getListCourseByCategoryId(categoryId).enqueue(new Callback<GetListCourse>() {
            @Override
            public void onResponse(Call<GetListCourse> call, Response<GetListCourse> response) {
                if (response.isSuccessful()) {
                    GetListCourse data = response.body();
//                    Log.d("TAG", "Dữ liệu của bạn: " + data.getData().get(0).getName());
                    // Cập nhật dữ liệu vào Adapter và cập nhật ListView
                    for (int i = 0; i < data.getData().size(); i++) {
                        courseList.add(data.getData().get(i));
                    }
                    courseAdapter.notifyDataSetChanged();
                } else {
                    Log.d("TAG", "error");
                    // Xử lý lỗi khi response không thành
                }
            }

            @Override
            public void onFailure(Call<GetListCourse> call, Throwable t) {
                // Xử lý lỗi khi request thất bại
                Log.d("TAG", "error api:  " + t);
            }
        });


    }
}