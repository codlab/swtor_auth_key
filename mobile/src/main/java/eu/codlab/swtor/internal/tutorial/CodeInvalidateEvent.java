package eu.codlab.swtor.internal.tutorial;

/**
 * Created by kevinleperf on 03/02/16.
 */
public class CodeInvalidateEvent {
    private long _next_iteration_in;

    public CodeInvalidateEvent(long nextIterationIn) {
        _next_iteration_in = nextIterationIn;
    }

    public long getNextIterationIn() {
        return _next_iteration_in;
    }
}
