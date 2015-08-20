package kata.external;

public class Data {

    public final String id;

    /** Latest event timestamp. */
    public final long timestamp;

    /** Counted events in timeframe. */
    public final long eventsSince;


    public Data(String id, long timestamp, long eventsSince) {
        this.id = id;
        this.timestamp = timestamp;
        this.eventsSince = eventsSince;
    }

    /** Update with another piece of event data iff the id matches. */
    public Data updateWith(Data other) {
        if (this.id.equals(other.id)) {
            long ts = other.timestamp < this.timestamp ? other.timestamp : this.timestamp;
            return new Data(id, ts, eventsSince + other.eventsSince);
        } else return this;
    }
}
