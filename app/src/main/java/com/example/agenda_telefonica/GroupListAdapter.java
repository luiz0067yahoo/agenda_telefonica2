package com.example.agenda_telefonica;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.ViewHolder> {

    private ArrayList<Group> groups;
    private DBGroupHelper dbHelper;
    private Context context;

    public GroupListAdapter(Context context, ArrayList<Group> groups, DBGroupHelper dbHelper) {
        this.context = context;
        this.groups = groups;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_group, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Group group = groups.get(position);

        holder.groupNameTextView.setText("NOME: "+group.getName());

        holder.editButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, GroupActivity.class);
            intent.putExtra("groupId", group.getId()); // id do grupo
            intent.putExtra("groupName", group.getName()); // nome do grupo
            context.startActivity(intent);
        });

        holder.deleteButton.setOnClickListener(v -> {
            dbHelper.deleteGroup(group.getId());
            showToast(group.getName() + " excluído");
            groups.remove(holder.getAdapterPosition());
            notifyItemRemoved(holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView groupNameTextView;

        Button editButton;
        Button deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            groupNameTextView = itemView.findViewById(R.id.cardGroupName);
            editButton = itemView.findViewById(R.id.editButtonGroup);
            deleteButton = itemView.findViewById(R.id.deleteButtonGroup);
        }
    }

    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}