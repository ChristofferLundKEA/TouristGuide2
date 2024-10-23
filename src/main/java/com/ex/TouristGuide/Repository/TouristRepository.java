package com.ex.TouristGuide.Repository;

import com.ex.TouristGuide.Model.City;
import com.ex.TouristGuide.Model.Tag;
import com.ex.TouristGuide.Model.TouristAttraction;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TouristRepository {

    private static final String URL = "jdbc:mysql://localhost:3306/tourist_attractions_db";
    private static final String USERNAME = "test";
    private static final String PASSWORD = "test";

    // Find all tourist attractions
    public List<TouristAttraction> findAll() {
        List<TouristAttraction> attractions = new ArrayList<>();
        String sql = "SELECT ta.id, ta.name, ta.description, c.name as cityName " +
                "FROM tourist_attractions ta " +
                "JOIN cities c ON ta.cityID = c.cityID";

        try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                TouristAttraction attraction = new TouristAttraction();
                attraction.setId(rs.getLong("id"));
                attraction.setName(rs.getString("name"));
                attraction.setDescription(rs.getString("description"));
                attraction.setCity(String.valueOf(City.valueOf(rs.getString("cityName"))));
                attraction.setTags(getTagsForAttraction(rs.getLong("id")));
                attractions.add(attraction);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return attractions;
    }

    // Find an attraction by name
    public TouristAttraction findByName(String name) {
        String sql = "SELECT ta.id, ta.name, ta.description, c.name as cityName " +
                "FROM tourist_attractions ta " +
                "JOIN cities c ON ta.cityID = c.cityID WHERE ta.name = ?";
        try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    TouristAttraction attraction = new TouristAttraction();
                    attraction.setId(rs.getLong("id"));
                    attraction.setName(rs.getString("name"));
                    attraction.setDescription(rs.getString("description"));
                    attraction.setCity(String.valueOf(City.valueOf(rs.getString("cityName"))));
                    attraction.setTags(getTagsForAttraction(rs.getLong("id")));
                    return attraction;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    // Save a new tourist attraction
    public void save(TouristAttraction attraction) {
        String sql = "INSERT INTO tourist_attractions (name, description, cityID) VALUES (?, ?, ?)";
        try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, attraction.getName());
            stmt.setString(2, attraction.getDescription());
            stmt.setInt(3, getCityIDByName(attraction.getCity()));
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    long attractionID = generatedKeys.getLong(1);
                    saveTagsForAttraction(attractionID, attraction.getTags());
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Update an existing attraction
    public void update(TouristAttraction attraction) {
        String sql = "UPDATE tourist_attractions SET description = ?, cityID = ? WHERE name = ?";
        try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, attraction.getDescription());
            stmt.setInt(2, getCityIDByName(attraction.getCity()));
            stmt.setString(3, attraction.getName());
            stmt.executeUpdate();

            long attractionID = getAttractionIDByName(attraction.getName());
            saveTagsForAttraction(attractionID, attraction.getTags());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Delete an attraction by name
    public void delete(String name) {


        String sql = "DELETE FROM tourist_attractions WHERE name = ?";
        try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Helper method to get cityID from city name
    private int getCityIDByName(String cityName) throws SQLException {
        String sql = "SELECT cityID FROM cities WHERE name = ?";
        try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, cityName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("cityID");
                } else {
                    throw new SQLException("City not found");
                }
            }
        }
    }

    // Helper method to get tagID from tag name
    private int getTagIDByName(String tagName) throws SQLException {
        String sql = "SELECT tagID FROM tags WHERE name = ?";
        try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, tagName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("tagID");
                } else {
                    throw new SQLException("Tag not found");
                }
            }
        }
    }

    // Helper method to associate tags with a tourist attraction in the join table
    private void saveTagsForAttraction(long attractionID, List<Tag> tags) throws SQLException {


        String deleteTagsSQL = "DELETE FROM attraction_tags WHERE attractionID = ?";
        try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement stmt = con.prepareStatement(deleteTagsSQL)) {

            stmt.setLong(1, attractionID);
            stmt.executeUpdate();
        }

        String insertTagSQL  = "INSERT INTO attraction_tags (attractionID, tagID) VALUES (?, ?)";
        try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement stmt = con.prepareStatement(insertTagSQL )) {

            for (Tag tag : tags) {
                stmt.setLong(1, attractionID);
                stmt.setInt(2, getTagIDByName(tag.name()));
                stmt.executeUpdate();
            }
        }
    }

    // Helper method to get tags for a specific attraction
    private List<Tag> getTagsForAttraction(long attractionID) throws SQLException {
        List<Tag> tags = new ArrayList<>();
        String sql = "SELECT t.name FROM tags t " +
                "JOIN attraction_tags at ON t.tagID = at.tagID " +
                "WHERE at.attractionID = ?";

        try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setLong(1, attractionID);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    tags.add(Tag.valueOf(rs.getString("name")));
                }
            }
        }
        return tags;
    }

    // Helper method to get attractionID by attraction name
    private long getAttractionIDByName(String attractionName) throws SQLException {
        String sql = "SELECT id FROM tourist_attractions WHERE name = ?";
        try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, attractionName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong("id");
                } else {
                    throw new SQLException("Attraction not found");
                }
            }
        }
    }
}
