package eventsg.backend.dao;

import eventsg.backend.model.User;
import eventsg.backend.model.Event;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventRegisterDao {
    int registerEvent(UUID userId, UUID eventId);

    int deregisterEvent(UUID userId, UUID eventId);

    List<UUID> getregisteredEvents(UUID userId);



}



