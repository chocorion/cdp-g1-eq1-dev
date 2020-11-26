package routes;

import domain.Project;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TestsTest {
    static Project project;
    static String baseUrl;

    @BeforeAll
    static void setup() throws IOException {
        HttpResponse response = HttpUtils.postItem(
                "projects/",
                new Project("projet pour tester les tests", "hod")
        );

        TestsTest.project = HttpUtils.getItemFromResponse(Project.class, response);
        TestsTest.baseUrl = "projects/" + TestsTest.project.id + "/tests/";
    }

    @AfterAll
    static void teardown() throws IOException {
        HttpUtils.delete("projects/" + TestsTest.project.id);
    }

    @Test
    void testGetAllForProject() throws IOException {
        String jsonMimeType = "application/json";

        HttpResponse response = HttpUtils.get(baseUrl);

        assertEquals(response.getStatusLine().getStatusCode(), 200);

        String mimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();
        assertEquals(jsonMimeType, mimeType);
    }
}