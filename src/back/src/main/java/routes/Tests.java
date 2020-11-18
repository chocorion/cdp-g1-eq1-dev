package routes;

import dao.DAOFactory;
import dao.TestDAO;
import domain.Test;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/projects/{id}/tests")
public class Tests {
    @GET
    @Produces("application/json")
    public Response getTests(@PathParam("id") int id) {
        TestDAO dao = DAOFactory.getInstance().getTestDAO();

        try {
            return Response.status(Response.Status.OK).entity(dao.getAllForProject(id)).build();
        } catch (Exception exception) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response postTest(@PathParam("id") int id, Test test) {
        test = new Test(test.name, test.description, test.lastExecution, test.state, id);
        TestDAO dao = DAOFactory.getInstance().getTestDAO();
        Test built;
        try {
            built = dao.insert(test);
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
        test = new Test(test.name, test.description, test.lastExecution, test.state, test_id, id);
        TestDAO dao = DAOFactory.getInstance().getTestDAO();

        try {
            dao.update(test);
        } catch (Exception exception) {
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
        TestDAO dao = DAOFactory.getInstance().getTestDAO();

        try {
            Test test = dao.getById(test_id);
            if (test.id != id) {
                return Response
                        .status(Response.Status.CONFLICT)
                        .entity("Test.projectId doesn't match with path")
                        .build();
            }

            dao.delete(test);
        } catch (Exception exception) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(exception.getMessage())
                    .build();
        }

        return Response.status(Response.Status.OK).build();
    }
}
