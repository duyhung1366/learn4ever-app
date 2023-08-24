package com.example.mylap.page.home;

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
import com.example.mylap.page.listCourse.ListCourse;
import com.example.mylap.responsive.GetListCourse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {
    private List<Category> itemList;
    private ConfigApi configApi = new ConfigApi();
    private ViewModelMain viewModelMain;
    private Context activity;

    public CustomAdapter(List<Category> itemList, FragmentActivity activity)  {
        this.itemList = itemList;
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
        Category item = itemList.get(position);

        Picasso.get().load(item.getAvatar()).into(holder.imageView);
        holder.textName.setText(item.getName());
        holder.textDescription.setText(formatText(item.getDes()));
        holder.buttonDoNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện khi nút "Làm ngay" được nhấn
//                Log.d("TAG", "id category: " + item.getId());
                configApi.getApiService().getListCourseByCategoryId(item.getId()).enqueue(new Callback<GetListCourse>() {
                    @Override
                    public void onResponse(Call<GetListCourse> call, Response<GetListCourse> response) {
                        if (response.isSuccessful()) {
                            GetListCourse data = response.body();
//                            Log.d("TAG", "stutus : " + data.getStatus() + " data : " + data.getData().get(0).getCourseName());
                            viewModelMain.setListCourses(data.getData());

                            Intent intent = new Intent(activity, ListCourse.class);
                            activity.startActivity(intent);
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
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
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

