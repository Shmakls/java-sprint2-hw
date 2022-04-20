package ru.andrianov.hmdata;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import ru.andrianov.data.HttpTaskRepository;
import ru.andrianov.server.KVServer;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class HttpHistoryRepositoryTest extends HistoryRepositoryTest<HttpHistoryRepository> {

    KVServer kvServer;

    @BeforeEach
    void beforeEach() {

        try {
            kvServer = new KVServer();
            kvServer.start();
        } catch (IOException e) {
            System.out.println("Произошла ошибка при запуске сервера.");
            e.printStackTrace();
        }
        historyRepository = new HttpHistoryRepository("http://localhost:8078");
    }

    @AfterEach
    void afterEach() {
        kvServer.stop();
    }


}