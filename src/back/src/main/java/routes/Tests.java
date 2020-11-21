package routes;

import dao.TestDAO;
import domain.Test;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/projects/{id}/tests")
public class Tests {
    @Inject TestDAO testDAO;

    @GET
    @Produces("application/json")
    public Response getTests(@PathParam("id") int id) {
        try {
            return Response.status(Response.Status.OK).entity(testDAO.getAllForProject(id)).build();
        } catch (Exception exception) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response postTest(@PathParam("id") int id, Test test) {
        test = new Test(test.name, test.description, test.lastExecution, test.state, id);
        Test built;
        try {
            built = testDAO.insert(test);
        } catch (Exception exception) {
            return Response
                    .status(Response.Status.CONFLICT)
                    .entity(exception.getMessage())
                    .build();
        }

        return Response.status(Response.Status.OK).entity(built).build();
    }

    @PUT
    @Path("{test_id}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response putTest(@PathParam("id") int id, @PathParam("test_id") int test_id, Test test) {
        test = new Test(test.name, test.description, test.lastExecution, test.state, id, test_id);

        try {
            testDAO.update(test);
        } catch (Exception exception) {
            System.out.println("Probleme : " + exception.getMessage());
            return Response
                    .status(Response.Status.CONFLICT)
                    .entity(exception.getMessage())
                    .build();
        }

        return Response.status(Response.Status.OK).entity(test).build();
    }

    @Path("{test_id}")
    @DELETE
    @Produces("application/json")
    public Response deleteTest(@PathParam("id") int id, @PathParam("test_id") int test_id) {
        try {
            Test test = testDAO.getById(test_id);
            if (test.id != id) {
                return Response
                        .status(Response.Status.CONFLICT)
                        .entity("Test.projectId doesn't match with path")
                        .build();
            }

            testDAO.delete(test);
        } catch (Exception exception) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(exception.getMessage())
                    .build();
        }

        return Response.status(Response.Status.OK).build();
    }
}
