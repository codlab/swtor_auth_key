package eu.codlab.swtor.internal.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by kevinleperf on 15/01/16.
 */
public class ToStringConverterFactory extends Converter.Factory {
    private static final MediaType MEDIA_TYPE = MediaType.parse("text/plain; charset=utf-8");

    /**
     * Default constructor
     */
    public ToStringConverterFactory(){

    }

    @Nullable
    public Converter<ResponseBody, String> responseBodyConverter(@NonNull Type type,
                                                                 @Nullable Annotation[] annotations,
                                                                 @NonNull Retrofit retrofit) {
        if (String.class.equals(type)) {
            return getConverterFromResponseBody();
        }
        return null;
    }

    @Nullable
    public Converter<String, RequestBody> requestBodyConverter(@NonNull Type type,
                                                               @Nullable Annotation[] annotations,
                                                               @NonNull Retrofit retrofit) {
        if (String.class.equals(type)) {
            return getConverterFromString();
        }
        return null;
    }

    @Nullable
    public Converter<?, String> stringConverter(@NonNull Type type,
                                                @Nullable Annotation[] annotations) {
        return null;
    }

    Converter<ResponseBody, String> getConverterFromResponseBody() {
        return new Converter<ResponseBody, String>() {
            @Override
            public String convert(@NonNull ResponseBody value) throws IOException {
                return value.string();
            }
        };
    }

    Converter<String, RequestBody> getConverterFromString(){
        return new Converter<String, RequestBody>() {
            @NonNull
            @Override
            public RequestBody convert(@NonNull String value) throws IOException {
                return RequestBody.create(MEDIA_TYPE, value);
            }
        };
    }


}