package com.teenthofabud.core.common.repository;


import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public interface TOABBaseMongoRepository<T, I extends Serializable> {

    public List<T> query(Query query);

}
