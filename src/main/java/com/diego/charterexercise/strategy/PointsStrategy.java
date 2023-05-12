package com.diego.charterexercise.strategy;

public interface PointsStrategy {
    public static final int ZERO_POINTS_AWARDED = 0;

    public long awardPoints(long amount);
}
