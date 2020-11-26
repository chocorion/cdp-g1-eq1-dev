package routes;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Project;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ProjectsTest {
    static ObjectMapper mapper = new ObjectMapper().configure(
            DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
            false
    );

    static HttpResponse postProject(Project project) throws IOException {
        HttpPost request = new HttpPost("http://back:8080/api/v1/projects/");
        request.addHeader("Accept", "application/json");
        request.addHeader("Content-type", "application/json");

        request.setEntity(new StringEntity(mapper.writeValueAsString(project)));

        return HttpClientBuilder.create().build().execute(request);
    }

    static HttpResponse putProject(Project project) throws IOException {
        HttpPut request = new HttpPut("http://back:8080/api/v1/projects/");
        request.addHeader("Accept", "application/json");
        request.addHeader("Content-type", "application/json");

        request.setEntity(new StringEntity(mapper.writeValueAsString(project)));

        return HttpClientBuilder.create().build().execute(request);
    }

    static Project getProjectFromResponce(HttpResponse response) throws IOException {
        String jsonFromResponse = EntityUtils.toString(response.getEntity());
        return mapper.readValue(jsonFromResponse, Project.class);
    }

    @Test
    void testGetAll() throws IOException {
        String jsonMimeType = "application/json";
        HttpUriRequest request = new HttpGet("http://back:8080/api/v1/projects");
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        assertEquals(response.getStatusLine().getStatusCode(), 200);

        String mimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();
        assertEquals(jsonMimeType, mimeType);
    }

    @Test
    void testInsert() throws IOException {
        String name = "Nom unique pour les tests !";
        String description = "Et hop une description !";

        HttpResponse response = postProject(new Project(name, description));

        assertEquals(200, response.getStatusLine().getStatusCode());

        Project project2 = getProjectFromResponce(response);
        assertNotEquals(project2.id, 0);

        assertEquals(project2.name, name);
        assertEquals(project2.description, description);
    }

    @Test
    void testGetById() throws IOException {
        String name = "Nom unique pour les tests 2!";
        String description = "Et hop une description !";

        HttpResponse response = postProject(new Project(name, description));
        String jsonFromResponse = EntityUtils.toString(response.getEntity());

        Project project = mapper.readValue(jsonFromResponse, Project.class);

        HttpUriRequest request = new HttpGet("http://back:8080/api/v1/projects/" + project.id);
        response = HttpClientBuilder.create().build().execute(request);
        Project project2 = getProjectFromResponce(response);

        assertEquals(project, project2);
    }

    @Test
    void testUpdate() throws IOException {
        String name = "Nom unique pour les tests 3!";
        String description = "Et hop une description !";

        HttpResponse response = postProject(new Project(name, description));
        Project inserted = getProjectFromResponce(response);

        String newName = "Nom unique pour les tests 4!";
        Project newProject = new Project(newName, inserted.description, inserted.id);

        Project updated = getProjectFromResponce(putProject(newProject));

        assertEquals(newProject, updated);

        HttpUriRequest request = new HttpGet("http://back:8080/api/v1/projects/" + updated.id);
        response = HttpClientBuilder.create().build().execute(request);
        assertEquals(updated, getProjectFromResponce(response));
    }

    @Test
    void testDelete() throws IOException {
        String name = "Nom unique pour les tests 5!";
        String description = "Et hop une description !";

        HttpResponse response = postProject(new Project(name, description));
        Project inserted = getProjectFromResponce(response);

        HttpDelete delete = new HttpDelete("http://back:8080/api/v1/projects/" + inserted.id);
        HttpResponse deleteResponse = HttpClientBuilder.create().build().execute(delete);
        assertEquals(200, response.getStatusLine().getStatusCode());

        HttpUriRequest request = new HttpGet("http://back:8080/api/v1/projects/" + inserted.id);
        response = HttpClientBuilder.create().build().execute(request);

        assertEquals(404, response.getStatusLine().getStatusCode());
    }
}