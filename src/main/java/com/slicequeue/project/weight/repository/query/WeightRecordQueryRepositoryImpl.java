package com.slicequeue.project.weight.repository.query;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.slicequeue.project.common.dto.TimeRangeRequest;
import com.slicequeue.project.user.entity.QUser;
import com.slicequeue.project.weight.dto.WeightRecordResponse;
import com.slicequeue.project.weight.entity.WeightRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;

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
            Long userId, TimeRangeRequest timeRangeRequest, Pageable pageable) { // TODO 필터링 조건 추가
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
                        weightRecord.createdAt.between(timeRangeRequest.getStartAt(), timeRangeRequest.getEndAt())
                )
                .orderBy()
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                // 응답 dto 클래스 프로젝션!
                .select(// 생성자 모양에 맞게 매게변수 전달
                        Projections.constructor(WeightRecordResponse.class, weightRecord, user, weightStatistic))
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
}
