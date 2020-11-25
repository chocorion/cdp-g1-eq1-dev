package routes;

import dao.DODDAO;
import dao.TaskDAO;
import domain.DOD;
import domain.Task;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("projects/{projectId}/tasks/")
public class Tasks {
    @Inject TaskDAO taskDAO;
    @Inject DODDAO dodDAO;

    @GET
    @Produces("application/json")
    public Response getAllForProject(@PathParam("projectId") int projectId) {
        try {
            return Response.status(200).entity(taskDAO.getAllForProject(projectId)).build();
        } catch (Exception e) {
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("us/{usId}")
    @Produces
    public Response getAllForUs(@PathParam("projectId") int projectId, @PathParam("usId") int usId) {
        try {
            return Response.status(200).entity(taskDAO.getAllForUserStory(projectId, usId)).build();
        } catch (Exception e) {
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("{taskId}/parents")
    @Produces("application/json")
    public Response getParentTasks(@PathParam("projectId") int projectId, @PathParam("taskId") int taskId) {
        try {
            Task task = taskDAO.getById(projectId, taskId);
            return Response.status(200).entity(taskDAO.getParentTasks(task)).build();
        } catch (Exception e) {
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("{taskId}/parents")
    @Consumes("application/json")
    @Produces("application/json")
    public Response addParentTask(@PathParam("projectId") int projectId, @PathParam("taskId") int childId, Task parent) {
        try {
            Task child = taskDAO.getById(projectId, childId);
            taskDAO.addDependency(parent, child);
            return Response.status(200).entity(taskDAO.getParentTasks(child)).build();
        } catch (Exception e) {
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("{taskId}/parents/{parentId}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response deleteParentTask(@PathParam("projectId") int projectId, @PathParam("taskId") int childId, @PathParam("parentId") int parentId) {
        try {
            Task child = taskDAO.getById(projectId, childId);
            Task parent = taskDAO.getById(projectId, parentId);
            taskDAO.deleteDependency(parent, child);
            return Response.status(200).entity(taskDAO.getParentTasks(child)).build();
        } catch (Exception e) {
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("{taskId}/children")
    @Produces("application/json")
    public Response getChildrenTasks(@PathParam("projectId") int projectId, @PathParam("taskId") int taskId) {
        try {
            Task task = taskDAO.getById(projectId, taskId);
            return Response.status(200).entity(taskDAO.getChildrenTasks(task)).build();
        } catch (Exception e) {
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("{taskId}/children")
    @Consumes("application/json")
    @Produces("application/json")
    public Response addChildTask(@PathParam("projectId") int projectId, @PathParam("taskId") int parentId, Task child) {
        try {
            Task parent = taskDAO.getById(projectId, parentId);
            taskDAO.addDependency(parent, child);
            return Response.status(200).entity(taskDAO.getChildrenTasks(parent)).build();
        } catch (Exception e) {
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("{taskId}/children/{childId}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response deleteChildTask(@PathParam("projectId") int projectId, @PathParam("taskId") int parentId,  @PathParam("childId") int childId) {
        try {
            Task parent = taskDAO.getById(projectId, parentId);
            Task child = taskDAO.getById(projectId, childId);
            taskDAO.deleteDependency(parent, child);
            return Response.status(200).entity(taskDAO.getChildrenTasks(parent)).build();
        } catch (Exception e) {
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("{taskId}/DOD")
    @Produces("application/json")
    public Response getDOD(@PathParam("projectId") int projectId, @PathParam("taskId") int taskId) {
        try {
            return Response.status(200).entity(dodDAO.getAllForTask(projectId, taskId)).build();
        } catch (Exception e) {
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("{taskId}/DOD")
    @Consumes("application/json")
    @Produces("application/json")
    public Response updateDOD(@PathParam("projectId") int projectId, @PathParam("taskId") int taskId, DOD dod) {
        dod = new DOD(projectId, taskId, dod.description, dod.state, dod.id);
        try {
            dodDAO.update(dod);
            return Response.status(200).entity(dod).build();
        } catch (Exception e) {
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("{taskId}/DOD")
    @Consumes("application/json")
    @Produces("application/json")
    public Response insertDOD(@PathParam("projectId") int projectId, @PathParam("taskId") int taskId, DOD dod) {
        dod = new DOD(projectId, taskId, dod.description, dod.state, dod.id);
        try {
            DOD inserted = dodDAO.insert(dod);
            return Response.status(200).entity(inserted).build();
        } catch (Exception e) {
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("{taskId}/DOD")
    @Consumes("application/json")
    @Produces("application/json")
    public Response deleteDOD(@PathParam("projectId") int projectId, @PathParam("taskId") int taskId, DOD dod) {
        dod = new DOD(projectId, taskId, dod.description, dod.state, dod.id);
        try {
            dodDAO.delete(dod);
            return getDOD(projectId, taskId);
        } catch (Exception e) {
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response insert(@PathParam("projectId") int projectId, Task task) {
        task = new Task(projectId, task.usId, task.memberId, task.title, task.duration, task.status);

        try {
            return Response.status(200).entity(taskDAO.insert(task)).build();
        } catch (Exception e) {
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("{taskId}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response update(@PathParam("projectId") int projectId, @PathParam("taskId") int taskId, Task task) {
        task = new Task(projectId, task.usId, task.memberId, task.title, task.duration, task.status, taskId);

        try {
            taskDAO.update(task);
            return Response.status(200).entity(task).build();
        } catch (Exception e) {
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("{taskId}")
    public Response delete(@PathParam("projectId") int projectId, @PathParam("taskId") int taskId) {
        try {
            taskDAO.delete(taskDAO.getById(projectId, taskId));
            return Response.status(200).build();
        } catch (Exception e) {
            return Response.status(400).entity(e.getMessage()).build();
        }
    }
}
