package routes;

import dao.SprintDAO;
import dao.UserStoryDAO;
import domain.Sprint;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

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
    @Path("active")
    @Produces("application/json")
    public Response getActive(@PathParam("projectId") int projectId) {
       try {
           Sprint activeSprint = sprintDAO.getActiveForProject(projectId);

           if (activeSprint == null){
               System.out.println("Active sprint not found...");
               throw new Exception("Can't find active sprint");
           }

            return Response.status(200).entity(activeSprint).build();
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
        if (!sprint.state.equals("pending")) {
            return Response.status(400).entity("You can only create a sprint with state 'pending'.").build();
        }

        sprint = new Sprint(projectId, sprint.name, "pending", sprint.id);

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
        sprint = new Sprint(projectId, sprint.name, sprint.state, sprintId);

        if (sprint.state.equals("active")) {
            Sprint active = null;
            try {
                active = sprintDAO.getActiveForProject(projectId);
            } catch (SQLException throwables) {
                return Response.status(500).entity(throwables.getMessage()).build();
            }

            if (active != null && !active.equals(sprint)) {
                System.out.println("Can't set this sprint to active !");

                return Response
                        .status(400)
                        .entity("Can't set this sprint to active, there is another active sprint.")
                        .build();
            }
        }

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
