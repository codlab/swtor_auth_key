package eu.codlab.common.security.events;

/**
 * Created by kevinleperf on 03/02/16.
 */
public class CodeInvalidateEvent {
    private long mNextIterationIn;

    public CodeInvalidateEvent(long nextIterationIn) {
        mNextIterationIn = nextIterationIn;
    }

    public long getNextIterationIn() {
        return mNextIterationIn;
    }
}
