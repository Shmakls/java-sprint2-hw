package ru.andrianov.hmdata;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import ru.andrianov.server.KVServer;

import java.io.IOException;



class HttpHistoryRepositoryTest extends HistoryRepositoryTest<HttpHistoryRepository> {

    KVServer kvServer;

    @BeforeEach
    void beforeEach() throws IOException {

        kvServer = new KVServer();
        kvServer.start();

        historyRepository = new HttpHistoryRepository("http://localhost:8078");
    }

    @AfterEach
    void afterEach() {
        kvServer.stop();
    }


}