package com.slicequeue.project.weight.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class WeightRecordResponse {

    private Long recordId;

    private float weight;

    private String unit;

    private String memo;

    private Long userId;

    private float totalAvgWeight;

    private float total;

    private List<String> imageUrls;

    private List<CommentSummary> comments;

    @Data
    @Builder
    public static class CommentSummary {

        private String comments;

        private Long userId;
    }
}
