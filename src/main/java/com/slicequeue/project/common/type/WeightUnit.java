package com.slicequeue.project.common.type;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum WeightUnit {
    KG("kg");

    @Getter
    private final String value;

}
