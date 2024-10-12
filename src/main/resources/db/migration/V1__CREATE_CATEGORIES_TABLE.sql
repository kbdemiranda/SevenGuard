create schema if not exists sevenguard;

create table sevenguard.categories(
    id bigserial,
    name varchar(255) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp,
    deleted_at timestamp
);

ALTER TABLE sevenguard.categories
    ADD CONSTRAINT pk_categories
        PRIMARY KEY (id);