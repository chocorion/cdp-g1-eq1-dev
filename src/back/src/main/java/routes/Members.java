package routes;

import dao.MemberDAO;
import domain.Member;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("projects/{projectId}/members")
public class Members {
    @Inject MemberDAO memberDAO;

    @GET 
    @Produces("application/json")
    public Response getAllMembers(@PathParam("projectId") int projectId) {
        try {
            return Response.status(200).entity(memberDAO.getAllForProject(projectId)).build();
        } catch (Exception exception ) {
            return Response.status(400).entity(exception.getMessage()).build();
        }
    }

    @GET
    @Path("{user}")
    @Produces("application/json")
    public Response getMember( @PathParam("projectId") int projectId , @PathParam("user") int user){
        try{
            return Response.status(200).entity(memberDAO.getById(projectId, user)).build();
        } catch (Exception exception){
            return Response.status(400).entity(exception.getMessage()).build();
        }
    }

    @PUT
    @Path("{user}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response putMember(@PathParam("projectId") int projectId, @PathParam("user") int user, Member member) {
        member = new Member(projectId, member.role, member.level, user);

        try{
            memberDAO.update(member);
        } catch (Exception exception) {
            return Response
                    .status(Response.Status.CONFLICT)
                    .entity(exception.getMessage())
                    .build();
        }
        return Response.status(200).entity(member).build();
    }

    @POST
    @Consumes("application/json")
    @Produces("applciation/json")
    public Response postMember(@PathParam("projectId") int projectId , Member member){
        member = new Member(projectId, member.role, member.level);
        Member built;
        try {
            built = memberDAO.insert(member);
        } catch (Exception exception) {
            return Response
                    .status(Response.Status.CONFLICT)
                    .entity(exception.getMessage())
                    .build();
        }

        return Response.status(Response.Status.OK).entity(built).build();
    }

    @Path("{user}")
    @DELETE
    @Produces("application/json")
    public Response deleteMember(@PathParam("projectId") int projectId, @PathParam("user") int user) {
        try {
            Member member = memberDAO.getById(projectId, user);
            memberDAO.delete(member);
        } catch (Exception exception) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(exception.getMessage())
                    .build();
        }

        return Response.status(Response.Status.OK).build();
    }
}