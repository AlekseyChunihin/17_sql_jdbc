package com.nixsolutions.ppp.dao;

import com.nixsolutions.ppp.entity.User;

import java.util.List;

public interface Dao {

    void create(Object Entity);

    void update(Object Entity);

    void remove(Object Entity);

    List<Object> findAll();

    Object findById(Long id);
}
