package ru.polskiy.dao.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.polskiy.conteiners.PostgresTestContainer;
import ru.polskiy.liquibase.LiquibaseInstance;
import ru.polskiy.model.entity.Workspace;
import ru.polskiy.model.entity.WorkspaceType;
import ru.polskiy.util.ConnectionManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("WorkspaceType dao implementation test")
public class WorkspaceTypeDaoImplTest extends PostgresTestContainer {

    private WorkspaceTypeDaoImpl workspaceTypeDao;

    @BeforeEach
    public void setUp() {
        ConnectionManager connectionManager = new ConnectionManager(
                container.getJdbcUrl(), container.getUsername(), container.getPassword(),
                "org.postgresql.Driver");

        LiquibaseInstance liquibaseTest = new LiquibaseInstance(connectionManager.getConnection(), "db/changelog/changelog.xml", "migration");
        liquibaseTest.runMigrations();

        workspaceTypeDao = new WorkspaceTypeDaoImpl(connectionManager);
    }

    @Test
    @DisplayName("find by id method verification test")
    public void testFindById() {
        WorkspaceType workspaceType = WorkspaceType.builder()
                .typeName("TestType")
                .build();
        workspaceTypeDao.save(workspaceType);

        Optional<WorkspaceType> workspaceTypeDb = workspaceTypeDao.findById(3L);
        assertTrue(workspaceTypeDb.isPresent());

        Optional<WorkspaceType> notFoundWorkspaceType = workspaceTypeDao.findById(999L);
        assertFalse(notFoundWorkspaceType.isPresent());
    }

    @Test
    @DisplayName("find all method verification test")
    public void testFindAll() {
        WorkspaceType workspaceType1 = WorkspaceType.builder()
                .typeName("TestType1")
                .build();
        WorkspaceType workspaceType2 = WorkspaceType.builder()
                .typeName("TestType2")
                .build();

        workspaceTypeDao.save(workspaceType1);
        workspaceTypeDao.save(workspaceType2);

        List<WorkspaceType> allWorkspaceTypes = workspaceTypeDao.findAll();
        assertFalse(allWorkspaceTypes.isEmpty());
        assertEquals(4, allWorkspaceTypes.size());
    }

    @Test
    @DisplayName("save method verification test")
    public void testSave() {
        WorkspaceType workspaceTypeToSave = WorkspaceType.builder()
                .typeName("TestType")
                .build();

        WorkspaceType savedWorkspaceType = workspaceTypeDao.save(workspaceTypeToSave);
        assertNotNull(savedWorkspaceType.getId());
        assertEquals(workspaceTypeToSave.getTypeName(), savedWorkspaceType.getTypeName());
    }
}
