package routes;

import dao.DAOFactory;
import dao.ProjectDAO;
import domain.Project;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("projects")
public class Projects {
    @GET
    @Produces("application/json")
    public List<Project> projects() {
        ProjectDAO projectDAO = DAOFactory.getInstance().getProjectDAO();

        return projectDAO.getAll();
    }
}
