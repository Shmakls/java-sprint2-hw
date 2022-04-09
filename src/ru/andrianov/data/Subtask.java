package ru.andrianov.data;

import java.time.Duration;
import java.time.ZonedDateTime;

public class Subtask extends Task {

    private int epicTaskId;

    public Subtask(String title, String description, Status status, ZonedDateTime startTime, Duration estimationTime, int epicTaskId) {
        super(title, description, status, startTime, estimationTime);
        this.epicTaskId = epicTaskId;
        endTime = startTime.plus(estimationTime);
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
