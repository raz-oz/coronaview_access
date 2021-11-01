package com.rad.ms.corona_view.access.Responses;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.Map;

public class ErrorResponse implements Response
{
    private Map<String, String> response;

    public ErrorResponse(String message)
    {
        response = new HashMap<>();
        response.put("Error", message);
    }

    @Override
    public Map<String, String> getBody()
    {
        return response;
    }

    /**
     * The function receives client exception and builds response at same type of the exception
     *
     * @param e - the exception causes by 404/401 Http request from data
     * @return responseEntity that fits to the exception.
     */
    public static Object buildErrorResponse(HttpClientErrorException e)
    {
        Map<String, String> error = new HashMap<>();
        error.put("Error", e.getResponseBodyAsString());
        if (e.getStatusCode().value() == 401)
        {
            error.put("Error", "Unauthorized: " + e.getResponseBodyAsString());
        }
        return new ResponseEntity<Object>(error, e.getStatusCode());
    }
}