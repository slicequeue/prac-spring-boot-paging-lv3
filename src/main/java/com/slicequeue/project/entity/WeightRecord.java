package com.slicequeue.project.entity;

import com.slicequeue.project.common.Constants;
import com.slicequeue.project.common.type.WeightUnit;
import com.slicequeue.project.entity.base.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "weight_record")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WeightRecord extends BaseTimeEntity {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("몸무게 일련번호")
    private Long id;

    @NotNull(message = "weight" + Constants.POSTFIX_NOTNULL_MESSAGE)
    @Column(name = "weight", nullable = false)
    private Float weight;

    @NotNull(message = "unit" + Constants.POSTFIX_NOTNULL_MESSAGE)
    @Enumerated(EnumType.STRING)
    @Column(name = "weight_unit", nullable = false)
    private WeightUnit unit;

    @Column(name = "memo")
    private String memo;

    @NotNull(message = "userId" + Constants.POSTFIX_NOTNULL_MESSAGE)
    @Column(name = "user_id")
    private Long userId;

}
