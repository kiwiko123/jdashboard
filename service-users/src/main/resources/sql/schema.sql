CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username TEXT UNIQUE NOT NULL,
    created_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    is_removed BOOLEAN NOT NULL DEFAULT FALSE
);
CREATE INDEX ON users (lower(username));

CREATE TABLE user_credentials (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    credential_type TEXT NOT NULL,
    credential_value TEXT NOT NULL,
    created_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    is_removed BOOLEAN NOT NULL DEFAULT FALSE
);
CREATE INDEX ON user_credentials (user_id, lower(credential_type));
CREATE UNIQUE INDEX ON user_credentials (user_id, credential_type) WHERE is_removed = false;