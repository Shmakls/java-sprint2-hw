package ru.andrianov.data;

public class Subtask extends Task {

    private int epicTaskId;

    public Subtask(String title, String description, Status status, int epicTaskId) {
        super(title, description, status);
        this.epicTaskId = epicTaskId;
    }

    public int getEpicTaskId() {
        return epicTaskId;
    }

    public void setEpicTaskId(int epicTaskId) {
        this.epicTaskId = epicTaskId;
    }

    @Override
    public String toString() {
        return "ID: " + getId()
                + ", название: " + getTitle()
                + ", описание: " + getDescription()
                + ", статус: " + getStatus() + "."
                + " Относится к epic задаче с ID " + (getEpicTaskId()) + ".";
    }
}
