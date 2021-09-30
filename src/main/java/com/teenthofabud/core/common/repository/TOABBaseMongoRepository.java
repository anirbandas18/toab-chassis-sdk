package com.teenthofabud.core.common.repository;


import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public interface TOABBaseMongoRepository<T, I extends Serializable> extends MongoRepository<T, I>, QueryByExampleExecutor<T> {

    public List<T> findAll(Query query);

}
