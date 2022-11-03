package com.teenthofabud.core.common.repository;

import com.teenthofabud.core.common.error.TOABErrorCode;
import com.teenthofabud.core.common.error.TOABSystemException;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;

import java.io.Serializable;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Slf4j
public class TOABBaseMongoRepositoryImpl<T, I extends Serializable> extends SimpleMongoRepository<T, I> implements TOABBaseMongoRepository<T, I> {

    private MongoOperations mongoOperations;
    private MongoEntityInformation entityInformation;
    private Document classCriteriaDocument;
    private Criteria classCriteria;

    public TOABBaseMongoRepositoryImpl(MongoEntityInformation<T, I> metadata, MongoOperations mongoOperations) {
        super(metadata, mongoOperations);
        this.entityInformation = metadata;
        this.mongoOperations = mongoOperations;

        if (entityInformation.getJavaType().isAnnotationPresent(TypeAlias.class)) {
            classCriteria = where("_class").is(entityInformation.getJavaType().getAnnotation(TypeAlias.class).annotationType());
            classCriteriaDocument = classCriteria.getCriteriaObject();
        } else {
            classCriteriaDocument = new Document();
            classCriteria = null;
        }
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

    @Override
    public List<T> findAll() {
        return classCriteria != null ? mongoOperations.find(new Query().addCriteria(classCriteria),
                entityInformation.getJavaType(),
                entityInformation.getCollectionName())
                : super.findAll();
    }
}
