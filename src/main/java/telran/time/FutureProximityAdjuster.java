package telran.time;

import java.util.Arrays;

public class FutureProximityAdjuster implements TimePointAdjuster {
    TimePoint[] timePoints;

    public FutureProximityAdjuster(TimePoint[] timePoints) {
        TimePoint[] newTimePoints = Arrays.copyOf(timePoints, timePoints.length);
        Arrays.sort(newTimePoints);
        this.timePoints = newTimePoints;

    }

    @Override
    public TimePoint adjust(TimePoint timePoint) {
        int searchRes = Arrays.binarySearch(timePoints, timePoint);
        int insertPlace = (searchRes < 0 ? -searchRes - 1 : searchRes);

        while (insertPlace < timePoints.length && timePoints[insertPlace].equals(timePoint)) {
            insertPlace++;
        }

        return (insertPlace < timePoints.length
                && timePoints[insertPlace].getTimeUnit().equals(timePoint.getTimeUnit()))
                        ? timePoints[insertPlace]
                        : null;
    }

}
