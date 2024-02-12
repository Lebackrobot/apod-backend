CREATE TABLE subscriptions (   
    id serial primary key,
    email text unique not null,
    username text not null,
    created_at timestamp default now(),
    status boolean default true,
    updated_at timestamp
);