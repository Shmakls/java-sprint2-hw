package ru.andrianov.server;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {

    private final String serverUrl;
    private String key;
    private HttpClient client;

    public KVTaskClient(String serverUrl) {
        this.serverUrl = serverUrl;
        client = HttpClient.newHttpClient();
        key = register();
    }

    public void put(String typeKey, String json) {
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        String compositeKey = typeKey + "_" + key;

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + "/save/" + compositeKey))
                .POST(body)
                .build();

        try {
            client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public String load(String typeKey) {
        String compositeKey = typeKey + "_" + key;
        String response = "";
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(serverUrl + "/load/" + compositeKey + "?API_KEY=" + compositeKey))
                .build();

        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();

        try {
            HttpResponse<String> httpResponse = client.send(httpRequest, handler);
            response = httpResponse.body();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    private String register() {
        String response = "";
        HttpRequest httpRequest = HttpRequest.newBuilder()
                                .GET()
                                .uri(URI.create(serverUrl + "/register"))
                                .build();

        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();

        try {
            HttpResponse<String> httpResponse = client.send(httpRequest, handler);
            response = httpResponse.body();
            System.out.println("Ключ " + response + " успешно получен!");

        } catch (IOException e) {
            System.out.println("Ошибка IOException");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("Ошибка InterruptedException");
            e.printStackTrace();
        }
        return response;
    }

}
