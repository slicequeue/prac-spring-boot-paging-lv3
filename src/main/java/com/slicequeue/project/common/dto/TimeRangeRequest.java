package com.slicequeue.project.common.dto;

import lombok.Getter;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Getter
public class TimeRangeRequest {

    private Instant startAt;

    private Instant endAt;

    private final Duration DEFAULT_DURATION_TWO_WEEKS = Duration.ofDays(14);

    public TimeRangeRequest(
            Instant startAt, Instant from,
            Instant endAt, Instant to
    ) {
        this.startAt = startAt != null ? startAt : from;
        this.endAt = endAt != null ? endAt : to;

        // 끝 일시 NULL 일 경우 현재 시간으로 설정
        if (this.endAt == null) {
            this.endAt = Instant.now();
        }

        // 시작 일시 NULL 일 경우 끝 날짜 기준으로 2주 전으로 세팅
        if (this.startAt == null) {
            this.startAt = this.endAt.minus(DEFAULT_DURATION_TWO_WEEKS);
        }

        // 순서 체크
        if (!this.startAt.isBefore(this.endAt)) {
            Instant temp = this.endAt;
            this.endAt = this.startAt;
            this.startAt = temp;
        }

    }
}
