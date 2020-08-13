package com.mntest;

import com.mntest.dto.User;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.net.MalformedURLException;
import java.net.URL;

@MicronautTest
public class UserServiceTest {

    @Inject
    EmbeddedServer server;

    @Test
    public void testAdd() throws MalformedURLException {
        HttpClient client = HttpClient.create(new URL("http://" + server.getHost() + ":" + server.getPort()));
        User user = new User("John");
        user = client.toBlocking().retrieve(HttpRequest.POST("/users", user), User.class);
        Assertions.assertNotNull(user);
        Assertions.assertEquals(1, user.getUserId());
    }

    @Test
    public void testAddNotValid() throws MalformedURLException {
        HttpClient client = HttpClient.create(new URL("http://" + server.getHost() + ":" + server.getPort()));
        final User user = new User("John");
        Assertions.assertThrows(HttpClientResponseException.class,
                () -> client.toBlocking().retrieve(HttpRequest.POST("/users", user), User.class),
                "person.age: must be greater than or equal to 0");
    }

    @Test
    public void testGetAll() throws MalformedURLException {
        HttpClient client = HttpClient.create(new URL("http://" + server.getHost() + ":" + server.getPort()));
        User[] persons = client.toBlocking().retrieve(HttpRequest.GET("/users"), User[].class);
        Assertions.assertEquals(1, persons.length);
    }
}
