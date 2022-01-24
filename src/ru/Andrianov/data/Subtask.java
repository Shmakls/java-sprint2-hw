package ru.Andrianov.data;

public class Subtask extends Task {

    private int epicTaskID;

    public Subtask(String title, String description, Status status) {
        super(title, description, status);
        epicTaskID = 0;
    }

    public int getEpicTaskID() {
        return epicTaskID;
    }

    public void setEpicTaskID(int epicTaskID) {
        this.epicTaskID = epicTaskID;
    }

}
