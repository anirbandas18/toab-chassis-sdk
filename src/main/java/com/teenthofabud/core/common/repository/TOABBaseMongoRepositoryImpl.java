package com.teenthofabud.core.common.repository;

import com.teenthofabud.core.common.error.TOABErrorCode;
import com.teenthofabud.core.common.error.TOABSystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;

import java.io.Serializable;
import java.util.List;

@Slf4j
public class TOABBaseMongoRepositoryImpl<T, I extends Serializable> extends SimpleMongoRepository<T, I> implements TOABBaseMongoRepository<T, I> {

    private MongoOperations mongoOperations;
    private MongoEntityInformation entityInformation;

    public TOABBaseMongoRepositoryImpl(MongoEntityInformation<T, I> metadata, MongoOperations mongoOperations) {
        super(metadata, mongoOperations);
        this.entityInformation = metadata;
        this.mongoOperations = mongoOperations;
    }

    @Override
    public List<T> query(Query query) {
        if(query == null) {
            log.debug("Query can't be null");
            throw new TOABSystemException(TOABErrorCode.SYSTEM_INTERNAL_ERROR, "Query can't be null");
        }
        log.debug("Executing dynamic query: {}", query.toString());
        return mongoOperations.find(query, entityInformation.getJavaType(), entityInformation.getCollectionName());
    }
}
