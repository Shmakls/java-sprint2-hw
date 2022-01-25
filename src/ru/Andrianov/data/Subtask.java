package ru.Andrianov.data;

public class Subtask extends Task {

    private int epicTaskID;

    public Subtask(String title, String description, Status status, int epicTaskID) {
        super(title, description, status);
        this.epicTaskID = epicTaskID;
    }

    public int getEpicTaskID() {
        return epicTaskID;
    }

    public void setEpicTaskID(int epicTaskID) {
        this.epicTaskID = epicTaskID;
    }

}
