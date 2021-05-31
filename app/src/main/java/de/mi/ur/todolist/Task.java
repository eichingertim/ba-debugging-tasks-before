package de.mi.ur.todolist;

import java.util.Date;

public class Task {

    private String description;
    private Date deadLine;

    private boolean completed;

    public Task() {

    }

    public Task(Task task) {
        setDescription(task.getDescription());
        setDeadLine(task.getDeadLine());
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
