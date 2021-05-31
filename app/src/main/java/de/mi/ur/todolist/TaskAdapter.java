package de.mi.ur.todolist;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends BaseAdapter {

    private final List<Task> taskItems = new ArrayList<>();

    public void addTask(@NonNull Task task) {
        taskItems.add(task);
    }

    @Override
    public int getCount() {
        return taskItems.size();
    }

    @Override
    public Task getItem(int position) {
        return taskItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            view = inflater.inflate(R.layout.layout_task, parent, false);
        }

        Task taskItem = getItem(position);

        fillData(taskItem, view);

        return view;
    }

    private void fillData(Task taskItem, View view) {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);

        TextView tvDescription = view.findViewById(R.id.tv_description);
        TextView tvDeadLine = view.findViewById(R.id.tv_deadline);
        LinearLayout llContainerDeadLine = view.findViewById(R.id.ll_container_deadline);

        tvDescription.setText(taskItem.getDescription());
        if (taskItem.getDeadLine() != null) {
            llContainerDeadLine.setVisibility(View.VISIBLE);
            tvDeadLine.setText(dateFormat.format(taskItem.getDeadLine()));
        } else {
            llContainerDeadLine.setVisibility(View.GONE);
        }

        if (taskItem.isCompleted()) {
            tvDescription.setPaintFlags(tvDeadLine.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            view.setEnabled(false);
        } else view.setEnabled(true);
    }
}
