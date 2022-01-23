package com.nixsolutions.ppp.dao;

import com.nixsolutions.ppp.entity.User;

import java.util.List;

public interface Dao<E> {

    void create(E Entity);

    void update(E Entity);

    void remove(E Entity);

    List<E> findAll();

    E findById(Long id);
}
