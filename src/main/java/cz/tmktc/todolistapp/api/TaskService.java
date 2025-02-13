package cz.tmktc.todolistapp.api;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class TaskService {
    private static final String BASE_URL = "http://localhost:8080/tasks";
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public TaskService() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public List<Task> getAllTasks() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == HttpURLConnection.HTTP_OK) {
            return objectMapper.readValue(response.body(), new TypeReference<>() {
            });
        }
        throw new RuntimeException("Failed to fetch tasks: " + response.statusCode());
    }

    public Task getTask(String id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == HttpURLConnection.HTTP_OK) {
            return objectMapper.readValue(response.body(), Task.class);
        }
        throw new RuntimeException("Task not found: " + response.statusCode());
    }

    public Task createTask(Task task) throws IOException, InterruptedException {
        String requestBody = objectMapper.writeValueAsString(task);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == HttpURLConnection.HTTP_CREATED) {
            return objectMapper.readValue(response.body(), Task.class);
        }
        throw new RuntimeException("Failed to create task: " + response.statusCode());
    }

    public boolean deleteTask(String id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .DELETE()
                .build();

        HttpResponse<Void> response = httpClient.send(request, HttpResponse.BodyHandlers.discarding());

        return response.statusCode() == HttpURLConnection.HTTP_NO_CONTENT;
    }
}