package com.teenthofabud.core.common.constant;

import lombok.Getter;
import lombok.ToString;

import java.util.Optional;

@Getter
@ToString
public enum TOABCascadeLevel {

    ZERO("0"),
    ONE("1"),
    TWO("2");

    private String levelCode;

    private TOABCascadeLevel(String levelCode) {
        this.levelCode = levelCode;
    }

    public static Optional<TOABCascadeLevel> findByLevelCode(String levelCode) {
        Optional<TOABCascadeLevel> cascadeLevel = Optional.empty();
        for(TOABCascadeLevel tcl : TOABCascadeLevel.values()) {
            if(tcl.getLevelCode().compareTo(levelCode) == 0) {
                cascadeLevel = Optional.of(tcl);
                break;
            }
        }
        return cascadeLevel;
    }

}
