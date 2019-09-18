package com.example.demospringboot.configuration;

import org.springframework.web.servlet.mvc.condition.RequestCondition;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApiVersionCondition implements RequestCondition<ApiVersionCondition> {

    private int apiVersion;

    private final static Pattern VERSION_PREFIX_PATTERN = Pattern.compile("/v(\\d+)/");

    ApiVersionCondition(int apiVersion) {
        this.apiVersion = apiVersion;
    }

    /**
     * ApiVersion specified on methods override that on the controller class.
     */
    @Override
    public ApiVersionCondition combine(ApiVersionCondition other) {
        return new ApiVersionCondition(other.getApiVersion());
    }

    @Override
    public ApiVersionCondition getMatchingCondition(HttpServletRequest request) {
        Matcher m = VERSION_PREFIX_PATTERN.matcher(request.getRequestURI());
        if (m.find()) {
            // find matching ApiVersionCondition
            int version = Integer.parseInt(m.group(1));
            if (version >= getApiVersion()) {
                return this;
            }
        }
        return null;
    }

    /**
     * When a request matches multiple ApiVersionConditions, assign the one with greater version number.
     */
    @Override
    public int compareTo(ApiVersionCondition other, HttpServletRequest request) {
        return other.getApiVersion() - getApiVersion();
    }

    public int getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(int apiVersion) {
        this.apiVersion = apiVersion;
    }
}
