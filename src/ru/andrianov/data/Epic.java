package ru.andrianov.data;

import java.util.ArrayList;

public class Epic extends Task {

    private ArrayList<Integer> subtasksIds;                                 //И здесь ругается на тип возвращаемого
                                                                            //значения. А если меняю и в методе ниже
    public Epic(String title, String description, Status status) {
        super(title, description, status);
        subtasksIds = new ArrayList<>();
    }

    public ArrayList<Integer> getSubtasksIds() {                            //вот тут, то он по всему тексту, где
        return subtasksIds;                                                 //вызывается ArrayList ругается. Как быть
    }                                                                       //правильно в таком случае?

    public void setSubtasksIds(ArrayList<Integer> subtasksIds) {
        this.subtasksIds = subtasksIds;
    }
}
