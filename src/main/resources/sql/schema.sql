CREATE TABLE users (
    user_id BIGSERIAL PRIMARY KEY,
    username TEXT UNIQUE NOT NULL,
    email_address TEXT UNIQUE,
    encrypted_password TEXT NOT NULL,
    first_name TEXT,
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

CREATE TABLE sessions (
    session_id BIGSERIAL PRIMARY KEY,
    start_time TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    end_time TIMESTAMP WITH TIME ZONE,
    token TEXT UNIQUE NOT NULL,
    user_id BIGINT REFERENCES users(user_id),
    created_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    last_updated_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    is_removed BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE game_states (
    game_state_id BIGSERIAL PRIMARY KEY,
    game_id BIGSERIAL,
    game_type TEXT NOT NULL,
    game_state_json TEXT,
    created_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    last_updated_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    is_removed BOOLEAN NOT NULL DEFAULT FALSE,
    UNIQUE (game_id, game_type)
);

CREATE TABLE user_game_state_associations (
    association_id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(user_id),
    game_state_id BIGINT NOT NULL REFERENCES game_states(game_state_id),
    UNIQUE (user_id, game_state_id)
);

CREATE TABLE words (
    word_id BIGSERIAL PRIMARY KEY,
    word TEXT UNIQUE NOT NULL
);

CREATE TABLE messages (
    message_id BIGSERIAL PRIMARY KEY,
    message TEXT NOT NULL,
    message_type_id INT NOT NULL,
    message_status_id TEXT NOT NULL,
    sender_user_id BIGINT NOT NULL REFERENCES users(user_id),
    recipient_user_id BIGINT NOT NULL REFERENCES users(user_id),
    sent_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    is_removed BOOLEAN NOT NULL DEFAULT FALSE,
    versions TEXT
);

CREATE TABLE notifications (
    notification_id BIGSERIAL PRIMARY KEY,
    notification_status_id TEXT NOT NULL,
    notification_source_id TEXT NOT NULL,
    user_id BIGINT NOT NULL REFERENCES users(user_id),
    content TEXT NOT NULL,
    created_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    received_date TIMESTAMP WITH TIME ZONE
);

CREATE TABLE feature_flags (
    feature_flag_id BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    status TEXT NOT NULL,
    flag_value TEXT,
    user_scope TEXT NOT NULL,
    user_id BIGINT REFERENCES users(user_id),
    is_removed BOOLEAN NOT NULL DEFAULT FALSE,
    versions TEXT
);