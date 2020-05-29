package com.app.assesment.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.app.assesment.model.ChildItems;

import com.app.assesment.model.GroupHeader;
import com.app.assesment.R;

import java.util.ArrayList;
import java.util.List;

public class CustomUserAdapter extends BaseExpandableListAdapter {
    private static String TAG = "CustomUserAdapter";
    private Context _context;
    private List<GroupHeader> _listDataHeader;

    public CustomUserAdapter(Context context, List<GroupHeader> listDataHeader) {
        Log.d(TAG, "CustomUserAdapter: "+listDataHeader);
        this._context = context;
        this._listDataHeader = listDataHeader;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<ChildItems> chList = _listDataHeader.get(groupPosition).getItems();
        return chList.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        ChildItems child = (ChildItems) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            convertView = inflater.inflate(R.layout.activity_users, null);
        }

        TextView txtListChild = convertView.findViewById(R.id.friday_time);
        TextView lblListItem1 = convertView.findViewById(R.id.friday_event);
        lblListItem1.setText(child.getEvent());
        txtListChild.setText(child.getTiming());

        return convertView;

    }

    @Override
    public int getChildrenCount(int groupPosition) {
        Log.d(TAG, "getposition "+groupPosition);

        ArrayList<ChildItems> chList = _listDataHeader.get(groupPosition).getItems();
        Log.d(TAG, "getChildrenCount"+chList);
        return chList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return _listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return _listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,View convertView, ViewGroup parent) {

        GroupHeader group = (GroupHeader) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            convertView = inflater.inflate(R.layout.list_group, null);
        }
        if (isExpanded){

        }

        TextView tv = convertView.findViewById(R.id.lblListHeader);
        tv.setTypeface(null, Typeface.BOLD);
        tv.setText(group.getTitle());
        return convertView;
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);

    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
