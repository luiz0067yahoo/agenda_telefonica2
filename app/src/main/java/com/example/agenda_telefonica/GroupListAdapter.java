package com.example.agenda_telefonica;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GroupListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Group> groups;
    private DBGroupHelper dbHelper;

    public GroupListAdapter(Context context, ArrayList<Group> groups, DBGroupHelper dbHelper) {
        this.context = context;
        this.groups = groups;
        this.dbHelper = dbHelper;
    }

    @Override
    public int getCount() {
        return groups.size();
    }

    @Override
    public Object getItem(int position) {
        return groups.get(position);
    }

    @Override
    public long getItemId(int position) {
        return groups.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_item_group, parent, false);

        TextView cardGroupName = view.findViewById(R.id.cardGroupName);
        Button editButton = view.findViewById(R.id.editButtonGroup);
        Button deleteButton = view.findViewById(R.id.deleteButtonGroup);

        Group group = groups.get(position);
        cardGroupName.setText(group.getName());

        editButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, GroupActivity.class);
            intent.putExtra("groupId", group.getId());
            intent.putExtra("groupName", group.getName());
            context.startActivity(intent);
        });

        deleteButton.setOnClickListener(v -> {
            dbHelper.deleteGroup(group.getId());
            showToast(group.getName() + " excluído");
            groups.remove(position);
            notifyDataSetChanged();
        });

        return view;
    }

    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
