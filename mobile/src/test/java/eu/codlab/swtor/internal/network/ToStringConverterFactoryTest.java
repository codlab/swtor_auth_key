package eu.codlab.swtor.internal.network;

import org.junit.Test;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by kevinleperf on 06/02/16.
 */
public class ToStringConverterFactoryTest {

    @Test
    public void teststringConverter() {
        ToStringConverterFactory constructor = new ToStringConverterFactory();
        Converter converter = constructor.stringConverter(new Type() {
            @Override
            public String toString() {
                return "";
            }
        }, new Annotation[]{});

        assertNull(converter);
    }

    @Test
    public void testgetConverterFromString() throws IOException {
        ToStringConverterFactory constructor = new ToStringConverterFactory();
        Converter<String, RequestBody> converter = constructor.getConverterFromString();
        MediaType type = converter.convert("").contentType();

        assertNotNull(converter);
        assertEquals(MediaType.parse("text/plain; charset=utf-8"), type);
    }

    @Test
    public void testgetConverterFromResponseBody() throws IOException {
        ToStringConverterFactory constructor = new ToStringConverterFactory();
        Converter<ResponseBody, String> converter = constructor.getConverterFromResponseBody();

        assertNotNull(converter);
    }

}
