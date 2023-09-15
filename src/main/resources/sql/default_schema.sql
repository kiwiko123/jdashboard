CREATE TABLE users (
    user_id BIGSERIAL PRIMARY KEY,
    username TEXT UNIQUE NOT NULL,
    email_address TEXT,
    first_name TEXT,
    created_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    last_updated_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    is_removed BOOLEAN NOT NULL DEFAULT FALSE
);
CREATE INDEX ON users (lower(username));
CREATE INDEX ON users (lower(email_address));

CREATE TABLE user_credentials (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(user_id),
    credential_type TEXT NOT NULL,
    credential_value TEXT NOT NULL,
    created_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    is_removed BOOLEAN NOT NULL DEFAULT FALSE
);
CREATE INDEX ON user_credentials (user_id, lower(credential_type));
CREATE UNIQUE INDEX ON user_credentials (user_id, credential_type) WHERE is_removed = false;

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
CREATE INDEX ON request_contexts (uri);
CREATE INDEX ON request_contexts (user_id);

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
CREATE INDEX ON sessions (user_id, is_removed);
CREATE INDEX ON sessions (token, is_removed);

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
CREATE INDEX ON game_states (game_type, game_id);

CREATE TABLE user_game_state_associations (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(user_id),
    game_state_id BIGINT NOT NULL REFERENCES game_states(id),
    UNIQUE (user_id, game_state_id)
);
CREATE INDEX ON user_game_state_associations (user_id);

CREATE TABLE words (
    word_id BIGSERIAL PRIMARY KEY,
    word TEXT UNIQUE NOT NULL
);
CREATE INDEX ON words (lower(word));

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
CREATE INDEX ON messages (message_type_id);
CREATE INDEX ON messages (sender_user_id);
CREATE INDEX ON messages (recipient_user_id);

CREATE TABLE notifications (
    notification_id BIGSERIAL PRIMARY KEY,
    notification_status_id TEXT NOT NULL,
    notification_source_id TEXT NOT NULL,
    user_id BIGINT NOT NULL REFERENCES users(user_id),
    content TEXT NOT NULL,
    created_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    received_date TIMESTAMP WITH TIME ZONE
);
CREATE INDEX ON notifications (user_id);

CREATE TABLE feature_flags (
    feature_flag_id BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    status TEXT NOT NULL,
    flag_value TEXT,
    user_scope TEXT NOT NULL,
    user_id BIGINT REFERENCES users(user_id),
    is_removed BOOLEAN NOT NULL DEFAULT FALSE
);
CREATE UNIQUE INDEX ON feature_flags (lower(name)) WHERE is_removed = false;
CREATE INDEX ON feature_flags (lower(name), user_scope, user_id);

CREATE TABLE feature_flag_contexts (
    id BIGSERIAL PRIMARY KEY,
    feature_flag_id BIGINT NOT NULL REFERENCES feature_flags(feature_flag_id),
    scope TEXT NOT NULL,
    user_id BIGINT,
    flag_status TEXT NOT NULL,
    flag_value TEXT,
    start_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    end_date TIMESTAMP WITH TIME ZONE
);
CREATE INDEX ON feature_flag_contexts (feature_flag_id) WHERE end_date IS NULL;
CREATE INDEX ON feature_flag_contexts (user_id) WHERE end_date IS NULL;

CREATE TABLE feature_flag_user_associations (
    id BIGSERIAL PRIMARY KEY,
    feature_flag_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    feature_flag_status TEXT NOT NULL,
    is_removed BOOLEAN NOT NULL DEFAULT FALSE
);
CREATE INDEX ON feature_flag_user_associations (feature_flag_id);
CREATE INDEX ON feature_flag_user_associations (user_id);
CREATE UNIQUE INDEX ON feature_flag_user_associations (feature_flag_id, user_id) WHERE is_removed = false;

CREATE TABLE table_record_versions (
    id BIGSERIAL PRIMARY KEY,
    table_name TEXT NOT NULL,
    record_id BIGINT NOT NULL,
    changes TEXT,
    created_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    created_by_user_id BIGINT
);
CREATE INDEX ON table_record_versions (table_name, record_id);

CREATE TABLE universal_unique_identifiers (
    id BIGSERIAL PRIMARY KEY,
    uuid TEXT UNIQUE NOT NULL,
    reference_key TEXT UNIQUE NOT NULL,
    created_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);
CREATE INDEX ON universal_unique_identifiers (uuid);
CREATE INDEX ON universal_unique_identifiers (reference_key);

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
CREATE INDEX ON chatroom_message_room_users (chatroom_message_room_id) WHERE is_removed = false;
CREATE INDEX ON chatroom_message_room_users (user_id) WHERE is_removed = false;
CREATE INDEX ON chatroom_messages (chatroom_message_room_id) WHERE is_removed = false;

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
CREATE INDEX ON permissions (user_id, permission_name);

CREATE TABLE grocery_lists (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    name TEXT,
    created_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    is_removed BOOLEAN NOT NULL DEFAULT FALSE
);
CREATE INDEX ON grocery_lists (user_id) WHERE is_removed = false;
CREATE INDEX ON grocery_items (lower(name)) WHERE is_removed = false;

CREATE TABLE grocery_items (
    id BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    category TEXT,
    is_removed BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE grocery_list_items (
    id BIGSERIAL PRIMARY KEY,
    grocery_list_id BIGINT NOT NULL REFERENCES grocery_lists(id),
    grocery_item_id BIGINT NOT NULL REFERENCES grocery_list_items(id),
    created_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    is_removed BOOLEAN NOT NULL DEFAULT FALSE
);
CREATE INDEX ON grocery_list_items (grocery_list_id) WHERE is_removed = false;

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
CREATE INDEX ON service_request_keys (service_client_name);
CREATE INDEX ON service_request_keys (request_token);

CREATE TABLE timeline_events (
    id BIGSERIAL PRIMARY KEY,
    event_name TEXT NOT NULL,
    event_key TEXT,
    metadata TEXT,
    created_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    created_by_user_id BIGINT NOT NULL
);
CREATE INDEX ON timeline_events (event_name, event_key);