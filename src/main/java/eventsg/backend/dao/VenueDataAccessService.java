package eventsg.backend.dao;

import eventsg.backend.datasource.Assets;
import eventsg.backend.mapper.VenueRowMapper;
import eventsg.backend.model.Venue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("venueDao")
public class VenueDataAccessService implements VenueDao {


    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public VenueDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * insert a new venue record into the postgres database with the sql statement
     *
     * @param venue Venue object with the necessary values to be inserted
     * @return 1 if insertion is successful; else 0
     */
    @Override
    public int addVenue(Venue venue) {
        String sql = "" +
                "INSERT INTO venue (" +
                "venueId, " +
                "address, " +
                "postalCode, " +
                "ownerId, " +
                "rentalFee, " +
                "area, " +
                "description, " +
                "venueName, " +
                "image" +
                ")" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        UUID venueId = UUID.randomUUID();
        String image = Assets.Assets().getVenueImage();
        return jdbcTemplate.update(
                sql,
                venueId,
                venue.getAddress(),
                venue.getPostalCode(),
                venue.getOwnerId(),
                venue.getRentalFee(),
                venue.getArea(),
                venue.getDescription(),
                venue.getVenueName(),
                image
        );
    }

    /**
     * delete a venue record from the postgres database
     *
     * @param venueId venueId
     * @return 1 if deletion is successful; else 0
     */
    @Override
    public int deleteVenueById(UUID venueId) { // tested
        final String sql = "" + "DELETE FROM venue WHERE venueId = ?";
        return jdbcTemplate.update(sql, venueId);
    }

    /**
     * update a record in the database
     *
     * @param venueId venueId
     * @param venue   Venue object with the necessary values used to perform update
     * @return 1 if update is successful; else 0
     */
    @Override
    public int updateVenueById(UUID venueId, Venue venue) { // tested
        String sql = "" +
                "UPDATE venue " +
                "SET " +
                "venueId = ?, " +
                "address = ?, " +
                "postalCode = ?, " +
                "ownerId = ?, " +
                "rentalFee = ?, " +
                "area = ?, " +
                "description = ?, " +
                "venueName = ?" +
                "WHERE venueId = ?";
        return jdbcTemplate.update(
                sql,
                venueId,
                venue.getAddress(),
                venue.getPostalCode(),
                venue.getOwnerId(),
                venue.getRentalFee(),
                venue.getArea(),
                venue.getDescription(),
                venue.getVenueName(),
                venueId
        );
    }

    /**
     * get the venue record with the venueId
     *
     * @param selectedVenueId venueId
     * @return the venue if found, else return null
     */
    @Override
    public Venue getVenueById(UUID selectedVenueId) { // tested
        final String sql = "SELECT * FROM venue WHERE venueId = ?";
        Venue venue = jdbcTemplate.queryForObject(
                sql,
                new Object[]{selectedVenueId},
                new VenueRowMapper());
        return venue;
    }

    /**
     * get all the venues in the database
     *
     * @return a list of Venue objects
     */
    @Override
    public List<Venue> getAllVenues() { // tested
        final String sql = "SELECT * FROM venue";
        List<Venue> venues = jdbcTemplate.query(sql, new VenueRowMapper());
        return venues;
    }

    /**
     * return all the venues belonging to a certain owner
     *
     * @param selectedOwnerId onwerId
     * @return a list of Venue objects
     */
    @Override
    public List<Venue> getVenuesByOwnerId(UUID selectedOwnerId) { // tested
        final String sql = "SELECT * FROM venue WHERE ownerId = ?";
        List<Venue> venues = jdbcTemplate.query(
                sql,
                new Object[]{selectedOwnerId},
                new VenueRowMapper());
        return venues;

    }

    /**
     * get all venues with similar name
     * @param selectedVenueName venue name
     * @return list of Venue objects
     */
    @Override
    public List<Venue> getVenueByName(String selectedVenueName) {  // tested
        final String sql = "SELECT * FROM venue WHERE venueName LIKE '%?%'?";
        List<Venue> venues = jdbcTemplate.query(
                sql,
                new Object[]{selectedVenueName},
                new VenueRowMapper());
        return venues;
    }


    /**
     * get all venues that have area close to the input area
     *
     * @param selectedArea the demanded area
     * @return a list of Venue objects
     */
    @Override
    public List<Venue> getVenueByArea(double selectedArea) { // tested
        final String sql = "SELECT * FROM venue WHERE ABS(area - ?) <= 10";
        List<Venue> venues = jdbcTemplate.query(
                sql,
                new Object[]{selectedArea},
                new VenueRowMapper());
        return venues;
    }

    /**
     * get all venues with prices around the specified budget
     *
     * @param budget budget
     * @return a list of Venue objects
     */
    @Override
    public List<Venue> getVenueByBudget(double budget) { //
        final String sql = "SELECT * FROM venue WHERE ABS(rentalFee - ?) <= 50";
        List<Venue> venues = jdbcTemplate.query(
                sql,
                new Object[]{budget},
                new VenueRowMapper());
        return venues;
    }

    /**
     * Get the venue of an event by eventID
     * @param eventId UUID of event.
     * @return the venue.
     */
    @Override
    public Venue getVenueByEventId(UUID eventId) {
        final String sql = "SELECT * FROM event INNER JOIN venue ON event.venueId = venue.venueId WHERE eventId = ?";
        Venue venue = jdbcTemplate.queryForObject(
                sql,
                new Object[]{eventId},
                new VenueRowMapper());
        return venue;
    }
}