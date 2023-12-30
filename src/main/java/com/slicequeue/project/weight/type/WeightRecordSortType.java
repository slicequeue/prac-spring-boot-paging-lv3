package com.slicequeue.project.weight.type;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.OrderSpecifier.NullHandling;
import com.querydsl.core.types.dsl.Expressions;
import com.slicequeue.project.weight.entity.QWeightRecord;
import com.slicequeue.project.weight.entity.WeightRecord;
import lombok.Getter;

import static com.slicequeue.project.weight.entity.QWeightRecord.weightRecord;

@Getter
public enum WeightRecordSortType {

    RECORD_ID("recordId", "id", weightRecord.id),
    WEIGHT("weight", "weight", weightRecord.weight),
    CREATED_AT("createdAt", "createdAt", weightRecord.createdAt),
    UPDATED_AT("updatedAt", "updatedAt", weightRecord.updatedAt),
    TOTAl_AVG_WEIGHT("totalAvgWeight", "totalAvgWeight", Expressions.stringPath("totalAvgWeight"))

    ;

    String sortingDtoPath;
    String sortingColumnPath;
    Expression<?> sortingColumnpathExpression;
    NullHandling nullHandling;

    WeightRecordSortType(String sortingDtoPath, String sortingColumnPath, Expression<?> sortingColumnpathExpression) {
        this.sortingDtoPath = sortingDtoPath;
        this.sortingColumnPath = sortingColumnPath;
        this.sortingColumnpathExpression = sortingColumnpathExpression;
        this.nullHandling = NullHandling.NullsLast;
    }
}
