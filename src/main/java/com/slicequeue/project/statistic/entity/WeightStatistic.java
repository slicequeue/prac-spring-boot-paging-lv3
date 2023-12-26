package com.slicequeue.project.statistic.entity;

import com.slicequeue.project.common.base.BaseTimeSoftDeleteEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static com.slicequeue.project.common.Constants.POSTFIX_NOTNULL_MESSAGE;

@Entity
@Table(name = "weight_statistic")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WeightStatistic extends BaseTimeSoftDeleteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("몸무게 통계 일련번호")
    private Long id;

    @Comment("몸무게 기록 일련 참조 번호")
    @NotNull(message = "weightRecordId" + POSTFIX_NOTNULL_MESSAGE)
    @Column(name = "weight_record_id", nullable = false)
    private Long weightRecordId;

    @NotNull(message = "totalAvgWeight" + POSTFIX_NOTNULL_MESSAGE)
    @Comment("몸무게 누적 평균값")
    @Column(name = "total_avg_weight", nullable = false)
    private Float totalAvgWeight;

    @Comment("몸무게 누적 최대값")
    @NotNull(message = "totalMaxWeight" + POSTFIX_NOTNULL_MESSAGE)
    @Column(name = "total_max_weight", nullable = false)
    private Float totalMaxWeight;

    @Comment("몸무게 누적 최대값")
    @NotNull(message = "totalMinWeight" + POSTFIX_NOTNULL_MESSAGE)
    @Column(name = "total_min_weight", nullable = false)
    private Float totalMinWeight;

    @Builder
    public WeightStatistic(Long id, Long weightRecordId, Float totalAvgWeight, Float totalMaxWeight, Float totalMinWeight) {
        this.id = id;
        this.weightRecordId = weightRecordId;
        this.totalAvgWeight = totalAvgWeight;
        this.totalMaxWeight = totalMaxWeight;
        this.totalMinWeight = totalMinWeight;
    }
}
