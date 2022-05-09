package com.hospital.restapi.service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.stream.Stream;

import com.hospital.restapi.exception.InvalidInputDataException;
import com.hospital.restapi.model.MayReturnNull;

public class FieldsValidator {

    public static void validate(Object obj) throws Exception {
        for (Method m : obj.getClass().getDeclaredMethods()) {
            Annotation[] annotations = m.getDeclaredAnnotations();
            Boolean mayBeNull = Stream.of(annotations).anyMatch(it -> it.annotationType().equals(MayReturnNull.class));

            String methodName = m.getName();
            if (!mayBeNull && methodName.startsWith("get") && m.invoke(obj) == null) {
                String propertyName = methodName.replace("get", "");
                throw new InvalidInputDataException("Invalid value for '" + propertyName + "' entered");
            }
        }
    }

}
