package ru.Andrianov.data;

import java.util.ArrayList;

public class Epic {

    private String title;
    private String description;
    private Status status;
    private ArrayList<Integer> subtasksListID;

    public Epic(String title, String description, Status status) {
        this.title = title;
        this.description = description;
        this.status = status;
        subtasksListID = new ArrayList<>();
    }

    public ArrayList<Integer> getSubtasksListID() {
        return subtasksListID;
    }

    public void setSubtasksListID(ArrayList<Integer> subtasksListID) {
        this.subtasksListID = subtasksListID;
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
