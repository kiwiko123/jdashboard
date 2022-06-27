package com.kiwiko.jdashboard.tools.apiclient.api.dto;

import com.kiwiko.jdashboard.library.http.client.api.dto.DefaultGsonPayloadDeserializer;
import com.kiwiko.jdashboard.library.http.client.api.dto.DefaultGsonPayloadSerializer;
import com.kiwiko.jdashboard.library.http.client.api.dto.PayloadDeserializer;
import com.kiwiko.jdashboard.library.http.client.api.dto.PayloadSerializer;

class RequestConstants {
    public static final PayloadSerializer DEFAULT_PAYLOAD_SERIALIZER = new DefaultGsonPayloadSerializer();
    public static final PayloadDeserializer DEFAULT_PAYLOAD_DESERIALIZER = new DefaultGsonPayloadDeserializer();
}
