package ru.andrianov.data;

import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<Integer> subtasksIds;

    public Epic(String title, String description, Status status) {
        super(title, description, status);
        subtasksIds = new ArrayList<>();
    }

    public ArrayList<Integer> getSubtasksIds() {
        return subtasksIds;
    }

    public void setSubtasksIds(ArrayList<Integer> subtasksIds) {
        this.subtasksIds = subtasksIds;
    }
}
