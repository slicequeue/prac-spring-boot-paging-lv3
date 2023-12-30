package com.slicequeue.project.common.base;


import com.querydsl.core.types.Expression;
import com.querydsl.core.types.OrderSpecifier;

public interface BaseSortableType {
    // TODO 정렬 전용 열거형 타입에 있어서 공통으로 처리할 수 있도록!
    // TODO 정렬 OrderSpecifier 만드는 것에 QueryDslUtil 클래스 만들 수 있도록 하기
    String getSortingDtoPath();
    String getSortingColumnPath();
    Expression<?> getSortingColumnPathExpression();
    OrderSpecifier.NullHandling getNullHandling();
}
