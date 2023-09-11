package com.example.mylap.page.topicLearn;
import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mylap.R;

import java.util.List;
import java.util.Map;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<TypeGroupHeader> groupHeaders;
    private Map<String, List<String>> childData;

    public CustomExpandableListAdapter(Context context, List<TypeGroupHeader> groupHeaders, Map<String, List<String>> childData) {
        this.context = context;
        this.groupHeaders = groupHeaders;
        this.childData = childData;
    }

    @Override
    public int getGroupCount() {
        return groupHeaders.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        TypeGroupHeader groupHeader = groupHeaders.get(groupPosition);
        return childData.get(groupHeader.getKey()).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupHeaders.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        TypeGroupHeader groupHeader = groupHeaders.get(groupPosition);
        return childData.get(groupHeader.getKey()).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        TypeGroupHeader groupHeader = groupHeaders.get(groupPosition);
        String groupHeaderTitle = groupHeader.getValue();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(com.example.mylap.R.layout.group_header_layout, null);
        }

        TextView groupHeaderTextView = convertView.findViewById(R.id.groupHeaderTextView);
        ImageView groupIcon = convertView.findViewById(R.id.imageView);

        groupHeaderTextView.setText(groupHeaderTitle);
//        groupHeaderTextView.setTextColor(context.getResources().getColor(android.R.color.black)); // Thiết lập màu cho group header

        if (isExpanded) {
            groupIcon.setImageResource(R.drawable.expand_down_double); // Đặt biểu tượng mở khi mục nhóm mở
        } else {
            groupIcon.setImageResource(R.drawable.expand_right_double); // Đặt biểu tượng đóng khi mục nhóm đóng
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String childItem = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_item_layout, null);
        }

        TextView childItemTextView = convertView.findViewById(R.id.childItemTextView);
        childItemTextView.setText(childItem);
        childItemTextView.setTextColor(context.getResources().getColor(android.R.color.black)); // Thiết lập màu cho child item

        return convertView;
    }

}
