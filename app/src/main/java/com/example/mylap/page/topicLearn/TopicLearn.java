package com.example.mylap.page.topicLearn;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mylap.R;
import com.example.mylap.api.ConfigApi;
import com.example.mylap.models.question.Question;
import com.example.mylap.models.topic.Topic;
import com.example.mylap.page.topicLearn.adapterQuestion.QuestionAdapter;
import com.example.mylap.responsive.GetListTopicRes;
import com.example.mylap.utils.Format;
import com.example.mylap.utils.ProgressDialogUtils;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopicLearn extends AppCompatActivity implements MediaControllerListener {

    ImageView btn_open_drawer;
    DrawerLayout drawerLayout;
    private LinearLayout layout_video;
    private LinearLayout layout_document;
    private LinearLayout none_topic;
    private VideoView videoView;
    private CustomMediaController mediaController;
    private boolean isRotated = false;
    private SensorManager sensorManager;
    private OrientationEventListener orientationEventListener;
    private FrameLayout fullLayout;
    private RelativeLayout videoLayout;

    private List<Topic> listTopics = new ArrayList<>();
    private List<Topic> listTopicChilds = new ArrayList<>();
    private TopicViewModel topicModel;
    ConfigApi configApi = new ConfigApi();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_learn);

        videoView = findViewById(R.id.videoView);
        layout_video = findViewById(R.id.layout_video);
        layout_document = findViewById(R.id.layout_document);
        none_topic = findViewById(R.id.none_topic);
        videoLayout = findViewById(R.id.videoLayout);
        fullLayout = findViewById(R.id.fullLayout);
        mediaController = new CustomMediaController(this, this);
        FrameLayout mediaControllerContainer = findViewById(R.id.mediaControllerContainer);

        // Liên kết VideoView với MediaController
        mediaController.setAnchorView(mediaControllerContainer);
        videoView.setMediaController(mediaController);

        // view model
        topicModel = new ViewModelProvider(this).get(TopicViewModel.class);

        topicModel.getCurrentTopic().observe(this, new Observer<Topic>() {
            @Override
            public void onChanged(Topic topic) {
                if (topic != null) {
                    none_topic.setVisibility(View.GONE);
                    Log.d("TAG", " current topic : " + topic.getName());
                    int topicType = topic.getTopicType();
                    switch (topicType) {
                        case 4:
                            layout_document.setVisibility(View.GONE);
                            //video
                            TextView topic_name = findViewById(R.id.topic_name_video);
                            TextView topic_update_date = findViewById(R.id.topic_update_date_video);
                            TextView topic_des = findViewById(R.id.topic_des_video);

                            topic_name.setText(topic.getName());
                            int[] date = Format.convertTimeMiliseconds(topic.getUpdateDate());
                            topic_update_date.setText("Ngày cập nhật : " + "Ngày " + date[0] + " Tháng " + date[1] + " Năm " + date[2]);
                            topic_des.setText(Format.formatText(topic.getDes(), false));

                            layout_video.setVisibility(View.VISIBLE);
                            String videoUrl = topic.getVideo();
                            Uri videoUri = Uri.parse(videoUrl);

                            videoView.setVideoURI(videoUri);

                            // Bắt đầu phát video
                            videoView.start();

                            // videoView.getCurrentPosition();
                            break;

                        case 2:
                            // bai tap
                            videoView.pause();
                            layout_video.setVisibility(View.GONE);
                            layout_document.setVisibility(View.GONE);

                            // get question
//                            configApi.getApiService()

                            // get element
                            RecyclerView recyclerView = findViewById(R.id.recyclerView_listQuestion);
                            QuestionAdapter adapter;


                            break;

                        case 5:
                            // document
                            videoView.pause();
                            layout_document.setVisibility(View.VISIBLE);
                            layout_video.setVisibility(View.GONE);

                            TextView topic_name_document = findViewById(R.id.topic_name_document);
                            TextView topic_update_date_document = findViewById(R.id.topic_update_date_document);

                            topic_name_document.setText(topic.getName());
                            int[] date_document = Format.convertTimeMiliseconds(topic.getUpdateDate());
                            topic_update_date_document.setText("Ngày cập nhật : " + "Ngày " + date_document[0] + " Tháng " + date_document[1] + " Năm " + date_document[2]);

//                            topic_des_document.setText(Format.formatText(topic.getDes()));

                            WebView webView = findViewById(R.id.webview_document);

                            WebSettings webSettings = webView.getSettings();
                            webSettings.setJavaScriptEnabled(true); // Bật JavaScript nếu cần thiết
                            webView.loadDataWithBaseURL(null, Format.formatText(topic.getDes(), true), "text/html", "UTF-8", null);

                            break;

                        default:
                            layout_video.setVisibility(View.GONE);
                            videoView.pause();
                            layout_document.setVisibility(View.GONE);
                            none_topic.setVisibility(View.VISIBLE);
                            break;
                    }
                    if (topicType == 4) {
                        // video
                        layout_video.setVisibility(View.VISIBLE);
                        String videoUrl = topic.getVideo();
                        Uri videoUri = Uri.parse(videoUrl);

                        videoView.setVideoURI(videoUri);

                        // Bắt đầu phát video
                        videoView.start();

                        // videoView.getCurrentPosition();
                    }
                }
            }
        });

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        // Khởi tạo OrientationEventListener
        orientationEventListener = new OrientationEventListener(this, SensorManager.SENSOR_DELAY_NORMAL) {
            @Override
            public void onOrientationChanged(int orientation) {
                if (orientation == ORIENTATION_UNKNOWN) return;

                int screenOrientation = getWindowManager().getDefaultDisplay().getRotation();

                if (screenOrientation == Surface.ROTATION_90 || screenOrientation == Surface.ROTATION_270) {
                    // Thiết bị xoay ngang (landscape mode)
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
                } else {
                    // Thiết bị ở chế độ dọc (portrait mode)
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
                }
            }
        };

        // get itent
        Intent intent = getIntent();
        String courseId = intent.getStringExtra("courseId");
        int type = intent.getIntExtra("type", 0);
        String courseName = intent.getStringExtra("courseName");

        if (type == 0 || courseId == null) {
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
                        if (response.isSuccessful() && response.body().getStatus() == 0) {
                            ArrayList<Topic> topics = response.body().getData();

                            List<TypeGroupHeader> groupHeaders = new ArrayList<>();
                            Map<String, List<TypeGroupHeader>> childData = new HashMap<>();

                            for (int i = 0; i < topics.size(); i++) {
                                Topic topic = topics.get(i);
                                listTopics.add(topic);

                                if (topic.getParentId() == null) {
                                    groupHeaders.add(new TypeGroupHeader(topic.get_id(), topic.getName()));
                                } else {
                                    listTopicChilds.add(topic);
                                    List<TypeGroupHeader> childDatasInTopic = new ArrayList<>();

                                    if (childData.containsKey(topic.getParentId())) {
                                        childDatasInTopic = childData.get(topic.getParentId());
                                    }

                                    childDatasInTopic.add(new TypeGroupHeader(topic.get_id(), topic.getName()));
                                    childData.put(topic.getParentId(), childDatasInTopic);
                                }
                            }

                            updateMenu(courseName, groupHeaders, childData, listTopics);
                            // set current topic
                            if (listTopicChilds.size() > 0) {
                                topicModel.setCurrentTopic(listTopicChilds.get(0));
                            }
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

    private void updateMenu(String courseName, List<TypeGroupHeader> groupHeaders, Map<String, List<TypeGroupHeader>> childData, List<Topic> listTopics) {

        NavigationView navigationView = findViewById(R.id.nav_view);
        ExpandableListView expandableListView = navigationView.findViewById(R.id.expandableListView);

        View headerView = navigationView.getHeaderView(0); // Lấy phần tiêu đề (header) đầu tiên

        TextView headerTitle = headerView.findViewById(R.id.navHeaderTitle);
        headerTitle.setText("Danh sách khóa học " + courseName);

        CustomExpandableListAdapter adapter = new CustomExpandableListAdapter(this, groupHeaders, childData, listTopics);

        expandableListView.setAdapter(adapter);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(
                    ExpandableListView parent, View v,
                    int groupPosition, int childPosition, long id) {
                // Xử lý sự kiện ở đây
                Topic topicClick = null;
                TypeGroupHeader childItem = (TypeGroupHeader) adapter.getChild(groupPosition, childPosition);
                String idChildItem = childItem.getKey();
                if (topicModel.get_currentTopic().get_id() == idChildItem) {
                    return true;
                }
                for (Topic topic : listTopicChilds) {
                    if (topic.get_id() == idChildItem) {
                        topicClick = topic;
                        break;
                    }
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                topicModel.setCurrentTopic(topicClick);
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

    // Xử lý khi người dùng ấn nút Full Màn hình
    public void onRotateClicked() {
        if (isRotated) {
            // Thoát chế độ Full Màn hình
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // Chuyển về chế độ dọc
            isRotated = false;
        } else {
            // Chuyển sang chế độ Full Màn hình
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); // Chuyển về chế độ ngang
            // Xử lý full màn
            ViewGroup.LayoutParams layoutParams = videoLayout.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            videoLayout.setLayoutParams(layoutParams);

            btn_open_drawer.setVisibility(View.GONE); //ẩn nút ngăn kéo

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