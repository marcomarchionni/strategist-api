package com.marcomarchionni.strategistapi.services.odata;

public interface ValueParser {
    <T> T parse(Class<T> fieldType, String value);
}