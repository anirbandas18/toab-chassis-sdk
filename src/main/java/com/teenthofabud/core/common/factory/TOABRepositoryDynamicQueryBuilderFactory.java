package com.teenthofabud.core.common.factory;

import com.teenthofabud.core.common.data.dto.TOABQueryParameterKey;
import com.teenthofabud.core.common.data.dto.TOABQueryParameterValue;
import com.teenthofabud.core.common.error.TOABErrorCode;
import com.teenthofabud.core.common.error.TOABSystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Map;

@Slf4j
public class TOABRepositoryDynamicQueryBuilderFactory {

    public Query createQueryWithAndConditionsOfFieldContainingValuesLike(Map<String, String> parameterMap) {
        if(parameterMap == null || parameterMap.isEmpty()) {
            log.debug("Parameters can't be null/empty");
            throw new TOABSystemException(TOABErrorCode.SYSTEM_IO_FAILURE, "Parameters name can't be null/empty");
        }
        Criteria criteria = new Criteria();
        for(String key : parameterMap.keySet()) {
            StringBuffer regex = new StringBuffer();
            regex.append(".*");
            regex.append(parameterMap.get(key));
            regex.append(".*");
            criteria.and(key).regex(regex.toString());
        }
        Query query = new Query();
        query.addCriteria(criteria);
        return  query;
    }

    public <T> Query createQueryWithAndConditionsOfFieldWithValues(Map<TOABQueryParameterKey, TOABQueryParameterValue<T>> parameterMap) {
        if(parameterMap == null || parameterMap.isEmpty()) {
            log.debug("Parameters can't be null/empty");
            throw new TOABSystemException(TOABErrorCode.SYSTEM_IO_FAILURE, "Parameters name can't be null/empty");
        }
        Criteria criteria = new Criteria();
        for(TOABQueryParameterKey key : parameterMap.keySet()) {
            TOABQueryParameterValue<T> value = parameterMap.get(key);
            switch (key.getOperatorType()) {
                case EQ:
                    criteria.and(key.getName()).is(value.getData());
                    break;
                case LIKE:
                    StringBuffer regex = new StringBuffer();
                    regex.append(".*");
                    regex.append(value.getData());
                    regex.append(".*");
                    criteria.and(key.getName()).regex(regex.toString());
                    break;
                case IN:
                case NIN:
                case NE:
                case GT:
                case GTE:
                case LT:
                case LTE:
                    throw new UnsupportedOperationException("Operator " + key.getOperatorType() + " not implemented");
            }
        }
        Query query = new Query();
        query.addCriteria(criteria);
        return  query;
    }

}
