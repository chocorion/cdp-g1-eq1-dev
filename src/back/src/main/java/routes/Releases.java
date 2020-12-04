package routes;


import dao.ReleaseDAO;
import domain.Release;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("projects/{projectId}/releases")
public class Releases {
    @Inject ReleaseDAO releaseDAO;

    @GET
    @Produces("application/json")
    public Response getProjectReleases(@PathParam("projectId") int projectId) {
        try {
            return Response.status(Response.Status.OK).entity(releaseDAO.getAllForProject(projectId)).build();
        } catch (Exception exception) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Path("{id}")
    @GET
    @Produces("application/json")
    public Response get(@PathParam("projectId") int projectId, @PathParam("id") int id) {
        try {
            return Response.status(Response.Status.OK).entity(releaseDAO.getById(id)).build();
        } catch (Exception exception) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response post(Release release) {
        Release built;
        try {
            built = releaseDAO.insert(release);
        } catch (Exception exception) {
            return Response
                    .status(Response.Status.CONFLICT)
                    .entity(exception.getMessage())
                    .build();
        }

        return Response.status(Response.Status.OK).entity(built).build();
    }

    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Response put(Release release) {
        try {
            releaseDAO.update(release);
            release = releaseDAO.getById(release.id);
        } catch (Exception exception) {
            return Response
                    .status(Response.Status.CONFLICT)
                    .entity(exception.getMessage())
                    .build();
        }

        return Response.status(Response.Status.OK).entity(release).build();
    }

    @Path("{id}")
    @DELETE
    public Response delete(@PathParam("id") int id) {
        try {
            Release release = releaseDAO.getById(id);
            releaseDAO.delete(release);
        } catch (Exception exception) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(exception.getMessage())
                    .build();
        }

        return Response.status(Response.Status.OK).build();
    }
}
