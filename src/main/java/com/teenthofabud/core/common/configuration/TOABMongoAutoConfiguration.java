package com.teenthofabud.core.common.configuration;

import com.teenthofabud.core.common.factory.TOABRepositoryDynamicQueryBuilderFactory;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Component
@AutoConfigureOrder(Ordered.LOWEST_PRECEDENCE)
public class TOABMongoAutoConfiguration {

    @Bean
    public TOABRepositoryDynamicQueryBuilderFactory dynamicQueryBuilderFactory() {
            return new TOABRepositoryDynamicQueryBuilderFactory();
    }

}
