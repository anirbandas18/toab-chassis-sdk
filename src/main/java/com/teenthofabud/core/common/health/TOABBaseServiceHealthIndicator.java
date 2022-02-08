package com.teenthofabud.core.common.health;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Slf4j
public abstract class TOABBaseServiceHealthIndicator implements HealthIndicator {

    public abstract String getServiceName();

    public abstract Status getServiceStatus() throws FeignException;

    public Health determineHealthOfService() {
        String key = this.getServiceName();
        Status status = Status.DOWN;
        String value = "Unavailable";
        try {
            Status serviceStatus = this.getServiceStatus();
            if(serviceStatus.equals(Status.UP)) {
                status = Status.UP;
                value = "Available";
            }
        } catch (FeignException e) {
            log.error("Unable to query health of " + key, e);
        }
        Health health = Health.status(status).withDetail(key, value).build();
        return health;
    }

    public String selectStatus() {
        List<Status> collection = Arrays.asList(Status.UP, Status.DOWN);
        Collections.shuffle(collection, new Random(System.currentTimeMillis()));
        Status selected = collection.get(0);
        return selected.getCode();
    }

}
