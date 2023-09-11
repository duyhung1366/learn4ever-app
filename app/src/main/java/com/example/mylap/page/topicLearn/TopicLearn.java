package com.example.mylap.page.topicLearn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mylap.R;
import com.example.mylap.api.ConfigApi;
import com.example.mylap.models.Course;
import com.example.mylap.models.topic.Topic;
import com.example.mylap.responsive.CourseDetailRes;
import com.example.mylap.responsive.GetListTopicRes;
import com.example.mylap.utils.ProgressDialogUtils;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopicLearn extends AppCompatActivity {

    ImageButton btn_open_drawer;
    DrawerLayout drawerLayout;

    ConfigApi configApi = new ConfigApi();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_learn);

        Intent intent = getIntent();
        String courseId = intent.getStringExtra("courseId");
        int type = intent.getIntExtra("type", 0);

        //map element
        btn_open_drawer = findViewById(R.id.btn_open_drawer);
        drawerLayout = findViewById(R.id.drawer_layout);

        btn_open_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        ProgressDialog progress = ProgressDialogUtils.createProgressDialog(this);
        progress.show();

        new Thread(new Runnable() {
            @Override
            public void run() {

                configApi.getApiService().getTopicByCourse("63ae88a2fe74a345583ff56e", 1, 1).enqueue(new Callback<GetListTopicRes>() {
                    @Override
                    public void onResponse(Call<GetListTopicRes> call, Response<GetListTopicRes> response) {
                        progress.dismiss();
                        Log.d("TAG", "status : " + response.body().getStatus() + "data : " + response.body().getData());
//                        if(response.isSuccessful() && response.body().getStatus() == 0) {
//                            ArrayList<Topic> topics = response.body().getData();
//                            Log.d("TAG", "successfull: " + topics.get(0).getName());
//                        } else {
//                            Toast.makeText(getApplicationContext(), "server bị lỗi", Toast.LENGTH_SHORT).show();
//                        }
                    }

                    @Override
                    public void onFailure(Call<GetListTopicRes> call, Throwable t) {
                        progress.dismiss();
                        Toast.makeText(getApplicationContext(), "server bị lỗi", Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "error api:  " + t);
                    }
                });
            }
        }).start();

        updateMenu("Toán 1");
    }

    private void updateMenu(String courseName) {

        NavigationView navigationView = findViewById(R.id.nav_view);
        ExpandableListView expandableListView = navigationView.findViewById(R.id.expandableListView);

        View headerView = navigationView.getHeaderView(0); // Lấy phần tiêu đề (header) đầu tiên

        TextView headerTitle = headerView.findViewById(R.id.navHeaderTitle);
        headerTitle.setText("Danh sách khóa học " + courseName);

        List<TypeGroupHeader> groupHeaders = new ArrayList<>();
        Map<String, List<String>> childData = new HashMap<>();

        groupHeaders.add(new TypeGroupHeader("idnhom1", "Nhóm 1 : Làm quen các số 1, 2, 3, 4, 5 Làm quen các số 1, 2, 3, 4, 5 Làm quen các số 1, 2, 3, 4, 5"));
        groupHeaders.add(new TypeGroupHeader("idnhom2", "Nhóm 2"));

        List<String> group1Items = new ArrayList<>();
        group1Items.add("Mục 1.1");
        group1Items.add("Mục 1.2");
        childData.put("idnhom1", group1Items);

        List<String> group2Items = new ArrayList<>();
        group2Items.add("Mục 2.1");
        group2Items.add("Mục 2.2");
        childData.put("idnhom2", group2Items);

        CustomExpandableListAdapter adapter = new CustomExpandableListAdapter(this, groupHeaders, childData);

        expandableListView.setAdapter(adapter);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(
                    ExpandableListView parent, View v,
                    int groupPosition, int childPosition, long id) {
                // Xử lý sự kiện ở đây
                Log.d("TAG", "groupPosition: " + groupPosition + "childPosition : " + childPosition + "id : " + id);

                return true;
            }
        });

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                boolean isExpanded = parent.isGroupExpanded(groupPosition);

                Log.d("TAG", "isExpanded : " + isExpanded);

                if (isExpanded) {

                } else {
                    // Mục nhóm đang đóng, bạn có thể thực hiện các hành động khi mở mục nhóm ở đây
                }

                // Trả về true để ngăn việc ExpandableListView tự động mở và đóng các nhóm
                return false;
            }
        });


    }

}