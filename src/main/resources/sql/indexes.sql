CREATE INDEX ON users (lower(email_address));

CREATE INDEX ON request_contexts (uri);
CREATE INDEX ON request_contexts (user_id);

CREATE INDEX ON game_states (game_type, game_id);