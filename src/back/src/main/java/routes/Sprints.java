package routes;

import dao.SprintDAO;
import dao.UserStoryDAO;
import domain.Sprint;
import domain.UserStory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("projects/{projectId}/sprints")
public class Sprints {
    @Inject SprintDAO sprintDAO;
    @Inject UserStoryDAO userStoryDAO;

    @GET
    @Produces("application/json")
    public Response getAllForProject(@PathParam("projectId") int projectId) {
        try {
            return Response.status(200).entity(sprintDAO.getAllForProject(projectId)).build();
        } catch (Exception e) {
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("{sprintId}/us")
    @Produces("application/json")
    public Response getUs(@PathParam("projectId") int projectId, @PathParam("sprintId") int sprintId) {
        try {
            return Response.status(200).entity(userStoryDAO.getBySprint(projectId, sprintId)).build();
        } catch (Exception e) {
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response insert(@PathParam("projectId") int projectId, Sprint sprint) {
        sprint = new Sprint(projectId, sprint.name, sprint.id);

        try {
            return Response.status(200).entity(sprintDAO.insert(sprint)).build();
        } catch (Exception e) {
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("{sprintId}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response update(@PathParam("projectId") int projectId, @PathParam("sprintId") int sprintId, Sprint sprint) {
        sprint = new Sprint(projectId, sprint.name, sprintId);

        try {
            sprintDAO.update(sprint);
            return Response.status(200).entity(sprint).build();
        } catch (Exception e) {
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("{sprintId}")
    public Response delete(@PathParam("projectId") int projectId, @PathParam("sprintId") int sprintId) {
        try {
            sprintDAO.delete(sprintDAO.getById(projectId, sprintId));
            return Response.status(200).build();
        } catch (Exception e) {
            return Response.status(400).entity(e.getMessage()).build();
        }
    }
}
