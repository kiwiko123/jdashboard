package com.kiwiko.jdashboard.webapp.framework.json.api;

import com.kiwiko.jdashboard.library.json.data.IntermediateJsonBody;

public interface CustomRequestBodySerializationStrategy<T> {

    T deserialize(IntermediateJsonBody body, T target);
}
