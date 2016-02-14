package eu.codlab.common.network;

import org.junit.Before;
import org.junit.Test;

import retrofit2.Response;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by kevinleperf on 11/02/16.
 */
public class CallbackStringTest {
    private CallbackString mCallbackString;

    @Before
    public void setUp() {
        mCallbackString = new CallbackString() {
            @Override
            public void onResponse(Response<String> response) {

            }
        };
    }

    @Test
    public void testNotSetThrowable() {
        assertNull(mCallbackString.getError());
        assertFalse(mCallbackString.hasError());
    }

    @Test
    public void testSetThrowable() {

        try {
            throw new NullPointerException("Exception");
        } catch (Throwable throwable) {
            mCallbackString.onFailure(throwable);
        }

        assertNotNull(mCallbackString.getError());
        assertTrue(mCallbackString.hasError());
    }


}
