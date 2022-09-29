package com.example.mywebquizengine.service;

import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProjectionUtil {
    public static <T> T parseToProjection(Object obj, Class<T> clazz) {
        ProjectionFactory pf = new SpelAwareProxyProjectionFactory();
        return pf.createProjection(clazz, obj);
    }

    public static <T> List<T> parseToProjectionList(List obj, Class<T> clazz) {
        List<T> messageViews = new ArrayList<>();
        ProjectionFactory pf = new SpelAwareProxyProjectionFactory();

        for (Object o : obj) {
            messageViews.add(pf.createProjection(clazz, o));
        }
        return messageViews;
    }

    public static <T> Set<T> parseToProjectionList(Set obj, Class<T> clazz) {
        Set<T> messageViews = new HashSet<>();
        ProjectionFactory pf = new SpelAwareProxyProjectionFactory();

        for (Object o : obj) {
            messageViews.add(pf.createProjection(clazz, o));
        }
        return messageViews;
    }

}