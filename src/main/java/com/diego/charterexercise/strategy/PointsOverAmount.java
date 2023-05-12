package com.diego.charterexercise.strategy;

public class PointsOverAmount implements PointsStrategy {

    private final long minimumAmount;
    private final long points;

    public PointsOverAmount(long minimumAmount, long points) {
        this.minimumAmount = minimumAmount;
        this.points = points;
    }

    @Override
    public long awardPoints(final long amount) {
        if (amount < minimumAmount) return ZERO_POINTS_AWARDED;

        return points * (amount - minimumAmount);
    }
}
