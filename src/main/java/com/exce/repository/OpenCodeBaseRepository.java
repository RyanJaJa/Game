package com.exce.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.math.BigInteger;
import java.util.List;

@NoRepositoryBean
public interface OpenCodeBaseRepository<T> extends CrudRepository<T, BigInteger> {

    T findById(BigInteger id);

    List<T> findByExpectIn(List<String> except);
}
