CREATE TABLE IF NOT EXISTS order_products (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    quantity bigint(20) NOT NULL,
    description varchar(255) NOT NULL,
    order_id bigint(20) NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY (order_id) REFERENCES orders(id)
)