package ru.polskiy.dao;

import java.util.List;
import java.util.Optional;

public interface MainDAO <I,T>{

    Optional<T> findById(I id);

    List<T> findAll();

    T save(T entity);
}
