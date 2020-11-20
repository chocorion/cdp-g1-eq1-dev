import dao.ProjectDAO;
import dao.sql.SQLProjectDAO;
import dao.sql.SQLTestDAO;
import dao.TestDAO;
import org.glassfish.jersey.internal.inject.AbstractBinder;

public class ApplicationBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(SQLProjectDAO.class).to(ProjectDAO.class);
        bind(SQLTestDAO.class).to(TestDAO.class);
    }
}
