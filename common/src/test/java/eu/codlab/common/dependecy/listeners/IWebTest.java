package eu.codlab.common.dependecy.listeners;

import android.support.annotation.NonNull;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import eu.codlab.common.dependency.listeners.IWeb;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.assertNotNull;

/**
 * Created by kevinleperf on 08/02/16.
 */
public class IWebTest {

    private IWeb mIWeb;

    @Before
    public void init() {
        mIWeb = new IWeb() {
            @NonNull
            @Override
            public Call<String> getRoot() {
                return new Call<String>() {
                    @Override
                    public Response<String> execute() throws IOException {
                        return null;
                    }

                    @Override
                    public void enqueue(Callback<String> callback) {

                    }

                    @Override
                    public boolean isExecuted() {
                        return false;
                    }

                    @Override
                    public void cancel() {

                    }

                    @Override
                    public boolean isCanceled() {
                        return false;
                    }

                    @Override
                    public Call<String> clone() {
                        return null;
                    }
                };
            }
        };
    }

    @Test
    public void testIWeb() {
        assertNotNull(mIWeb.getRoot());
    }
}
