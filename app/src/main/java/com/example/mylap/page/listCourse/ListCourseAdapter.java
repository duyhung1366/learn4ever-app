package com.example.mylap.page.listCourse;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mylap.R;
import com.example.mylap.ViewModel.ViewModelMain;
import com.example.mylap.api.ConfigApi;
import com.example.mylap.models.Category;
import com.example.mylap.models.Course;
import com.example.mylap.page.listCourse.CustomViewHolder;
import com.example.mylap.page.listCourse.ListCourse;
import com.example.mylap.responsive.GetListCourse;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ListCourseAdapter extends RecyclerView.Adapter<CustomViewHolder> {
    private List<Course> courseList;
    private ViewModelMain viewModelMain;
    private Context activity;

    public ListCourseAdapter(List<Course> courseList, FragmentActivity activity)  {
        this.courseList = courseList ;
        this.viewModelMain = new ViewModelProvider(activity).get(ViewModelMain.class);
        this.activity = activity;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Course item = courseList.get(position);

        Log.d("TAG", "image course: " + item.getAvatar());
        Picasso.get().load(item.getAvatar()).into(holder.imageView, new Callback() {
            @Override
            public void onSuccess() {
                // Ảnh tải thành công
                Log.d("TAG", "onSuccess: ");
            }

            @Override
            public void onError(Exception e) {
                // Xử lý lỗi ở đây
                Log.d("TAG", "onError: " + e);
            }
        });
        holder.textName.setText(item.getCourseName());
        holder.textDescription.setText(formatText(item.getShortDes()));
//        holder.buttonDoNow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Xử lý sự kiện khi nút "Làm ngay" được nhấn
////                Log.d("TAG", "id category: " + item.getId());
//                Intent intent = new Intent(activity, ListCourse.class);
//                intent.putExtra("categoryId", item.getId());
//                activity.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    private String formatText(String text) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            CharSequence formattedText = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY);
            return formattedText.toString();
        } else {
            CharSequence formattedText = Html.fromHtml(text);
            return formattedText.toString();
        }
    }
}

