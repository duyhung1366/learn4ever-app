package com.example.mylap.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mylap.models.Course;

import java.util.ArrayList;

public class ViewModelMain extends ViewModel {
    private MutableLiveData<ArrayList<Course>> listCourses = new MutableLiveData<>();

    public LiveData<ArrayList<Course>> getListCourses() {
        return this.listCourses;
    }

    public void setListCourses(ArrayList<Course> _listCourses) {
        Log.d("TAG", "list course : " + _listCourses.get(0).getCourseName());
        this.listCourses.setValue(_listCourses);
    }
}
