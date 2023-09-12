package com.example.mylap.page.topicLearn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.opengl.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

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

public class TopicLearn extends AppCompatActivity implements MediaControllerListener {

    ImageButton btn_open_drawer;
    DrawerLayout drawerLayout;
    List<Topic> topicChilds = new ArrayList<Topic>();
    private VideoView videoView;
    private CustomMediaController mediaController;
    private boolean isRotated = false;

    ConfigApi configApi = new ConfigApi();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_learn);

        videoView = findViewById(R.id.videoView);
        mediaController = new CustomMediaController(this, this);
        FrameLayout mediaControllerContainer = findViewById(R.id.mediaControllerContainer);

        String videoUrl = "http://res.cloudinary.com/dxp3jz1fc/video/upload/v1675790377/d85vrtprfiyttr2pstgm.mp4";
        Uri videoUri = Uri.parse(videoUrl);

        videoView.setVideoURI(videoUri);

        // Liên kết VideoView với MediaController
        mediaController.setAnchorView(mediaControllerContainer);
        videoView.setMediaController(mediaController);

        // Bắt đầu phát video
        videoView.start();
        videoView.getCurrentPosition();

        Intent intent = getIntent();
        String courseId = intent.getStringExtra("courseId");
        int type = intent.getIntExtra("type", 0);
        String courseName = intent.getStringExtra("courseName");

        if(type == 0 || courseId == null) {
            return;
        }

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

                configApi.getApiService().getTopicByCourse(courseId, type, 1).enqueue(new Callback<GetListTopicRes>() {
                    @Override
                    public void onResponse(Call<GetListTopicRes> call, Response<GetListTopicRes> response) {
                        progress.dismiss();
                        if(response.isSuccessful() && response.body().getStatus() == 0) {
                            ArrayList<Topic> topics = response.body().getData();
                            ArrayList<Topic> topicChildDatas = response.body().getTopicChildData();

                            Log.d("TAG", "topicChild data : " + topicChildDatas.size());

                            List<TypeGroupHeader> groupHeaders = new ArrayList<>();
                            Map<String, List<TypeGroupHeader>> childData = new HashMap<>();

                            for(int i = 0; i < topics.size(); i++) {
                                Topic topic = topics.get(i);

                                if(topic.getParentId() == null) {
                                    groupHeaders.add(new TypeGroupHeader(topic.getId(),topic.getName()));

//                                    Log.d("TAG", "topic name : " + topic.getName());
//
//                                    Log.d("TAG", "topic.getTopicChildData().size(): " + topic.getTopicChildData().size() + " - " + topic.getTopicChild() );
                                        List<TypeGroupHeader> itemChild = new ArrayList<TypeGroupHeader>();
                                    if(topic.getTopicChildData().size() > 0) {
                                        for(int j = 0; j < topic.getTopicChildData().size(); j++) {
                                            Topic topicChild = topic.getTopicChildData().get(j);
                                            Log.d("TAG", "topicChild id: " + topicChild.getId() + " topicChild name : " + topicChild.getName());
                                            topicChilds.add(topicChild);
                                            itemChild.add(new TypeGroupHeader(topicChild.getId(), topicChild.getName()));
                                        }
                                    }
                                    childData.put(topic.getId(), itemChild);
                                }
                            }

                            updateMenu(courseName, groupHeaders, childData);

                        } else {
                            Toast.makeText(getApplicationContext(), "server bị lỗi", Toast.LENGTH_SHORT).show();
                        }
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
    }

    private void updateMenu(String courseName, List<TypeGroupHeader> groupHeaders, Map<String, List<TypeGroupHeader>> childData) {

        NavigationView navigationView = findViewById(R.id.nav_view);
        ExpandableListView expandableListView = navigationView.findViewById(R.id.expandableListView);

        View headerView = navigationView.getHeaderView(0); // Lấy phần tiêu đề (header) đầu tiên

        TextView headerTitle = headerView.findViewById(R.id.navHeaderTitle);
        headerTitle.setText("Danh sách khóa học " + courseName);

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

    @Override
    public void onRotateClicked() {
        if (isRotated) {
            // Xoay video về chế độ ban đầu
            // ...

            isRotated = false;
        } else {
            // Xoay video 90 độ theo chiều kim đồng hồ

            isRotated = true;
        }
    }

    @Override
    public boolean isVideoRotated() {
        return isRotated;
    }

    @Override
    public void setVideoRotated(boolean isRotated) {
        this.isRotated = isRotated;
    }
}