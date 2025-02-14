package cz.tmktc.todolistapp.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import cz.tmktc.todolistapp.model.observer.ChangeType;
import cz.tmktc.todolistapp.model.observer.Observable;
import cz.tmktc.todolistapp.model.observer.Observer;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class TaskService implements Observable {
    private static final String BASE_URL = "http://localhost:8080/tasks";
    private static TaskService taskService = null;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final Map<ChangeType, Set<Observer>> listOfObservers = new HashMap<>();

    private TaskService() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        for (ChangeType changeType : ChangeType.values()) {
            listOfObservers.put(changeType, new HashSet<>());
        }
    }

    public static TaskService getInstance() {
        if (taskService == null) taskService = new TaskService();
        return taskService;
    }

    public List<Task> getAllTasks() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .GET()
                .build();

        HttpResponse<String> response;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (response.statusCode() == HttpURLConnection.HTTP_OK) {
            try {
                return objectMapper.readValue(response.body(), new TypeReference<>() {
                });
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException("Failed to fetch tasks: " + response.statusCode());
    }

    public Task getTask(String id) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .GET()
                .build();

        HttpResponse<String> response;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (response.statusCode() == HttpURLConnection.HTTP_OK) {
            try {
                notifyObserver();
                return objectMapper.readValue(response.body(), Task.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException("Task not found: " + response.statusCode());
    }

    public void saveTask(Task task) {
        String requestBody;
        try {
            requestBody = objectMapper.writeValueAsString(task);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (response.statusCode() == HttpURLConnection.HTTP_CREATED) notifyObserver();
        else throw new RuntimeException("Failed to create task: " + response.statusCode());
    }

    public void deleteTask(String id) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .DELETE()
                .build();

        HttpResponse<Void> response;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.discarding());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (response.statusCode() == HttpURLConnection.HTTP_NO_CONTENT) notifyObserver();
    }

    @Override
    public void register(ChangeType changeType, Observer observer) {
        listOfObservers.get(changeType).add(observer);
    }

    private void notifyObserver() {
        for (Observer observer : listOfObservers.get(ChangeType.TASKS_CHANGE)) {
            observer.update();
        }
    }
}