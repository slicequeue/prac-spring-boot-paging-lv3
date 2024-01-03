package com.slicequeue.project.weight.dto;

import lombok.Getter;

@Getter
public class WeightRecordSearchCondition {

  private String totalGrade;

  private Boolean isWarning;

  public WeightRecordSearchCondition(String totalGrade, Boolean isWarning) {
    this.totalGrade = totalGrade;
    this.isWarning = isWarning;
  }
}
