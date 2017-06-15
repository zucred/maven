package com.vascomouta.VMLogger.webservice;


import java.util.List;
import java.util.Map;

public class Response {

    private int responseCode;
    private String responseString;
    private Map<String, List<String>> headerParams;

    public Response(int responseCode, String responseString) {
        super();
        this.responseCode = responseCode;
        this.responseString = responseString;
    }
    public Response(int responseCode, String responseString, Map<String, List<String>> headerParams) {
        super();
        this.responseCode = responseCode;
        this.responseString = responseString;
        this.headerParams = headerParams;
    }

}
