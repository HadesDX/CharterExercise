package com.diego.charterexercise.strategy;

public class PointsBetweenRange implements PointsStrategy {
    private final long startRange;
    private final long endRange;
    private final long points;
    private final long maximumPoints;

    public PointsBetweenRange(long startRange, long endRange, long points) {
        this.startRange = startRange;
        this.endRange = endRange;
        this.points = points;
        this.maximumPoints = points * (endRange - startRange);
    }

    @Override
    public long awardPoints(final long amount) {
        if (amount < startRange) return ZERO_POINTS_AWARDED;
        if (amount >= endRange) return maximumPoints;

        return points * (amount - startRange + 1);
    }
}
