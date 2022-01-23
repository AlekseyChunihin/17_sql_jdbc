package com.nixsolutions.ppp.dao;

import com.nixsolutions.ppp.entity.User;

import java.util.List;

public interface Dao<E> {

    void create(E Entity);

    void update(E Entity);

    void remove(User user);

    List<User> findAll();

    User findById(Long id);
}
