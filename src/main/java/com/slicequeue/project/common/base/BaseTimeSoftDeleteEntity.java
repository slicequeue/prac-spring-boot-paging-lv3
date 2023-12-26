package com.slicequeue.project.common.base;


import lombok.Getter;
import org.hibernate.annotations.Comment;

import javax.persistence.MappedSuperclass;
import java.time.Instant;

@Getter
@MappedSuperclass
public class BaseTimeSoftDeleteEntity extends BaseTimeEntity {

    @Comment("삭제일시")
    private Instant deletedAt;

    public void changeDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public boolean isDeleted() {
        return this.deletedAt != null;
    }
}
