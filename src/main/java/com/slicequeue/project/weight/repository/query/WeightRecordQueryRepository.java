package com.slicequeue.project.weight.repository.query;

import com.slicequeue.project.weight.entity.WeightRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeightRecordQueryRepository extends JpaRepository<WeightRecord, Long> {
}
