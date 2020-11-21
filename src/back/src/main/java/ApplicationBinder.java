import dao.ProjectDAO;
import dao.TaskDAO;
import dao.UserStoryDAO;
import dao.sql.SQLProjectDAO;
import dao.sql.SQLTaskDAO;
import dao.sql.SQLTestDAO;
import dao.TestDAO;
import dao.sql.SQLUserStoryDAO;
import org.glassfish.jersey.internal.inject.AbstractBinder;

public class ApplicationBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(SQLProjectDAO.class).to(ProjectDAO.class);
        bind(SQLTestDAO.class).to(TestDAO.class);
        bind(SQLUserStoryDAO.class).to(UserStoryDAO.class);
        bind(SQLTaskDAO.class).to(TaskDAO.class);
    }
}
