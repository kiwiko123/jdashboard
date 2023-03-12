CREATE TABLE application_events (
    id BIGSERIAL PRIMARY KEY,
    event_type TEXT NOT NULL,
    event_key TEXT,
    metadata TEXT,
    is_removed BOOLEAN NOT NULL DEFAULT FALSE,
    created_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);
CREATE INDEX ON application_events (event_type, event_key);

CREATE TABLE service_request_keys (
    id BIGSERIAL PRIMARY KEY,
    scope TEXT NOT NULL,
    service_client_name TEXT NOT NULL,
    description TEXT,
    created_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    created_by_user_id BIGINT,
    expiration_date TIMESTAMP WITH TIME ZONE NOT NULL,
    request_token TEXT NOT NULL
);
CREATE INDEX ON service_request_keys (service_client_id);
CREATE INDEX ON service_request_keys (request_token);