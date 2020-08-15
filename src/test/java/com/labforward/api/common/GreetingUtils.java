package com.labforward.api.common;

import com.labforward.api.hello.domain.Greeting;
import org.json.JSONException;
import org.json.JSONObject;

public final class GreetingUtils {

    public static String getGreetingBody(Greeting greeting) throws JSONException {
        JSONObject json = new JSONObject().put("message", greeting.getMessage());

        if (greeting.getId() != null) {
            json.put("id", greeting.getId());
        }

        return json.toString();
    }
}
