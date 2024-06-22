package ru.polskiy.dao.impl;

import ru.polskiy.dao.WorkspaceDAO;
import ru.polskiy.exception.NoSuchWorkspaceException;
import ru.polskiy.model.entity.Workspace;

import java.rmi.NoSuchObjectException;
import java.util.*;

public class WorkspaceDAOimpl implements WorkspaceDAO {

    Long id =1L;

    Map<Long, Workspace> workSpaces=new HashMap<>();

    @Override
    public Optional<Workspace> findById(Long id) {
        return Optional.ofNullable(workSpaces.get(id));
    }

    @Override
    public List<Workspace> findAll() {
        return new ArrayList<>(workSpaces.values());
    }

    @Override
    public Workspace save(Workspace entity) {
        entity.setId(id++);
        workSpaces.put(entity.getId(),entity);
        return workSpaces.get(entity.getId());
    }

    @Override
    public void delete(Workspace workspace) {
        if(workSpaces.containsValue(workspace)){
            workSpaces.remove(workspace.getId());
        }else{
            throw new NoSuchWorkspaceException(workspace.toString());
        }
    }

    @Override
    public Workspace update(Workspace workspace) {
        Long userId = workspace.getId();
        if(workSpaces.containsKey(userId)){
            workSpaces.put(userId,workspace);
            return workSpaces.get(userId);
        }else {
            throw new IllegalArgumentException("Workspace with id:"+userId+" doesn't exist");
        }
    }
}
