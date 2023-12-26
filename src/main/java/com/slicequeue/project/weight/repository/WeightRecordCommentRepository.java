package com.slicequeue.project.weight.repository;

import com.slicequeue.project.weight.entity.WeightRecordComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeightRecordCommentRepository extends JpaRepository<WeightRecordComment, Long> {
}
