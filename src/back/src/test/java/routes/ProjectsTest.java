package routes;

import domain.Project;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ProjectsTest {

    @Test
    void testGetAll() throws IOException {
        String jsonMimeType = "application/json";
        HttpResponse response = HttpUtils.get("projects/");

        assertEquals(response.getStatusLine().getStatusCode(), 200);

        String mimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();
        assertEquals(jsonMimeType, mimeType);
    }

    @Test
    void testInsert() throws IOException {
        String name = "Nom unique pour les tests !";
        String description = "Et hop une description !";

        HttpResponse response = HttpUtils.postItem("projects/", new Project(name, description));

        assertEquals(200, response.getStatusLine().getStatusCode());

        Project project2 = HttpUtils.getItemFromResponse(Project.class, response);
        assertNotEquals(0, project2.id);

        assertEquals(project2.name, name);
        assertEquals(project2.description, description);
    }

    @Test
    void testGetById() throws IOException {
        String name = "Nom unique pour les tests 2!";
        String description = "Et hop une description !";

        HttpResponse response = HttpUtils.postItem("projects/", new Project(name, description));
        String jsonFromResponse = EntityUtils.toString(response.getEntity());

        Project project = HttpUtils.mapper.readValue(jsonFromResponse, Project.class);

        response = HttpUtils.get("projects/" + project.id);
        Project project2 = HttpUtils.getItemFromResponse(Project.class, response);

        assertEquals(project, project2);
    }

    @Test
    void testUpdate() throws IOException {
        String name = "Nom unique pour les tests 3!";
        String description = "Et hop une description !";

        HttpResponse response = HttpUtils.postItem("projects/", new Project(name, description));
        Project inserted = HttpUtils.getItemFromResponse(Project.class, response);

        String newName = "Nom unique pour les tests 4!";
        Project newProject = new Project(newName, inserted.description, inserted.id);

        Project updated = HttpUtils.getItemFromResponse(Project.class, HttpUtils.putItem("projects/", newProject));

        assertEquals(newProject, updated);

        response = HttpUtils.get("projects/" + updated.id);
        assertEquals(updated, HttpUtils.getItemFromResponse(Project.class, response));
    }

    @Test
    void testDelete() throws IOException {
        String name = "Nom unique pour les tests 5!";
        String description = "Et hop une description !";

        HttpResponse response = HttpUtils.postItem("projects", new Project(name, description));
        Project inserted = HttpUtils.getItemFromResponse(Project.class, response);

        response = HttpUtils.delete("projects/" + inserted.id);
        assertEquals(200, response.getStatusLine().getStatusCode());

        response = HttpUtils.get("projects/" + inserted.id);

        assertEquals(404, response.getStatusLine().getStatusCode());
    }
}