CREATE TABLE if not exists TNSEnergy.hourly_data (
    id SERIAL PRIMARY KEY,
    day INTEGER NOT NULL CHECK (day BETWEEN 1 AND 31),
    hour INTEGER NOT NULL CHECK (hour BETWEEN 0 AND 23),
    volume DECIMAL(15, 13) NOT NULL
    );

CREATE INDEX idx_consumption_day_hour ON TNSEnergy.hourly_data(day, hour);
CREATE INDEX idx_consumption_hour ON TNSEnergy.hourly_data(hour);