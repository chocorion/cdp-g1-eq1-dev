package routes;

import domain.Project;
import domain.Sprint;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SprintsTest {
    Project project;

    @BeforeEach
    public void beforeEach() throws IOException {
        project = new Project(
                "My project for sprint testing",
                "Hey, this is a beautiful description !"
        );

        project = HttpUtils.getItemFromResponse(
                Project.class,
                HttpUtils.<Project>postItem("projects", project)
        );
    }

    @AfterEach
    public void afterAll() throws IOException {
        HttpUtils.delete("projects/" + project.id);
    }

    @Test
    void testGetAll() throws IOException {
        String jsonMimeType = "application/json";
        HttpResponse response = HttpUtils.get("projects/1/sprints");

        assertEquals(response.getStatusLine().getStatusCode(), 200);

        String mimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();
        assertEquals(jsonMimeType, mimeType);
    }


    @Test
    void testGetEmpty() throws IOException {
        HttpResponse response = HttpUtils.get("projects/" + project.id + "/sprints/");
        assertEquals(200, response.getStatusLine().getStatusCode());

        List result = HttpUtils.getItemFromResponse(List.class, response);
        assertEquals(0, result.size());
    }

    @Test
    void testGetNotEmpty() throws IOException {
        HttpUtils.postItem(
                "projects/" + project.id + "/sprints/",
                new Sprint(project.id, "sprint1")
        );

        HttpUtils.postItem(
                "projects/" + project.id + "/sprints/",
                new Sprint(project.id, "sprint2")
        );

        HttpResponse response = HttpUtils.get("projects/" + project.id + "/sprints/");
        assertEquals(200, response.getStatusLine().getStatusCode());


        List result = HttpUtils.getItemFromResponse(List.class, response);
        assertEquals(2, result.size());
    }

    @Test
    void testGetActive() throws IOException {

        HttpUtils.postItem(
                "projects/" + project.id + "/sprints/",
                new Sprint(project.id, "sprint2", "active")
        );
        HttpResponse response = HttpUtils.get("projects/" + project.id + "/sprints/active");
        assertEquals(200, response.getStatusLine().getStatusCode());

        Sprint result = HttpUtils.getItemFromResponse(Sprint.class, response);
        assertNotNull(result);
    }

    @Test
    void testGetNoActive() throws IOException {
        System.out.println("Sending request to projects/" + project.id + "/sprints/active");

        HttpResponse response = HttpUtils.get("projects/" + project.id + "/sprints/active");
        assertEquals(400, response.getStatusLine().getStatusCode());
    }

}