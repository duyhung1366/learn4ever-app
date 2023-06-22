package com.example.mylap.page.home;

import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mylap.R;
import com.example.mylap.models.Category;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {
    private List<Category> itemList;

    public CustomAdapter(List<Category> itemList) {
        this.itemList = itemList;
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

