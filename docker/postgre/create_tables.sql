CREATE USER docker WITH PASSWORD 'docker';
CREATE DATABASE docker;
GRANT ALL PRIVILEGES ON DATABASE docker TO docker;

\c docker;

CREATE TABLE IF NOT EXISTS product (
  product_id INT NOT NULL,
  name varchar(250) NOT NULL,
  PRIMARY KEY (product_id)
);