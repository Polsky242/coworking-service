package ru.polskiy.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.polskiy.dao.WorkspaceDao;
import ru.polskiy.exception.DuplicateException;
import ru.polskiy.exception.NoSuchWorkspaceException;
import ru.polskiy.model.entity.User;
import ru.polskiy.model.entity.Workspace;
import ru.polskiy.model.entity.WorkspaceType;
import ru.polskiy.service.impl.WorkspaceServiceImpl;

import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class WorkspaceServiceImplTest {

    @InjectMocks
    private WorkspaceServiceImpl workspaceService;

    @Mock
    private WorkspaceDao workspaceDAO;

    @Test
    void getCurrentWorkspaces() {
        Workspace workspace = Workspace.builder().typeId(1L)
                .startDate(LocalDateTime.of(2024, 6, 23, 12, 30))
                .endDate(LocalDateTime.of(2024, 6, 23, 15, 30))
                .userId(1L)
                .build();
        List<Workspace> singletonWorkspaces = Collections.singletonList(workspace);
        when(workspaceDAO.findAll()).thenReturn(singletonWorkspaces);

        List<Workspace> result = workspaceService.getCurrentWorkspaces(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(workspace, result.get(0));
    }

    @Test
    void submitWorkspace_SUCCESS() {
        Workspace workspace = Workspace.builder().typeId(1L)
                .startDate(LocalDateTime.of(2024, 6, 23, 12, 30))
                .endDate(LocalDateTime.of(2024, 6, 23, 15, 30))
                .build();

        User mockUser = User.builder().login("testUser").build();
        mockUser.setId(1L);
        WorkspaceType workspaceType = WorkspaceType.builder().typeName("workspaceType").build();
        workspaceType.setId(1L);


        when(workspaceDAO.findById(1L)).thenReturn(Optional.ofNullable(workspace));

        assertDoesNotThrow(() -> workspaceService.submitWorkspace(1L, 1L, 1L));

    }

    @Test
    void submitWorkspaceType_Error() {

        Long workspaceId = 1L;
        NoSuchWorkspaceException exception = assertThrows(NoSuchWorkspaceException.class,
                () -> workspaceService.submitWorkspace(100L, 2L, workspaceId));

        assertEquals("Workspace with id:" + workspaceId + " doesn't exist", exception.getLocalizedMessage());
        verify(workspaceDAO, never()).save(any());
    }

    @Test
    void SubmitWorkspace_DuplicateException() {

        Workspace workspace = Workspace.builder().typeId(1L)
                .startDate(LocalDateTime.of(2024, 6, 23, 12, 30))
                .endDate(LocalDateTime.of(2024, 6, 23, 15, 30))
                .userId(1L)
                .build();
        when(workspaceDAO.findById(1L)).thenReturn(Optional.of(workspace));

        DuplicateException exception = assertThrows(DuplicateException.class,
                () -> workspaceService.submitWorkspace(1L, 1L, 1L));

        assertEquals("workspace " + workspace + " booked", exception.getMessage());
        verify(workspaceDAO, never()).save(any());

    }

    @Test
    void getWorkspaceByDate() {
        Long userId = -1L;

        User testUser = User.builder()
                .login("test")
                .build();
        testUser.setId(userId);
        Workspace workspace1 = Workspace.builder().typeId(1L)
                .startDate(LocalDateTime.of(2024, 6, 23, 12, 30))
                .endDate(LocalDateTime.of(2024, 6, 23, 15, 30))
                .build();
        Workspace workspace2 = Workspace.builder().typeId(1L)
                .startDate(LocalDateTime.of(2024, 6, 23, 16, 30))
                .endDate(LocalDateTime.of(2024, 6, 23, 18, 30))
                .build();
        Workspace workspace3 = Workspace.builder().typeId(0L)
                .startDate(LocalDateTime.of(2024, 6, 23, 13, 30))
                .endDate(LocalDateTime.of(2024, 6, 23, 15, 30))
                .build();

        when(workspaceDAO.findAll()).thenReturn(Arrays.asList(
                workspace1,
                workspace2,
                workspace3
        ));

        List<Workspace> result = workspaceService.getWorkspacesByDate(2024, 6, 23);

        assertEquals(3, result.size());
    }

    @Test
    void cancelBook() {
        Workspace workspace = Workspace.builder().typeId(1L)
                .startDate(LocalDateTime.of(2024, 6, 23, 12, 30))
                .endDate(LocalDateTime.of(2024, 6, 23, 15, 30))
                .userId(1L)
                .build();
        workspace.setId(1L);
        when(workspaceService.getCurrentWorkspaces(1L)).thenReturn(List.of(workspace));
        workspaceService.cancelBook(1L, workspace);
        assertNull(workspace.getUserId());
    }
}
