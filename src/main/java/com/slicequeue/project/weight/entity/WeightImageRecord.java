package com.slicequeue.project.weight.entity;

import com.slicequeue.project.common.base.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static com.slicequeue.project.common.Constants.POSTFIX_NOTNULL_MESSAGE;

@Entity
@Table(name = "weight_image_record")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WeightImageRecord extends BaseTimeEntity {

    @Comment("몸무게 기록 사진 일련번호")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("사진 URL")
    @NotNull(message = "imageUrl" + POSTFIX_NOTNULL_MESSAGE)
    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Setter
    @NotNull(message = "weightRecord" + POSTFIX_NOTNULL_MESSAGE)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "weight_record_id", referencedColumnName = "id")
    private WeightRecord weightRecord;

    @Builder
    public WeightImageRecord(Long id, String imageUrl, WeightRecord weightRecord) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.weightRecord = weightRecord;
    }

}
