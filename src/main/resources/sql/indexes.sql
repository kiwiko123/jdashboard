CREATE INDEX ON users (lower(username));
CREATE INDEX ON users (lower(email_address));

CREATE INDEX ON request_contexts (uri);
CREATE INDEX ON request_contexts (user_id);

CREATE INDEX ON sessions (user_id, is_removed);
CREATE INDEX ON sessions (token, is_removed);

CREATE INDEX ON game_states (game_type, game_id);

CREATE INDEX ON user_game_state_associations (user_id);

CREATE INDEX ON words (lower(word));

CREATE INDEX ON messages (message_type_id);
CREATE INDEX ON messages (sender_user_id);
CREATE INDEX ON messages (recipient_user_id);

CREATE INDEX ON notifications (user_id);

CREATE UNIQUE INDEX ON feature_flags (lower(name)) WHERE is_removed = false;
CREATE INDEX ON feature_flags (lower(name), user_scope, user_id);

CREATE INDEX ON application_events (event_type, event_key);