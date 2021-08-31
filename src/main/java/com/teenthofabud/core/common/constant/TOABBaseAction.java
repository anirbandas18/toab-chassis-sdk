package com.teenthofabud.core.common.constant;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public enum TOABBaseAction {

    CREATE("create"),
    READ("read"),
    UPDATE("update"),
    DELETE("delete");

    private String name;

    private TOABBaseAction(String name) {
        this.name = name;
    }

}
