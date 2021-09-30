package com.teenthofabud.core.common.factory;

import com.teenthofabud.core.common.error.TOABErrorCode;
import com.teenthofabud.core.common.error.TOABSystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Map;

@Slf4j
public class TOABRepositoryDynamicQueryBuilderFactory {

    public Query createQueryForFieldContainingValueConditions(Map<String, String> parameterMap) {
        if(parameterMap == null || parameterMap.isEmpty()) {
            log.debug("Parameters can't be null/empty");
            throw new TOABSystemException(TOABErrorCode.SYSTEM_IO_FAILURE, "Parameters name can't be null/empty");
        }
        Query query = new Query();
        for(String key : parameterMap.keySet()) {
            StringBuffer regex = new StringBuffer();
            regex.append(".*");
            regex.append(parameterMap.get(key));
            regex.append(".*");
            Criteria criteria = new Criteria(key);
            criteria.regex(regex.toString());
            query.addCriteria(criteria);
        }
        return  query;
    }

}
