CREATE TABLE grocery_lists (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    name TEXT,
    created_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    is_removed BOOLEAN NOT NULL DEFAULT FALSE
);
CREATE INDEX ON grocery_lists (user_id) WHERE is_removed = false;

CREATE TABLE grocery_items (
    id BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    category TEXT,
    is_removed BOOLEAN NOT NULL DEFAULT FALSE
);
CREATE INDEX ON grocery_items (lower(name)) WHERE is_removed = false;

CREATE TABLE grocery_list_items (
    id BIGSERIAL PRIMARY KEY,
    grocery_list_id BIGINT NOT NULL REFERENCES grocery_lists(id),
    grocery_item_id BIGINT NOT NULL REFERENCES grocery_list_items(id),
    created_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    is_removed BOOLEAN NOT NULL DEFAULT FALSE
);
CREATE INDEX ON grocery_list_items (grocery_list_id) WHERE is_removed = false;