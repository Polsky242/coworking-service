package ru.polskiy.dao;


import ru.polskiy.model.entity.Workspace;

import java.util.List;

public interface WorkspaceDAO extends MainDAO<Long, Workspace> {

    boolean delete(Long id);
    Workspace update(Workspace workspace);
}
