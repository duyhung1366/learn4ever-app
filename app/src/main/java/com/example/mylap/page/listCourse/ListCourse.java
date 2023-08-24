package com.example.mylap.page.listCourse;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mylap.R;
import com.example.mylap.ViewModel.ViewModelMain;
import com.example.mylap.models.Course;

import java.util.ArrayList;

public class ListCourse extends AppCompatActivity {
    private ViewModelMain viewModelMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_course);
        this.viewModelMain = new ViewModelProvider(this).get(ViewModelMain.class);
//        ArrayList<Course> data = this.viewModelMain.getListCourses();
        this.viewModelMain.getListCourses().observe(this, new Observer<ArrayList<Course>>() {
            @Override
            public void onChanged(ArrayList<Course> data) {
                Log.d("TAG", "size data : " + data.size());
                if (data.size() > 0) {
                    Log.d("TAG", " data : " + data.get(0).getCourseName());
                    // Cập nhật UI dựa trên dữ liệu ở đây
                }
            }
        });

    }
}