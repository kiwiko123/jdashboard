package com.kiwiko.webapp.mvc.json.api;

import com.kiwiko.library.json.data.IntermediateJsonBody;

public interface CustomRequestBodySerializationStrategy<T> {

    T deserialize(IntermediateJsonBody body, T target);
}
