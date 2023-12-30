package com.slicequeue.project.weight.dto;

import com.slicequeue.project.statistic.entity.WeightStatistic;
import com.slicequeue.project.user.entity.User;
import com.slicequeue.project.weight.entity.WeightImageRecord;
import com.slicequeue.project.weight.entity.WeightRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class WeightRecordResponse {

    private Long recordId;

    private float weight;

    private String unit;

    private String memo;

    private Long userId;

    private String userName;

    private float totalAvgWeight;

    private String totalGrade; // TODO case 이용하여 프로젝션 넘기기, 정렬에도 사용

    private List<String> imageUrls;

    private List<CommentSummary> comments;

    private Instant createdAt;

    private Instant updatedAt;

    @Data
    @Builder
    public static class CommentSummary {

        private String comments;

        private Long userId;

    }

    public WeightRecordResponse(WeightRecord weightRecord, User user, WeightStatistic weightStatistic) {
        this.recordId = weightRecord.getId();
        this.weight = weightRecord.getWeight();
        this.unit = weightRecord.getUnit().getValue();
        this.memo = weightRecord.getMemo();
        this.userId = user.getId();
        this.userName = user.getName();
        this.totalAvgWeight = weightStatistic.getTotalAvgWeight();
//        this.totalGrade
        this.imageUrls = weightRecord.getImageRecords().stream()
                .map(WeightImageRecord::getImageUrl)
                .toList();
        this.comments = weightRecord.getComments().stream()
                .map(each -> CommentSummary.builder()
                        .userId(each.getUserId())
                        .comments(each.getComment())
                        .build())
                .toList();
        this.createdAt = weightRecord.getCreatedAt();
        this.updatedAt = weightRecord.getUpdatedAt();
    }
}
