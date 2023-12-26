package com.slicequeue.project.entity;

import com.slicequeue.project.common.type.WeightUnit;
import com.slicequeue.project.entity.base.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
public class WeightRecord extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("몸무게 기록 일련번호")
    private Long id;

    @NotNull(message = "weight" + POSTFIX_NOTNULL_MESSAGE)
    @Column(name = "weight", nullable = false)
    private Float weight;

    @NotNull(message = "unit" + POSTFIX_NOTNULL_MESSAGE)
    @Enumerated(EnumType.STRING)
    @Column(name = "weight_unit", nullable = false)
    private WeightUnit unit;

    @Column(name = "memo")
    private String memo;

    @NotNull(message = "userId" + POSTFIX_NOTNULL_MESSAGE)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @OneToMany(mappedBy = "weightRecord", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WeightRecordComment> comments = new ArrayList<>();

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
