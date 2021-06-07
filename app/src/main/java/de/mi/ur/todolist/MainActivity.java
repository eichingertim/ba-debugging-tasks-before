package de.mi.ur.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Projekt Features:
 *  - Über ein Eingabefeld können Aufgaben beschrieben werden
 *  - Per Button kann eine solche Aufgabe zu einer Liste hinzugefügt werden
 *  - Optional kann auch eine Deadline per DatePicker festgelegt werden
 *  - Durch Langes klicken, kann eine Aufgabe abgehakt/durchgestrichen werden.
 */
public class MainActivity extends AppCompatActivity {

    private TaskAdapter adapter;

    private ListView lvTasks;
    private TextInputEditText etTaskInput;
    private TextView tvSelectedDeadline;
    private ImageButton btnSelectDeadline;
    private Button btnAddTask;

    private Task currentEditedTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupListView();
        setupListeners();


    }

    private void setupListView() {
        adapter = new TaskAdapter();
        lvTasks.setAdapter(adapter);
    }

    private void setupListeners() {
        lvTasks.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return MainActivity.this.onTaskLongClicked(position);
            }
        });

        btnSelectDeadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.onSelectDeadlineClick();
            }
        });

        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.onAddTaskClick();
            }
        });
    }

    public void setupUI() {
        setContentView(R.layout.activity_main);

        lvTasks = findViewById(R.id.lv_tasks);

        etTaskInput = findViewById(R.id.et_task_input);
        btnSelectDeadline = findViewById(R.id.btn_select_deadline);
        btnAddTask = findViewById(R.id.btn_add);
        tvSelectedDeadline = findViewById(R.id.tv_selected_date);
    }

    private void onSelectDeadlineClick() {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {

            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setDeadLine(calendar);

        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void resetInput() {
        currentEditedTask = null;
        tvSelectedDeadline.setText(R.string.deadline_preview_empty);
        tvSelectedDeadline.setTypeface(Typeface.DEFAULT, Typeface.ITALIC);
        etTaskInput.setText("");
    }

    private void setDeadLine(Calendar calendar) {
        if (currentEditedTask == null) {
            currentEditedTask = new Task();
        }
        currentEditedTask.setDeadLine(calendar.getTime());
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
        tvSelectedDeadline.setText(dateFormat.format(currentEditedTask.getDeadLine()));
        tvSelectedDeadline.setTypeface(Typeface.DEFAULT);
    }

    private void onAddTaskClick() {
        Editable taskInputText = etTaskInput.getText();

        if (taskInputText != null && !taskInputText.toString().trim().isEmpty()) {
            currentEditedTask = currentEditedTask == null ? new Task() : currentEditedTask;
            currentEditedTask.setDescription(taskInputText.toString());
            adapter.addTask(new Task(currentEditedTask));
            adapter.notifyDataSetChanged();
            resetInput();
        } else {
            Toast.makeText(this, R.string.empty_input_warning, Toast.LENGTH_LONG).show();
        }
    }

    private boolean onTaskLongClicked(int position) {
        Task longClickedTask = adapter.getItem(position);

        if (longClickedTask.isCompleted()) return false;

        longClickedTask.setCompleted(true);
        adapter.notifyDataSetChanged();

        return true;
    }


}