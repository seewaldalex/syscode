package com.syscode.syscodedemo.integration.util;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.lang.reflect.Type;

public abstract class EndpointTest extends IntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    protected Gson gson = new Gson();


    protected <T> T readResponse(MockHttpServletResponse mvcResult, Class<T> typeToken) throws IOException {
        return gson.fromJson(mvcResult.getContentAsString(), typeToken);
    }

    protected <T> T readResponse(MockHttpServletResponse mvcResult, Type type) throws IOException {
        return gson.fromJson(mvcResult.getContentAsString(), type);
    }

    protected String toJson(Object toJson) {
        return gson.toJson(toJson);
    }

}
