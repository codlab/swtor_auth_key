package eu.codlab.common.network;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import eu.codlab.test.TestUtil;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.Sink;
import okio.Timeout;
import retrofit2.Converter;
import retrofit2.Retrofit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by kevinleperf on 06/02/16.
 */
public class ToStringConverterFactoryTest {

    private ToStringConverterFactory mConstructor;
    private Retrofit mRetrofit;

    @Before
    public void setUp() {
        mConstructor = new ToStringConverterFactory();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(NetworkConstants.SWTOR)
                .addConverterFactory(mConstructor)
                .build();
    }

    @Test
    public void testConstructor() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        TestUtil.assertPublicClassWellDefined(ToStringConverterFactory.class);
    }

    @Test
    public void testResponseBodyConverterNull() {

        Converter<ResponseBody, String> converter =
                mConstructor.responseBodyConverter(byte[].class, new Annotation[]{}, mRetrofit);

        assertNull(converter);
    }

    @Test
    public void testResponseBodyConverterNotNull() {

        Converter<ResponseBody, String> converter =
                mConstructor.responseBodyConverter(String.class, new Annotation[]{}, mRetrofit);

        assertNotNull(converter);
    }


    @Test
    public void testRequestBodyConverterNull() {

        Converter<String, RequestBody> converter =
                mConstructor.requestBodyConverter(byte[].class, new Annotation[]{}, mRetrofit);

        assertNull(converter);
    }

    @Test
    public void testRequestBodyConverterNotNull() {

        Converter<String, RequestBody> converter =
                mConstructor.requestBodyConverter(String.class, new Annotation[]{}, mRetrofit);

        assertNotNull(converter);
    }

    @Test
    public void testrequestBodyConverter() {
        final ToStringConverterFactory constructor = new ToStringConverterFactory();

        final Converter<String, RequestBody> converter = constructor.requestBodyConverter(new Type() {
        }, new Annotation[]{}, mRetrofit);
        Converter<ResponseBody, String> converter2 = constructor.responseBodyConverter(new Type() {
        }, new Annotation[]{}, mRetrofit);

        assertNull(converter);
        assertNull(converter2);
    }

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

    @Test
    public void testGetConverterFromResponseBody() throws IOException {
        final String test_against = "SOME STRING";

        final byte[] array = test_against.getBytes();
        final BufferedSource source = new BufferedSource() {
            @Override
            public Buffer buffer() {
                return null;
            }

            @Override
            public boolean exhausted() throws IOException {
                return false;
            }

            @Override
            public void require(long byteCount) throws IOException {

            }

            @Override
            public boolean request(long byteCount) throws IOException {
                return false;
            }

            @Override
            public byte readByte() throws IOException {
                return 0;
            }

            @Override
            public short readShort() throws IOException {
                return 0;
            }

            @Override
            public short readShortLe() throws IOException {
                return 0;
            }

            @Override
            public int readInt() throws IOException {
                return 0;
            }

            @Override
            public int readIntLe() throws IOException {
                return 0;
            }

            @Override
            public long readLong() throws IOException {
                return 0;
            }

            @Override
            public long readLongLe() throws IOException {
                return 0;
            }

            @Override
            public long readDecimalLong() throws IOException {
                return 0;
            }

            @Override
            public long readHexadecimalUnsignedLong() throws IOException {
                return 0;
            }

            @Override
            public void skip(long byteCount) throws IOException {

            }

            @Override
            public ByteString readByteString() throws IOException {
                return null;
            }

            @Override
            public ByteString readByteString(long byteCount) throws IOException {
                return null;
            }

            @Override
            public byte[] readByteArray() throws IOException {
                return array;
            }

            @Override
            public byte[] readByteArray(long byteCount) throws IOException {
                return new byte[0];
            }

            @Override
            public int read(byte[] sink) throws IOException {
                return 0;
            }

            @Override
            public void readFully(byte[] sink) throws IOException {

            }

            @Override
            public int read(byte[] sink, int offset, int byteCount) throws IOException {
                return 0;
            }

            @Override
            public void readFully(Buffer sink, long byteCount) throws IOException {

            }

            @Override
            public long readAll(Sink sink) throws IOException {
                return 0;
            }

            @Override
            public String readUtf8() throws IOException {
                return null;
            }

            @Override
            public String readUtf8(long byteCount) throws IOException {
                return null;
            }

            @Override
            public String readUtf8Line() throws IOException {
                return null;
            }

            @Override
            public String readUtf8LineStrict() throws IOException {
                return null;
            }

            @Override
            public int readUtf8CodePoint() throws IOException {
                return 0;
            }

            @Override
            public String readString(Charset charset) throws IOException {
                return null;
            }

            @Override
            public String readString(long byteCount, Charset charset) throws IOException {
                return null;
            }

            @Override
            public long indexOf(byte b) throws IOException {
                return 0;
            }

            @Override
            public long indexOf(byte b, long fromIndex) throws IOException {
                return 0;
            }

            @Override
            public long indexOf(ByteString bytes) throws IOException {
                return 0;
            }

            @Override
            public long indexOf(ByteString bytes, long fromIndex) throws IOException {
                return 0;
            }

            @Override
            public long indexOfElement(ByteString targetBytes) throws IOException {
                return 0;
            }

            @Override
            public long indexOfElement(ByteString targetBytes, long fromIndex) throws IOException {
                return 0;
            }

            @Override
            public InputStream inputStream() {
                return null;
            }

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                return 0;
            }

            @Override
            public Timeout timeout() {
                return null;
            }

            @Override
            public void close() throws IOException {

            }
        };


        ResponseBody body = new ResponseBody() {
            @Override
            public MediaType contentType() {
                return null;
            }

            @Override
            public long contentLength() {
                return test_against.length();
            }

            @Override
            public BufferedSource source() {
                return source;
            }
        };


        ToStringConverterFactory constructor = new ToStringConverterFactory();
        Converter<ResponseBody, String> converter = constructor.getConverterFromResponseBody();

        assertNotNull(converter);
        assertEquals(test_against, converter.convert(body));

    }

}
