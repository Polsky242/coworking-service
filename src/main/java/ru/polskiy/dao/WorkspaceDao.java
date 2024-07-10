package ru.polskiy.dao;


import ru.polskiy.model.entity.Workspace;

import java.util.List;

public interface WorkspaceDao extends MainDao<Long, Workspace> {

    boolean delete(Long id);
    Workspace update(Workspace workspace);

    List<Workspace> findAllByUserId(Long userId);
}
