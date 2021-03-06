CREATE TABLE event (

    /* id, title, description, startTime, endTime, registrationDeadline,
       capacity, numOfParticipants, avgRating, category status */
    eventId UUID NOT NULL PRIMARY KEY,
    organizerId UUID NOT NULL,
    title VARCHAR(128) NOT NULL,
    description VARCHAR(256) NOT NULL,
    startTime TIMESTAMP NOT NULL,
    endTime TIMESTAMP NOT NULL,
    registrationDeadline TIMESTAMP NOT NULL,
    capacity INT NOT NULL,
    numOfParticipants INT DEFAULT 0,
    avgRating FLOAT DEFAULT 0,
    category VARCHAR(32),
    status VARCHAR(32),
    venueId UUID NOT NULL,
    image VARCHAR(256) DEFAULT 'assets/event-1.jpg'
);





