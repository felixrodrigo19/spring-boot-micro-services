CREATE TABLE IF NOT EXISTS payments(
    id bigint(20) NOT NULL AUTO_INCREMENT,
    value decimal(19,2) NOT NULL,
    name varchar(100) NOT NULL,
    number varchar(19) NOT NULL,
    expiration_date varchar(7) NOT NULL,
    code varchar(3) NOT NULL,
    status varchar(50) NOT NULL,
    order_id bigint(20) NOT NULL,
    payment_method_id bigint(20) NOT NULL,
    PRIMARY KEY (id)
);