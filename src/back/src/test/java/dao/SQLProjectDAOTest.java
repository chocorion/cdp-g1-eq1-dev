package dao;

import org.junit.jupiter.api.Test;

import dao.sql.SQLProjectDAO;
import domain.Project;

import static org.junit.jupiter.api.Assertions.*;

class ProjectTest {

    @Test
    void testGet() throws Exception {
        new SQLProjectDAO().getAll().forEach(project -> System.out.println(project.description));;
    }

    @Test
    void testInsert() throws Exception {
        Project project = new Project("Java project","I am a java project");
        new SQLProjectDAO().insert(project);
    }
}