package routes;

import dao.DAOFactory;
import dao.UserStoryDAO;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("{projectId}/us")
public class UserStories {
    @GET
    @Produces("application/json")
    public Response getAlls(@PathParam("projectId") int projectId) {
        UserStoryDAO usDao = DAOFactory.getInstance().getUserStoryDAO();

        try {
            return Response.status(200).entity(usDao.getAllForProject(projectId)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
