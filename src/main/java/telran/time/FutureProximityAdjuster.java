package telran.time;

public class FutureProximityAdjuster implements TimePointAdjuster {
    TimePoint [] timePoints;

    //need to sort
    public FutureProximityAdjuster(TimePoint [] timePoints){
        // copy a given array, sort copy, assign to the field timePoints
        //using java standart Arrays
        // repeated time points are possible
    }
    @Override
    public TimePoint adjust(TimePoint timePoint) {

        // TODO 
        //returns a time point only in future (greater than a given time point) from the field timePoints
        // nearest to a given timePoint value
        return null;
    }

}
