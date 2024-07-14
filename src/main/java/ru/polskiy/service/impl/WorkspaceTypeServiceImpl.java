package ru.polskiy.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.polskiy.dao.WorkspaceTypeDao;
import ru.polskiy.dto.WorkspaceTypeRequest;
import ru.polskiy.model.entity.WorkspaceType;
import ru.polskiy.service.WorkspaceTypeService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkspaceTypeServiceImpl implements WorkspaceTypeService {

    private final WorkspaceTypeDao workspaceTypeDAO;

    @Override
    public List<WorkspaceType> showAvailableWorkspaceTypes() {
        return workspaceTypeDAO.findAll();
    }

    @Override
    public WorkspaceType save(WorkspaceTypeRequest request) {
        WorkspaceType workspaceType = WorkspaceType.builder().typeName(request.typeName())
                .build();

        return workspaceTypeDAO.save(workspaceType);
    }
}
