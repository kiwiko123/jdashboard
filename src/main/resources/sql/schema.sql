CREATE TABLE users (
    user_id BIGSERIAL PRIMARY KEY,
    email_address TEXT UNIQUE NOT NULL,
    encrypted_password TEXT NOT NULL,
    created_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    last_updated_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);