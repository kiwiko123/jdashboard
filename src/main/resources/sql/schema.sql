CREATE TABLE users (
    user_id BIGSERIAL PRIMARY KEY,
    email_address TEXT UNIQUE NOT NULL,
    encrypted_password TEXT NOT NULL,
    created_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    last_updated_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    is_removed BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE request_contexts (
    request_context_id BIGSERIAL PRIMARY KEY,
    uri TEXT NOT NULL,
    start_time TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    end_time TIMESTAMP WITH TIME ZONE,
    created_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    last_updated_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    is_removed BOOLEAN NOT NULL DEFAULT FALSE,
    user_id BIGINT REFERENCES users(user_id)
);