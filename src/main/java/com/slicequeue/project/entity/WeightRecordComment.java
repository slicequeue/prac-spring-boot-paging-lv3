package com.slicequeue.project.entity;

import com.slicequeue.project.common.Constants;
import com.slicequeue.project.entity.base.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static com.slicequeue.project.common.Constants.POSTFIX_NOTNULL_MESSAGE;

@Entity
@Table(name = "weight_record_comment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WeightRecordComment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("몸무게 기록 댓글 일련번호")
    private Long id;

    @NotNull(message = "comment" + POSTFIX_NOTNULL_MESSAGE)
    @Column(name = "comment", nullable = false)
    private String comment;

    @NotNull(message = "userId" + POSTFIX_NOTNULL_MESSAGE)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Setter
    @NotNull(message = "weightRecord" + POSTFIX_NOTNULL_MESSAGE)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "weight_record_id", referencedColumnName = "id")
    private WeightRecord weightRecord;

    @Builder
    public WeightRecordComment(Long id, String comment, Long userId, WeightRecord weightRecord) {
        this.id = id;
        this.comment = comment;
        this.userId = userId;
        this.weightRecord = weightRecord;
    }

}
