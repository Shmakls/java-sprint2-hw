package ru.Andrianov.data;

import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<Integer> subtasksListID;

    public Epic(String title, String description, Status status) {
        super(title, description, status);
        subtasksListID = new ArrayList<>();
    }

    public ArrayList<Integer> getSubtasksListID() {
        return subtasksListID;
    }

    public void setSubtasksListID(ArrayList<Integer> subtasksListID) {
        this.subtasksListID = subtasksListID;
    }
}
