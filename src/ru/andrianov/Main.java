package ru.andrianov;


import ru.andrianov.data.*;
import ru.andrianov.server.KVServer;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        new KVServer().start();


    }
}