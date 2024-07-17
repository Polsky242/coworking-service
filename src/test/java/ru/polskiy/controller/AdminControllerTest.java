package ru.polskiy.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.polskiy.controllers.AdminController;
import ru.polskiy.dto.WorkspaceTypeRequest;
import ru.polskiy.model.entity.User;
import ru.polskiy.model.entity.WorkspaceType;
import ru.polskiy.service.UserService;
import ru.polskiy.service.WorkspaceTypeService;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdminControllerTest {
    @InjectMocks
    private AdminController adminController;
    @Mock
    private UserService userService;
    @Mock
    private WorkspaceTypeService workspaceTypeService;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        try (AutoCloseable closeable = MockitoAnnotations.openMocks(this)) {
            mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
        }
    }

    @Test
    public void testShowAllUsers() throws Exception {
        List<User> users = Collections.singletonList(new User());
        when(userService.showAll()).thenReturn(users);

        mockMvc.perform(get("api/v1/admin/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddWorkspaceType() throws Exception {
        String workspaceTypeName = "test";
        WorkspaceTypeRequest request = new WorkspaceTypeRequest(workspaceTypeName);
        WorkspaceType workspaceType = new WorkspaceType();
        when(workspaceTypeService.save(request)).thenReturn(workspaceType);

        mockMvc.perform(post("/api/v1/admin/workspace-type/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"" + workspaceTypeName + "\"}"))
                .andExpect(status().isOk());
    }
}
