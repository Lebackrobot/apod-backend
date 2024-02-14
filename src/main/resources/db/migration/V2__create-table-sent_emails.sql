CREATE TABLE sent_emails(
    id SERIAL PRIMARY KEY,
    subscription_id int NOT NULL,
    created_at DATE NOT NULL DEFAULT NOW(),
    updated_at DATE,

    FOREIGN KEY (subscription_id) REFERENCES subscriptions (id)
);