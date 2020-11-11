package dao;

public interface DAOFactory {
    ProjectDAO getProjectDAO();
    TestDAO getTestDAO();
}
