package com.kiwiko.library.persistence.dataAccess.data;

import com.kiwiko.library.json.data.IntermediateJsonBody;

import java.util.HashMap;
import java.util.Map;

public class VersionChanges extends IntermediateJsonBody {

    public VersionChanges(Map<String, Object> body) {
        super(body);
    }

    public VersionChanges() {
        this(new HashMap<>());
    }
}
