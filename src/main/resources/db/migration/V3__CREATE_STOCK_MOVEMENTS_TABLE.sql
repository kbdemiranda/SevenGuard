CREATE TABLE sevenguard.stock_movements
(
    id            bigserial,
    product_id    integer     not null,
    movement_type varchar(10) not null,
    quantity      integer     not null,
    movement_date timestamp default current_timestamp,
    created_at    timestamp default current_timestamp,
    updated_at    timestamp,
    deleted_at    timestamp
);

ALTER TABLE sevenguard.stock_movements
    ADD CONSTRAINT pk_stock_movements
        PRIMARY KEY (id);

ALTER TABLE sevenguard.stock_movements
    ADD CONSTRAINT fk_product
        FOREIGN KEY (product_id)
            REFERENCES sevenguard.products (id);
