package com.slicequeue.project.weight.repository.query;

import com.slicequeue.project.common.dto.TimeRangeRequest;
import com.slicequeue.project.weight.dto.WeightRecordResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WeightRecordQueryRepositoryCustom {

    Page<WeightRecordResponse> findPageWeightRecordResponses(
            Long userId, TimeRangeRequest timeRangeRequest, Pageable pageable);

}
