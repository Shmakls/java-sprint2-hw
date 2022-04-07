package ru.andrianov.data;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task {

    private String title;
    private String description;
    private Status status;
    private Integer id;
    private Type type;
    private LocalDateTime startTime;
    private Duration estimationTime;

    public Task(String title, String description, Status status) {
        this.title = title;
        this.description = description;
        this.status = status;
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
