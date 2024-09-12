CREATE TABLE IF NOT EXISTS orders (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    date_time datetime NOT NULL,
    status varchar(50) NOT NULL,
    PRIMARY KEY(id)
)