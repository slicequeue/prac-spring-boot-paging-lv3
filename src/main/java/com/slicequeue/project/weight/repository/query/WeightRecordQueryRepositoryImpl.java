package com.slicequeue.project.weight.repository.query;

import com.slicequeue.project.common.dto.TimeRangeRequest;
import com.slicequeue.project.weight.dto.WeightRecordResponse;
import com.slicequeue.project.weight.entity.WeightRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class WeightRecordQueryRepositoryImpl extends QuerydslRepositorySupport implements WeightRecordQueryRepositoryCustom {

    public WeightRecordQueryRepositoryImpl(Class<?> domainClass) {
        super(WeightRecord.class);
    }

    @Override
    public Page<WeightRecordResponse> findPageWeightRecordReponses(
            Long userId, TimeRangeRequest timeRangeRequest, Pageable pageable) {
        // TODO 구현
        return null;
    }
}
