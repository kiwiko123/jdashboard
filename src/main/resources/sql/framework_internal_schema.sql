CREATE TABLE application_events (
    id BIGSERIAL PRIMARY KEY,
    event_type TEXT NOT NULL,
    event_key TEXT,
    metadata TEXT,
    is_removed BOOLEAN NOT NULL DEFAULT FALSE,
    created_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);
CREATE INDEX ON application_events (event_type, event_key);

CREATE TABLE incoming_application_request_logs (
    id BIGSERIAL PRIMARY KEY,
    uri TEXT NOT NULL,
    remote_host TEXT,
    ip_address TEXT,
    request_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    user_id BIGINT
);