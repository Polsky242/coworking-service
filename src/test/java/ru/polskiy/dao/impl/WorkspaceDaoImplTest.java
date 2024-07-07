package ru.polskiy.dao.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.polskiy.conteiners.PostgresTestContainer;
import ru.polskiy.liquibase.LiquibaseInstance;
import ru.polskiy.model.entity.Workspace;
import ru.polskiy.util.ConnectionManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("workspace dao implementation test")
public class WorkspaceDaoImplTest extends PostgresTestContainer {

    private WorkspaceDaoImpl workspaceDao;

    @BeforeEach
    public void setUp() {
        ConnectionManager connectionManager = new ConnectionManager(
                container.getJdbcUrl(), container.getUsername(), container.getPassword(),
                "org.postgresql.Driver");

        LiquibaseInstance liquibaseTest = new LiquibaseInstance(connectionManager.getConnection(), "db/changelog/changelog.xml", "migration");
        liquibaseTest.runMigrations();

        workspaceDao = new WorkspaceDaoImpl(connectionManager);
    }

    @Test
    @DisplayName("find by id method verification test")
    public void testFindById() {
        Workspace workspace = Workspace.builder()
                .userId(1L)
                .typeId(1L)
                .startDate(LocalDateTime.of(2024,6,12,12,30))
                .endDate(LocalDateTime.of(2024,6,12,16,30))
                .build();
        workspace.onCreate();
        workspaceDao.save(workspace);

        Optional<Workspace> foundWorkspace = workspaceDao.findById(1L);
        assertTrue(foundWorkspace.isPresent());
        assertEquals(LocalDateTime.of(2024,6,12,12,30), foundWorkspace.get().getStartDate());
        assertEquals(LocalDateTime.of(2024,6,12,16,30), foundWorkspace.get().getEndDate());

        Optional<Workspace> notFoundWorkspace = workspaceDao.findById(999L);
        assertFalse(notFoundWorkspace.isPresent());
    }

    @Test
    @DisplayName("find all method verification test")
    public void testFindAll() {
        Workspace workspace1 = Workspace.builder()
                .userId(1L)
                .typeId(1L)
                .startDate(LocalDateTime.of(2024,6,12,12,30))
                .endDate(LocalDateTime.of(2024,6,12,16,30))
                .build();
        workspace1.onCreate();
        Workspace workspace2 = Workspace.builder()
                .userId(2L)
                .typeId(2L)
                .startDate(LocalDateTime.of(2024,6,12,12,30))
                .endDate(LocalDateTime.of(2024,6,12,16,30))
                .build();
        workspace2.onCreate();

        workspaceDao.save(workspace1);
        workspaceDao.save(workspace2);

        List<Workspace> allWorkspaces = workspaceDao.findAll();
        assertFalse(allWorkspaces.isEmpty());
        assertEquals(2, allWorkspaces.size());
    }

    @Test
    @DisplayName("save method verification test")
    public void testSave() {
        Workspace workspaceToSave = Workspace.builder()
                .userId(1L)
                .typeId(1L)
                .startDate(LocalDateTime.of(2024,6,12,12,30))
                .endDate(LocalDateTime.of(2024,6,12,16,30))
                .build();
        workspaceToSave.onCreate();

        Workspace savedWorkspace = workspaceDao.save(workspaceToSave);
        assertNotNull(savedWorkspace.getId());
        assertEquals(savedWorkspace.getUserId(), workspaceToSave.getUserId());
        assertEquals(savedWorkspace.getTypeId(), workspaceToSave.getTypeId());
        assertEquals(savedWorkspace.getStartDate(), workspaceToSave.getStartDate() );
        assertEquals(savedWorkspace.getEndDate(), workspaceToSave.getEndDate());
    }

    @Test
    @DisplayName("save null workspace method verification test")
    public void testSave_NullWorkspace() {
        assertThrows(NullPointerException.class, () -> workspaceDao.save(null));
    }
}
