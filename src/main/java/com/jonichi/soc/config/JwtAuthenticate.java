package com.jonichi.soc.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

public class JwtAuthenticate implements AuthenticationEntryPoint, Serializable {

    /*
     * - serialVersionUID serves as the "state" of serializable object. This is used by Java in deserializing
     * a serialized object.
     * - serialization is the process of transmitting information in a different data structure.
     * - an object is serialized into a string to be transmitted and gets deserialized back into an object when
     * it reaches its destination.
     *
     * - To make it simple, serialVersionUID is marker to make sure everything works smoothly, it is a precaution
     * to ensure that compatibility is maintained in this class, it saves the version of this class.
     * - Meaning, if modification is done into this class (JwtAuthenticate), it could impact the compatibility of
     * objects. This is where the serialVersionUID comes into play, by specifying this version identifier, you're
     * essentially telling Java to be aware that the class might change over time, as long as the serialVersionUID
     * remains the same, it should make an effort to deserialize objects correctly, handling the differences between
     * versions.
     * */
    private static final long serialVersionUID = 56884229452954174L;

    // This method (commence) is for handling unauthorized access in Spring Security application. When an unauthorized
    // request is detected, it sends HTTP response with a status code of 401 Unauthorized and message "Unauthorized"
    // to inform that the access is denied.
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }


}
