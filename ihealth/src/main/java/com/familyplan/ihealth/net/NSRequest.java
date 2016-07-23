package com.familyplan.ihealth.net;

import java.util.Map;

/**
 * Created by LSD on 15/10/18.
 */
public interface NSRequest {
    enum RequestMethod{
        GET,POST
    }
    NSRequest doRequest();

    void cancelRequest();

    void setParams(Map<String, String> params);

    void setHeaders(Map<String, String> header);
}
