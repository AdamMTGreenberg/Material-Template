package com.adamg.materialtemplate.cloud.network.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

/**
 * Class that handles conversion of an object from JSON to concrete JAVA using Jackson since the speed in
 * the latest benchmarks is ~3x that of GSON as of 10/24/14
 * <br>
 * See {@linktourl https://github.com/kdubb1337/retrofit-examples} for the concrete class this was based on.
 * <br>
 * Used under Apache license.
 * <p/>
 * <br>
 * @author Adam Greenberg
 * @version 1 on 6/27/15
 */
public class JacksonConverter implements Converter {

    // MIME type standard
    private static final String MIME_TYPE = "application/json; charset=UTF-8";

    /**
     * Maps the objects according to their Jackson annotations
     */
    private final ObjectMapper objectMapper;

    /**
     * Default constructor creating a self containg {@link ObjectMapper}
     */
    public JacksonConverter() {
        this(new ObjectMapper());
    }

    /**
     * Default constructor for the JSON to Jackson converter
     *
     * @param objectMapper default object that maps the POJO to the JSON object
     */
    public JacksonConverter(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Object fromBody(final TypedInput body, final Type type) throws ConversionException {
        try {
            JavaType javaType = objectMapper.getTypeFactory().constructType(type);
            return objectMapper.readValue(body.in(), javaType);
        } catch (final IOException e) {
            throw new ConversionException(e);
        }
    }

    @Override
    public TypedOutput toBody(final Object object) {
        try {
            String json = objectMapper.writeValueAsString(object);
            return new TypedByteArray(MIME_TYPE, json.getBytes("UTF-8"));
        }
        catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

}
