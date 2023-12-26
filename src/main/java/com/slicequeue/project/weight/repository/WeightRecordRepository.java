package com.slicequeue.project.weight.repository;

import com.slicequeue.project.weight.entity.WeightRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeightRecordRepository extends JpaRepository<WeightRecord, Long> {
}
