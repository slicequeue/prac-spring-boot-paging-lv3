package com.slicequeue.project.weight.repository.query;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLQuery;
import com.slicequeue.project.common.dto.TimeRangeRequest;
import com.slicequeue.project.weight.dto.WeightRecordResponse;
import com.slicequeue.project.weight.dto.WeightRecordSearchCondition;
import com.slicequeue.project.weight.entity.WeightRecord;
import com.slicequeue.project.weight.type.WeightRecordSortType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.ArrayList;
import java.util.List;

import static com.slicequeue.project.statistic.entity.QWeightStatistic.weightStatistic;
import static com.slicequeue.project.user.entity.QUser.user;
import static com.slicequeue.project.weight.entity.QWeightRecord.weightRecord;

public class WeightRecordQueryRepositoryImpl
        extends QuerydslRepositorySupport // Querydsl 3.x 버전을 대상으로 만듬  Querydsl 4.x에 나온 JPAQueryFactory 로 시작할 수 없음
        implements WeightRecordQueryRepositoryCustom {

    public WeightRecordQueryRepositoryImpl() {
        super(WeightRecord.class);
    }

    @Override
    public Page<WeightRecordResponse> findPageWeightRecordResponses(
            Long userId, TimeRangeRequest timeRangeRequest,
        WeightRecordSearchCondition searchCondition, Pageable pageable) {
        List<WeightRecordResponse> content = from(weightRecord)
                // 1:1 관계이며 경우 엔티티 fetch join 진행 - 페이징 갯수 결과에 영향을 미치지 않으므로
                .innerJoin(user)
                .on(user.id.eq(weightRecord.userId))    // 연관관계 없는 경우에도 지정 가능
                .fetchJoin()                            // fetchJoin 수행!
                .leftJoin(weightStatistic)
                .on(weightStatistic.weightRecordId.eq(weightRecord.id)) // 연관관계 없는 경우에도 지정 가능
                .fetchJoin()                                            // fetchJoin 수행! 1:1 이므로 중복 제거용 distinct 수행하지 않음
                .where(
                        weightRecord.userId.eq(userId),
                        weightRecord.deletedAt.isNull(),
                        weightRecord.createdAt.between(timeRangeRequest.getStartAt(), timeRangeRequest.getEndAt()),
                        searchCondition(searchCondition)
                )
                .orderBy(getOrderSpecifiers(pageable))  // 정렬 처리
                // 페이징 처리
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                // 응답 dto 클래스 프로젝션!
                .select( // 생성자 모양에 맞게 매게변수 전달
                        Projections.constructor(WeightRecordResponse.class,
                            weightRecord, user, weightStatistic,
                            selectTotalGrade(),
                            selectIsWarning()
                            ))
                .fetch();

        JPQLQuery<WeightRecord> countQuery = from(weightRecord)
                .leftJoin(weightStatistic)
                .on(weightStatistic.weightRecordId.eq(weightRecord.id))
                .where(
                        weightRecord.userId.eq(userId),
                        weightRecord.deletedAt.isNull(),
                        weightRecord.createdAt.between(timeRangeRequest.getStartAt(), timeRangeRequest.getEndAt())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    private static Expression<String> selectTotalGrade() {
        return (new CaseBuilder())
            .when(weightStatistic.totalAvgWeight.lt(40)).then("체중미달")
            .when(weightStatistic.totalAvgWeight.gt(80)).then("과체중")
            .otherwise("정상")
            .as(WeightRecordSortType.TOTAL_GRADE.getSortingColumnPath());
    }

    private static BooleanExpression selectIsWarning() {
        // 이럴때는 그냥 case 하자
        return (new CaseBuilder())
            .when(weightStatistic.totalAvgWeight.lt(40.f).or(weightStatistic.totalAvgWeight.gt(80.f)))
            .then(true)
            .otherwise(false).as(WeightRecordSortType.IS_WARNING.getSortingColumnPath());

//        // Expressions.anyOf 메서드를 사용하여 두 개의 조건을 OR 연산으로 결합하고, 그 결과를 반환
//        // 아래 `잘못된 방법` 으로의 형변환 오류 회피 가능인 줄 알았으나 이것도 오류!
//        return Expressions.anyOf(
//            weightStatistic.totalAvgWeight.lt(40.f),
//            weightStatistic.totalAvgWeight.gt(80.f)
//        ).as(WeightRecordSortType.IS_WARNING.getSortingColumnPath());

        // 잘못된 방법
//        return weightStatistic.totalAvgWeight.gt(80.f)
//                .or(weightStatistic.totalAvgWeight.lt(40.f)) // 이렇게 할 경우 cast 오류가 발생함
//            // 다른방식이 필요
//            .as(WeightRecordSortType.IS_WARNING.getSortingColumnPath());
    }

    private Predicate searchCondition(WeightRecordSearchCondition searchCondition) {
        // coalesce 이용하여 두 개 이상의 검색 조건 중 하나라도 만족하는 레코드를 찾는 데 사용
        // null 인 경우 해당 조건을 넘김
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression booleanExpression = totalGrade(searchCondition.getTotalGrade());
        BooleanExpression warning = selectIsWarning(searchCondition.getIsWarning());
        return builder.andAnyOf(booleanExpression, warning);
    }

    private BooleanExpression totalGrade(String totalGrade) {
        return (totalGrade != null) ? Expressions.stringPath("totalGrade").eq(totalGrade) : null;
    }

    private BooleanExpression selectIsWarning(Boolean isWarning) {
        return (isWarning != null) ? Expressions.booleanPath("isWarning").eq(isWarning) : null;
    }

    private OrderSpecifier<?>[] getOrderSpecifiers(Pageable pageable) {
        Sort sort = pageable.getSort();
        if (sort.isEmpty()) {
            return new OrderSpecifier[] {};
        }
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
        for (Sort.Order order : sort.get().toList()) {
            WeightRecordSortType sortType = WeightRecordSortType.convert(order);
            Order dslOrder = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
            if (sortType != null) {
                orderSpecifiers.add(
                        new OrderSpecifier(dslOrder, sortType.getSortingColumnPathExpression(), sortType.getNullHandling()));
            }
        }
        return orderSpecifiers.toArray(OrderSpecifier[]::new);
    }
}
