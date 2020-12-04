import dao.*;
import dao.sql.*;
import org.glassfish.jersey.internal.inject.AbstractBinder;

public class ApplicationBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(SQLProjectDAO.class).to(ProjectDAO.class);
        bind(SQLTestDAO.class).to(TestDAO.class);
        bind(SQLUserStoryDAO.class).to(UserStoryDAO.class);
        bind(SQLTaskDAO.class).to(TaskDAO.class);
        bind(SQLDODDAO.class).to(DODDAO.class);
        bind(SQLSprintDAO.class).to(SprintDAO.class);
        bind(SQLMemberDAO.class).to(MemberDAO.class);
        bind(SQLReleaseDAO.class).to(ReleaseDAO.class);
    }
}
