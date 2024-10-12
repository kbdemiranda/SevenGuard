create schema if not exists sevenguard;

create table sevenguard.products
(
    id                bigserial,
    name              varchar(255)   not null,
    description       text,
    price             numeric(10, 2) not null,
    quantity_in_stock integer        not null,
    category_id       integer,
    created_at        timestamp default current_timestamp,
    updated_at        timestamp,
    deleted_at        timestamp

);

ALTER TABLE sevenguard.products
    ADD CONSTRAINT pk_products
        PRIMARY KEY (id);

ALTER TABLE sevenguard.products
    ADD CONSTRAINT fk_category
        FOREIGN KEY (category_id)
            REFERENCES sevenguard.categories (id);