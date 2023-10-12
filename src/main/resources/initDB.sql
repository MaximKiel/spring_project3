CREATE TABLE sensor (
                        id int PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
                        name varchar(30) NOT NULL UNIQUE
);

CREATE TABLE measurement (
                        id int PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
                        value double precision NOT NULL CHECK (value > -100) CHECK (value < 100),
                        raining boolean NOT NULL,
                        measure_at timestamp NOT NULL,
                        sensor_id int REFERENCES sensor(id) ON DELETE CASCADE NOT NULL
);