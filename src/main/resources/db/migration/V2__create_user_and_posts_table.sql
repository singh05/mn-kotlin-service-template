

CREATE TABLE IF NOT EXISTS users (
  user_id INT GENERATED ALWAYS AS IDENTITY,
  first_name varchar(255) NOT NULL,
  last_name varchar(255),
  email varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP,
  PRIMARY KEY(user_id),
  UNIQUE(email)
);

CREATE TABLE IF NOT EXISTS user_posts (
  post_id INT GENERATED ALWAYS AS IDENTITY,
  user_id INT NOT NULL,
  title varchar(500) NOT NULL,
  content text NOT NULL,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP,
  PRIMARY KEY(post_id),
  CONSTRAINT fk_users
      FOREIGN KEY(user_id)
  	  REFERENCES users(user_id)
  	  ON DELETE CASCADE
);