package com.biwise.audit.repository;

import com.biwise.audit.domain.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface Dao<T, ID> {

    Optional<T> get(ID id);

    List<T> findAll();

    T save(T t);

    T update(T t);

    void delete(T t);
}
