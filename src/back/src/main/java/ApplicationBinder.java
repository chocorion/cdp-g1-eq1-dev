import dao.ProjectDAO;
import dao.SQLDAO.SQLProjectDAO;
import dao.SQLDAO.SQLTestDAO;
import dao.TestDAO;
import org.glassfish.jersey.internal.inject.AbstractBinder;

public class ApplicationBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(SQLProjectDAO.class).to(ProjectDAO.class);
        bind(SQLTestDAO.class).to(TestDAO.class);
    }
}
