CREATE TABLE users (
    user_id BIGSERIAL PRIMARY KEY,
    username TEXT UNIQUE NOT NULL,
    email_address TEXT,
    encrypted_password TEXT NOT NULL,
    first_name TEXT,
    created_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    last_updated_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    is_removed BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE user_credentials (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(user_id),
    credential_type TEXT NOT NULL,
    credential_value TEXT NOT NULL,
    created_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
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

CREATE TABLE client_sessions (
    id BIGSERIAL PRIMARY KEY,
    start_time TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    end_time TIMESTAMP WITH TIME ZONE
);

CREATE TABLE game_states (
    id BIGSERIAL PRIMARY KEY,
    game_id BIGSERIAL,
    game_type TEXT NOT NULL,
    game_state_json TEXT,
    is_removed BOOLEAN NOT NULL DEFAULT FALSE,
    UNIQUE (game_id, game_type)
);

CREATE TABLE user_game_state_associations (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(user_id),
    game_state_id BIGINT NOT NULL REFERENCES game_states(id),
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
    is_removed BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE application_events (
    id BIGSERIAL PRIMARY KEY,
    event_type TEXT NOT NULL,
    event_key TEXT,
    metadata TEXT,
    is_removed BOOLEAN NOT NULL DEFAULT FALSE,
    created_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

CREATE TABLE table_record_versions (
    id BIGSERIAL PRIMARY KEY,
    table_name TEXT NOT NULL,
    record_id BIGINT NOT NULL,
    changes TEXT,
    created_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    created_by_user_id BIGINT
);

CREATE TABLE universal_unique_identifiers (
    id BIGSERIAL PRIMARY KEY,
    uuid TEXT UNIQUE NOT NULL,
    reference_key TEXT UNIQUE NOT NULL,
    created_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

CREATE TABLE chatroom_message_rooms (
    id BIGSERIAL PRIMARY KEY,
    is_removed BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE chatroom_message_room_users (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(user_id),
    chatroom_message_room_id BIGINT NOT NULL REFERENCES chatroom_message_rooms(id),
    is_removed BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE chatroom_messages (
    id BIGSERIAL PRIMARY KEY,
    sender_user_id BIGINT NOT NULL REFERENCES users(user_id),
    chatroom_message_room_id BIGINT NOT NULL REFERENCES chatroom_message_rooms(id),
    message_status TEXT NOT NULL,
    sent_date TIMESTAMP WITH TIME ZONE,
    message TEXT,
    is_removed BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE permissions (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(user_id),
    permission_name TEXT NOT NULL,
    created_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    is_removed BOOLEAN NOT NULL DEFAULT FALSE
);