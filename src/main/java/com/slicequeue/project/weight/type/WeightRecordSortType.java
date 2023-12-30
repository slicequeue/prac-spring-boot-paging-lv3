package com.slicequeue.project.weight.type;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.OrderSpecifier.NullHandling;
import com.querydsl.core.types.dsl.Expressions;
import com.slicequeue.project.common.base.BaseSortableType;
import com.slicequeue.project.statistic.entity.QWeightStatistic;
import lombok.Getter;
import org.springframework.data.domain.Sort;

import java.util.Arrays;

import static com.slicequeue.project.statistic.entity.QWeightStatistic.weightStatistic;
import static com.slicequeue.project.weight.entity.QWeightRecord.weightRecord;

@Getter
public enum WeightRecordSortType {

    RECORD_ID("recordId", "id", weightRecord.id),
    WEIGHT("weight", "weight", weightRecord.weight),
    CREATED_AT("createdAt", "createdAt", weightRecord.createdAt),
    UPDATED_AT("updatedAt", "updatedAt", weightRecord.updatedAt),
    TOTAl_AVG_WEIGHT("totalAvgWeight", "totalAvgWeight", weightStatistic.totalAvgWeight)

    ;

    final String sortingDtoPath;
    final String sortingColumnPath;
    final Expression<?> sortingColumnPathExpression;
    final NullHandling nullHandling;

    WeightRecordSortType(String sortingDtoPath, String sortingColumnPath, Expression<?> sortingColumnPathExpression) {
        this.sortingDtoPath = sortingDtoPath;
        this.sortingColumnPath = sortingColumnPath;
        this.sortingColumnPathExpression = sortingColumnPathExpression;
        this.nullHandling = NullHandling.NullsLast;
    }

    public static WeightRecordSortType convert(Sort.Order order) {
        String property = order.getProperty();
        return Arrays.stream(values())
                .filter(each -> each.sortingDtoPath.equals(property))
                .findFirst()
                .orElse(null);
    }
}
