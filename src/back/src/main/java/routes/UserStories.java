package routes;

import dao.TaskDAO;
import dao.UserStoryDAO;
import domain.UserStory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;


@Path("projects/{projectId}/us")
public class UserStories {
    @Inject UserStoryDAO userStoryDAO;
    @Inject TaskDAO taskDAO;

    @GET
    @Produces("application/json")
    public Response getAll(@PathParam("projectId") int projectId) {
        try {
            return Response.status(200).entity(userStoryDAO.getAllForProject(projectId)).build();
        } catch (Exception e) {
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("unreleased")
    @Produces("application/json")
    public Response getUnreleased(@PathParam("projectId") int projectId) {
        try {
            return Response.status(200).entity(userStoryDAO.getUnreleasedForProject(projectId)).build();
        } catch (Exception e) {
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("{usId}")
    @Produces("application/json")
    public Response get(@PathParam("projectId") int projectId, @PathParam("usId") int usId) {
        try {
            return Response.status(200).entity(userStoryDAO.getById(projectId, usId)).build();
        } catch (Exception e) {
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("{usId}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response putUs(@PathParam("projectId") int projectId, @PathParam("usId") int usId, UserStory userStory) {
        userStory = new UserStory(projectId, userStory.description, userStory.priority, userStory.difficulty, userStory.sprint, usId);

        try {
            userStoryDAO.update(userStory);
        } catch (Exception exception) {
            return Response
                    .status(Response.Status.CONFLICT)
                    .entity(exception.getMessage())
                    .build();
        }

        return Response.status(Response.Status.OK).entity(userStory).build();
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response postUs(@PathParam("projectId") int projectId, UserStory userStory) {
        userStory = new UserStory(projectId, userStory.description, userStory.priority, userStory.difficulty, userStory.sprint);

        UserStory built;
        try {
            built = userStoryDAO.insert(userStory);
        } catch (Exception exception) {
            return Response
                    .status(Response.Status.CONFLICT)
                    .entity(exception.getMessage())
                    .build();
        }

        return Response.status(Response.Status.OK).entity(built).build();
    }

    @Path("{usId}")
    @DELETE
    @Produces("application/json")
    public Response deleteUs(@PathParam("projectId") int projectId, @PathParam("usId") int usId) {
        try {
            UserStory userStory = userStoryDAO.getById(projectId, usId);

            userStoryDAO.delete(userStory);
        } catch (Exception exception) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(exception.getMessage())
                    .build();
        }

        return Response.status(Response.Status.OK).build();
    }
}
