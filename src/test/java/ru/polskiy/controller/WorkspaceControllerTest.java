package ru.polskiy.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.polskiy.controllers.WorkspaceController;
import ru.polskiy.dao.UserDao;
import ru.polskiy.model.entity.User;
import ru.polskiy.model.entity.Workspace;
import ru.polskiy.service.WorkspaceService;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class WorkspaceControllerTest {

    @Mock
    private WorkspaceService workspaceService;

    @Mock
    private UserDao userDao;

    @InjectMocks
    private WorkspaceController workspaceController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(workspaceController).build();

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void testGetCurrentWorkspaces() throws Exception {
        when(userDao.findByLogin(any())).thenReturn(Optional.of(new User()));
        when(workspaceService.getCurrentWorkspaces(anyLong())).thenReturn(Arrays.asList(new Workspace()));

        mockMvc.perform(get("/api/v1/workspace/current")
                        .param("login", "testLogin")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testSubmitWorkspace() throws Exception {
        when(userDao.findByLogin(any())).thenReturn(Optional.of(new User()));
        doNothing().when(workspaceService).submitWorkspace(any(), any(), anyLong());

        mockMvc.perform(post("/api/v1/workspace/submit")
                        .param("login", "testLogin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"counterNumber\": \"123\", \"meterTypeId\": \"1\"}"))
                .andExpect(status().isOk());
    }
}
