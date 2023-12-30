package com.slicequeue.project.weight.entity;

import com.slicequeue.project.common.type.WeightUnit;
import com.slicequeue.project.common.base.BaseTimeSoftDeleteEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static com.slicequeue.project.common.Constants.POSTFIX_NOTNULL_MESSAGE;

@Entity
@Table(name = "weight_record")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WeightRecord extends BaseTimeSoftDeleteEntity {

    @Comment("몸무게 기록 일련번호")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("몸무게 값")
    @NotNull(message = "weight" + POSTFIX_NOTNULL_MESSAGE)
    @Column(name = "weight", nullable = false)
    private Float weight;

    @Comment("몸무게 값 단위")
    @NotNull(message = "unit" + POSTFIX_NOTNULL_MESSAGE)
    @Enumerated(EnumType.STRING)
    @Column(name = "weight_unit", nullable = false)
    private WeightUnit unit;

    @Comment("몸무게 기록에 대한 메모")
    @Column(name = "memo")
    private String memo;

    @Comment("몸무게 기록 사용자 일련번호")
    @NotNull(message = "userId" + POSTFIX_NOTNULL_MESSAGE)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @BatchSize(size = 1000) // where in 절로 이 크기 만큼 가져옴
    @OneToMany(mappedBy = "weightRecord", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WeightRecordComment> comments = new ArrayList<>();

    @BatchSize(size = 1000) // where in 절로 이 크기 만큼 가져옴
    @OneToMany(mappedBy = "weightRecord", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WeightImageRecord> imageRecords = new ArrayList<>();

    @Builder
    public WeightRecord(Long id, Float weight, WeightUnit unit, String memo, Long userId, List<WeightRecordComment> comments, List<WeightImageRecord> imageRecords) {
        this.id = id;
        this.weight = weight;
        this.unit = unit;
        this.memo = memo;
        this.userId = userId;
        this.comments = comments;
        this.imageRecords = imageRecords;
    }

    public void changeComments(List<WeightRecordComment> comments) {
        this.comments.clear();
        this.comments.addAll(comments);
    }

    public void changeImageRecords(List<WeightImageRecord> imageRecords) {
        this.imageRecords.clear();
        this.imageRecords.addAll(imageRecords);
    }

}
