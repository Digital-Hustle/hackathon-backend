CREATE SCHEMA IF NOT EXISTS tns_energy;

CREATE TABLE IF NOT EXISTS users
(
    id         INTEGER PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    username   VARCHAR(50)  NOT NULL UNIQUE,
    password   VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS profile
(
    id      INTEGER PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    user_id INTEGER NOT NULL UNIQUE,
    CONSTRAINT fk_profile_user FOREIGN KEY (user_id)
        REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS report
(
    id           INTEGER PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    data         BYTEA     NOT NULL,
    user_id      INTEGER   NOT NULL,
    created_at   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    file_name    VARCHAR(255),
    file_size    BIGINT,
    content_type VARCHAR(100),
    CONSTRAINT fk_report_user FOREIGN KEY (user_id)
        REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS hourly_data
(
    id     INTEGER PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    day    INTEGER         NOT NULL CHECK (day BETWEEN 1 AND 31),
    hour   INTEGER         NOT NULL CHECK (hour BETWEEN 0 AND 23),
    volume DECIMAL(15, 13) NOT NULL
);

CREATE TABLE IF NOT EXISTS users_roles
(
    user_id INTEGER     NOT NULL,
    role    VARCHAR(20) NOT NULL,
    CONSTRAINT pk_users_roles PRIMARY KEY (user_id, role),
    CONSTRAINT fk_users_roles_user FOREIGN KEY (user_id)
        REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS user_roles
(
    user_id INTEGER     NOT NULL,
    role    VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);