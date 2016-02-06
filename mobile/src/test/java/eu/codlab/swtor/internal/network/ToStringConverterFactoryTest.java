package eu.codlab.swtor.internal.network;

import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import retrofit2.Converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by kevinleperf on 06/02/16.
 */
public class ToStringConverterFactoryTest {

    @Test
    public void testgetConverterFromString() throws IOException {
        ToStringConverterFactory constructor = new ToStringConverterFactory();
        Converter<String, RequestBody> converter = constructor.getConverterFromString();
        MediaType type = converter.convert("").contentType();

        assertNotNull(converter);
        assertEquals(MediaType.parse("text/plain"), type);
    }

    @Test
    public void testgetConverterFromResponseBody() throws IOException {
        ToStringConverterFactory constructor = new ToStringConverterFactory();
        Converter<ResponseBody, String> converter = constructor.getConverterFromResponseBody();


        ResponseBody impl = new ResponseBody() {
            @Override
            public MediaType contentType() {
                return null;
            }

            @Override
            public long contentLength() {
                return "test".length();
            }

            @Override
            public BufferedSource source() {
                return null;
            }
        };

        Mockito.when(impl.string()).thenReturn("test");

        assertNotNull(converter);
        assertEquals("test", impl.string());
    }

}
