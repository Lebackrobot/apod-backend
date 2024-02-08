CREATE TABLE "user" (   
    id serial primary key,
    email text unique not null,
    nickname text not null,
    created_at timestamp default now(),
    updated_at timestamp
);