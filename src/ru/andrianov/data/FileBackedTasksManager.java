package ru.andrianov.data;

import java.util.ArrayList;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskRepository {


    private String toString(Task task) {

        Type type = findTypeTask(task);

        StringBuilder taskString = new StringBuilder((task.getId() + "," + type.currency + ","
                                                    + task.getTitle() + "," + task.getStatus().currency + ","
                                                    + task.getDescription() + ","));

        if (task instanceof Subtask) {
            taskString.append(((Subtask) task).getEpicTaskId());
        }

        if (task instanceof Epic) {
            List<Integer> subtasksId = ((Epic) task).getSubtasksIds();
            for (int i = 0; i < subtasksId.size(); i++) {
                taskString.append(subtasksId.get(i));
                if (i != subtasksId.size() - 1) {
                    taskString.append(",");
                }
            }
        }
        return taskString.toString();
    }

    private Task fromString(String value) {

        String[] fromString = value.split(",");
        int taskId = Integer.parseInt(fromString[0]);
        Type type = Type.getTypeByString(fromString[1]);
        String title = fromString[2];
        Status status = Status.getStatusByString(fromString[3]);
        String description = fromString[4];

        Task task = new Task(title, description, status);
        task.setId(taskId);

        if (fromString[1].equals("SUBTASK")) {
            int epicId = Integer.parseInt(fromString[5]);
            Subtask subtask = (Subtask) task;
            subtask.setEpicTaskId(epicId);
            return subtask;
        }

        if (fromString[1].equals("EPIC")) {
            List<Integer> subtasksId = new ArrayList<>();
            for (int i = 5; i < fromString.length; i ++) {
                subtasksId.add(Integer.parseInt(fromString[i]));
            }
            Epic epic = (Epic) task;
            epic.setSubtasksIds(subtasksId);
            return epic;
        }

        return task;
    }

    private Type findTypeTask(Task task) {

        Type typesTasks = null;
        if (task instanceof Task) {
            typesTasks = Type.TASK;
        } else if (task instanceof Subtask) {
            typesTasks = Type.SUBTASK;
        } else if (task instanceof Epic) {
            typesTasks = Type.EPIC;
        }
        return typesTasks;
    }

    private void save() {



    }

    @Override
    public Integer createNewTask(Task task) {
        return super.createNewTask(task);
    }
}
