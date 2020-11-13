import dao.SQLDAOFactoryTest;
import dao.SQLProjectDAOTest;
import dao.SQLTestDAOTest;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;


@RunWith(JUnitPlatform.class)
@SelectClasses( {
        SQLProjectDAOTest.class,
        SQLDAOFactoryTest.class,
        SQLTestDAOTest.class
} )
@SelectPackages("")
public class TestSuite {
}
