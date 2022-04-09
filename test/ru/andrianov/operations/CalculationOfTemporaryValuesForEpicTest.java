package ru.andrianov.operations;

import org.junit.jupiter.api.Test;
import ru.andrianov.data.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CalculationOfTemporaryValuesForEpicTest {

    TaskRepository taskRepository = new InMemoryTaskRepository();
    ZoneId zoneId = ZoneId.of("Europe/Moscow");
    LocalDateTime localDateTimeForSubtask1 = LocalDateTime.of(2022, 1, 15, 12, 0);
    LocalDateTime localDateTimeForSubtask2 = LocalDateTime.of(2022, 1, 16, 12, 0);

    Duration duration = Duration.ofMinutes(30);

    ZonedDateTime startTimeSubtask1 = ZonedDateTime.of(localDateTimeForSubtask1, zoneId);
    ZonedDateTime startTimeSubtask2 = ZonedDateTime.of(localDateTimeForSubtask2, zoneId);

    Epic epic1 = new Epic("TestEpic1", "DescriptionTestEpic1", Status.NEW);
    Subtask subtask1 = new Subtask("TestSubtask1", "DescriptionTestSubtask1", Status.NEW, startTimeSubtask1, duration , 1);
    Subtask subtask2 = new Subtask("TestSubtask2", "DescriptionTestSubtask2", Status.IN_PROGRESS, startTimeSubtask2, duration, 1);


    @Test
    void shouldBeCalculateStartTimeAndEndTimeAndDurationEpic() {

        epic1.setSubtasksIds(List.of(2, 3));

        taskRepository.createNewTask(epic1);
        taskRepository.createNewTask(subtask1);
        taskRepository.createNewTask(subtask2);

        CalculationOfTimeValuesForEpic.checkAndChangeEpicTimeValues(taskRepository, 1);

        Epic epicFromRepository = (Epic) taskRepository.getTaskById(1);

        ZonedDateTime startTimeFromEpic = epicFromRepository.getStartTime();
        ZonedDateTime endTimeFromEpic = epicFromRepository.getEndTime();
        Duration estimationTimeFromEpic = epicFromRepository.getEstimationTime();

        assertEquals(startTimeSubtask1, startTimeFromEpic);
        assertEquals(startTimeSubtask2.plus(duration), endTimeFromEpic);
        assertEquals(3600, estimationTimeFromEpic.getSeconds());

    }


}