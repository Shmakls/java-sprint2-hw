package ru.andrianov.operations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.andrianov.data.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CalculationOfTemporaryValuesForEpicTest {

    TaskRepository taskRepository;

    ZoneId zoneId;
    LocalDateTime localDateTimeForSubtask1;
    LocalDateTime localDateTimeForSubtask2;
    Duration duration;
    ZonedDateTime startTimeSubtask1;
    ZonedDateTime startTimeSubtask2;

    Epic epic1;
    Subtask subtask1;
    Subtask subtask2;

    @BeforeEach
    void initializeDateForTest() {
        taskRepository = new InMemoryTaskRepository();
        zoneId = ZoneId.of("Europe/Moscow");
        localDateTimeForSubtask1 = LocalDateTime.of(2022, 1, 15, 12, 0);
        localDateTimeForSubtask2 = LocalDateTime.of(2022, 1, 16, 12, 0);

        duration = Duration.ofMinutes(30);

        startTimeSubtask1 = ZonedDateTime.of(localDateTimeForSubtask1, zoneId);
        startTimeSubtask2 = ZonedDateTime.of(localDateTimeForSubtask2, zoneId);

        epic1 = new Epic("TestEpic1", "DescriptionTestEpic1", Status.NEW);
        subtask1 = new Subtask("TestSubtask1", "DescriptionTestSubtask1", Status.NEW, startTimeSubtask1, duration , 1);
        subtask2 = new Subtask("TestSubtask2", "DescriptionTestSubtask2", Status.IN_PROGRESS, startTimeSubtask2, duration, 1);
    }

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