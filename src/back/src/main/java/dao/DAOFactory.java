package dao;

public abstract class DAOFactory {
    private static DAOFactory instance;

    abstract public ProjectDAO getProjectDAO();
    abstract public TestDAO getTestDAO();

    public static DAOFactory getInstance() {
        if (instance == null)
            instance = new SQLDAOFactory();

        return instance;
    }
}
