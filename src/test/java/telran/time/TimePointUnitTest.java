package telran.time;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class TimePointUnitTest {

    @Test
    public void testCompareTo() {
        // 10 hour < 20 hour
        TimePoint timePoint1 = new TimePoint(10, TimeUnit.HOUR);
        TimePoint timePoint2 = new TimePoint(20, TimeUnit.HOUR);
        assertEquals(-1, timePoint1.compareTo(timePoint2));

        // 10 hour > 20 Seconds
        TimePoint timePoint3 = new TimePoint(10, TimeUnit.HOUR);
        TimePoint timePoint4 = new TimePoint(20, TimeUnit.SECOND);
        assertEquals(1, timePoint3.compareTo(timePoint4));

        // 10 mins < 20 hours
        TimePoint timePoint5 = new TimePoint(10, TimeUnit.MINUTE);
        TimePoint timePoint6 = new TimePoint(20, TimeUnit.HOUR);
        assertEquals(-1, timePoint5.compareTo(timePoint6));

        // 60 sec == 1 min
        TimePoint timePoint7 = new TimePoint(60, TimeUnit.SECOND);
        TimePoint timePoint8 = new TimePoint(1, TimeUnit.MINUTE);
        assertEquals(0, timePoint7.compareTo(timePoint8));
    }

    @Test
    public void testConvert() {
        // 1 hour to 60 mins
        TimePoint timePoint1 = new TimePoint(1, TimeUnit.HOUR);
        TimePoint timePoint1Converted = timePoint1.convert(TimeUnit.MINUTE);
        assertEquals(60, timePoint1Converted.getAmount());
        assertEquals(TimeUnit.MINUTE, timePoint1Converted.getTimeUnit());

        // 10 sec to 10 sec
        TimePoint timePoint2 = new TimePoint(10, TimeUnit.SECOND);
        TimePoint timePoint2Converted = timePoint2.convert(TimeUnit.SECOND);
        assertEquals(10, timePoint2Converted.getAmount());
        assertEquals(TimeUnit.SECOND, timePoint2Converted.getTimeUnit());

        // 3600 sec to 1 hour
        TimePoint timePoint3 = new TimePoint(3600, TimeUnit.SECOND);
        TimePoint timePoint3Converted = timePoint3.convert(TimeUnit.HOUR);
        assertEquals(1, timePoint3Converted.getAmount());
        assertEquals(TimeUnit.HOUR, timePoint3Converted.getTimeUnit());
    }

    @Test
    public void testEquals() {
        // equals to itself
        TimePoint timePoint1 = new TimePoint(60, TimeUnit.SECOND);
        assertTrue(timePoint1.equals(timePoint1));

        // same points
        TimePoint timePoint2 = new TimePoint(3, TimeUnit.HOUR);
        TimePoint timePoint3 = new TimePoint(3, TimeUnit.HOUR);
        assertTrue(timePoint2.equals(timePoint3));

        // not equals
        TimePoint timePoint4 = new TimePoint(15, TimeUnit.MINUTE);
        TimePoint timePoint5 = new TimePoint(4, TimeUnit.SECOND);
        assertFalse(timePoint4.equals(timePoint5));

        // same amount, different units
        TimePoint timePoint6 = new TimePoint(1, TimeUnit.SECOND);
        TimePoint timePoint7 = new TimePoint(1, TimeUnit.MINUTE);
        assertFalse(timePoint6.equals(timePoint7));

        // not object
        TimePoint timePoint8 = new TimePoint(10, TimeUnit.MINUTE);
        String nonTimePoint = "Not a TimePoint";
        assertFalse(timePoint8.equals(nonTimePoint));

        // null object
        TimePoint timePoint9 = new TimePoint(12, TimeUnit.HOUR);
        assertFalse(timePoint9.equals(null));
    }

    @Test
    public void testBetween() {
        // positive and negative difference
        TimePoint timePoint1 = new TimePoint(24, TimeUnit.HOUR);
        TimePoint timePoint2 = new TimePoint(11, TimeUnit.HOUR);
        assertEquals(-13, TimeUnit.HOUR.between(timePoint1, timePoint2));
        assertEquals(13, TimeUnit.HOUR.between(timePoint2, timePoint1));

        // no difference
        TimePoint timePoint3 = new TimePoint(1, TimeUnit.HOUR);
        TimePoint timePoint4 = new TimePoint(60, TimeUnit.MINUTE);
        assertEquals(0, TimeUnit.HOUR.between(timePoint4, timePoint3));
        assertEquals(0, TimeUnit.HOUR.between(timePoint3, timePoint4));

        // tests from skype recomendations
        float expectedT5T6 = 1.0f / 60.0f;
        TimePoint timePoint5 = new TimePoint(1, TimeUnit.HOUR);
        TimePoint timePoint6 = new TimePoint(61, TimeUnit.MINUTE);
        assertEquals(expectedT5T6, TimeUnit.HOUR.between(timePoint5, timePoint6));
        assertEquals(-expectedT5T6, TimeUnit.HOUR.between(timePoint6, timePoint5));
        assertEquals(1, TimeUnit.MINUTE.between(timePoint5, timePoint6));
        assertEquals(-1, TimeUnit.MINUTE.between(timePoint6, timePoint5));
        assertEquals(60, TimeUnit.SECOND.between(timePoint5, timePoint6));
        assertEquals(-60, TimeUnit.SECOND.between(timePoint6, timePoint5));
    }

    @Test
    public void testFutureProximityAdjuster() {
        // get ready
        TimePoint timePoint1 = new TimePoint(12, TimeUnit.SECOND);
        TimePoint timePoint2 = new TimePoint(48, TimeUnit.MINUTE);
        TimePoint timePoint3 = new TimePoint(24, TimeUnit.HOUR);
        TimePoint[] timePoints = {
                timePoint2, timePoint1, timePoint3
        };
        FutureProximityAdjuster futureProximityAdjuster = new FutureProximityAdjuster(timePoints);

        // nearest is 12 Seconds
        TimePoint timePointInFuture = new TimePoint(11, TimeUnit.SECOND);
        TimePoint expectedTimePoint = new TimePoint(12, TimeUnit.SECOND);
        TimePoint findResult = futureProximityAdjuster.adjust(timePointInFuture);
        assertEquals(expectedTimePoint, findResult);

        // nearest is 48 Minutes
        TimePoint timePointInFuture2 = new TimePoint(15, TimeUnit.SECOND);
        TimePoint expectedTimePoint2 = new TimePoint(48, TimeUnit.MINUTE);
        TimePoint findResult2 = futureProximityAdjuster.adjust(timePointInFuture2);
        assertEquals(expectedTimePoint2, findResult2);

        // no points in future
        TimePoint timePointInFuture3 = new TimePoint(25, TimeUnit.HOUR);
        TimePoint notFindResult = futureProximityAdjuster.adjust(timePointInFuture3);
        assertEquals(null, notFindResult);
    }
}