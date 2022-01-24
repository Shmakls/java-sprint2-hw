package ru.Andrianov.data;

public class Subtask {

    private String title;
    private String description;
    private Status status;
    private int epicTaskID;

    public Subtask(String title, String description, Status status) {
        this.title = title;
        this.description = description;
        this.status = status;
        epicTaskID = 0;
    }

    public int getEpicTaskID() {
        return epicTaskID;
    }

    public void setEpicTaskID(int epicTaskID) {
        this.epicTaskID = epicTaskID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
