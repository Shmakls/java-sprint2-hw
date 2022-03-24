package ru.andrianov.data;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {

    private List<Integer> subtasksIds;

    public Epic(String title, String description, Status status) {
        super(title, description, status);
        subtasksIds = new ArrayList<>();
    }

    public List<Integer> getSubtasksIds() {
        return subtasksIds;
    }

    public void setSubtasksIds(List<Integer> subtasksIds) {
        this.subtasksIds = subtasksIds;
    }

    @Override
    public String toString() {
        return "ID: " + getId()
                + ", название: " + getTitle()
                + ", описание: " + getDescription()
                + ", статус: " + getStatus() + "."
                + "Зависимые подзадачи с ID: " + subtasksIds.toString() + ".";
    }
}
