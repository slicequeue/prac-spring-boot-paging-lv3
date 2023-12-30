package com.slicequeue.project.weight.controller;

import com.slicequeue.project.common.dto.TimeRangeRequest;
import com.slicequeue.project.weight.dto.WeightRecordResponse;
import com.slicequeue.project.weight.repository.query.WeightRecordQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class WeightRecordController {

    private final WeightRecordQueryRepository weightRecordQueryRepository;

    @RequestMapping(method = RequestMethod.GET, path = "/api/v1/records")
    public Page<WeightRecordResponse> getRecordsV1(
            @RequestParam Long userId,
            @ModelAttribute TimeRangeRequest timeRangeRequest,
            @PageableDefault Pageable pageable
    ) {
        return weightRecordQueryRepository.findPageWeightRecordResponses(userId, timeRangeRequest, pageable);
    }

}
