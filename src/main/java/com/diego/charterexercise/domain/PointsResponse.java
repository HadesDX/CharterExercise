package com.diego.charterexercise.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class PointsResponse {
    @JsonProperty
    Map<String, Long> pointsByMonths;
    @JsonProperty
    long totalPoints;

    public PointsResponse(Map<String, Long> pointsByMonths, long totalPoints) {
        this.pointsByMonths = pointsByMonths;
        this.totalPoints = totalPoints;
    }

}
