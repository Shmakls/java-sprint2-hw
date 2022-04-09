package ru.andrianov.data;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Objects;

public class Task {

    protected String title;
    protected String description;
    protected Status status;
    protected Integer id;
    protected Type type;
    protected ZonedDateTime startTime;
    protected ZonedDateTime endTime;
    protected Duration estimationTime;

    public Task(String title, String description, Status status, ZonedDateTime startTime, Duration estimationTime) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.startTime = startTime;
        this.estimationTime = estimationTime;
        endTime = startTime.plus(estimationTime);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(ZonedDateTime endTime) {
        this.endTime = endTime;
    }

    public Duration getEstimationTime() {
        return estimationTime;
    }

    public void setEstimationTime(Duration estimationTime) {
        this.estimationTime = estimationTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id);
    }

    @Override
    public String toString() {
        return "ID: " + getId()
                + ", название: " + getTitle()
                + ", описание: " + getDescription()
                + ", статус: " + getStatus() + ".";
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
