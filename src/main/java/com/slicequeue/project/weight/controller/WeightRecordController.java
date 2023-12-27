package com.slicequeue.project.weight.controller;

import com.slicequeue.project.weight.dto.WeightRecordResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WeightRecordController {

    @RequestMapping(method = RequestMethod.GET, path = "/api/v1/records")
    public Page<WeightRecordResponse> getRecordsV1() {
        // TODO 구현
        return null;
    }

}
