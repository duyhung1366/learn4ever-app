package com.example.mylap.page.topicLearn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

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

import com.example.mylap.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TopicLearn extends AppCompatActivity {

    ImageButton btn_open_drawer;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_learn);

        //map element
        btn_open_drawer = findViewById(R.id.btn_open_drawer);
        drawerLayout = findViewById(R.id.drawer_layout);

        btn_open_drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

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