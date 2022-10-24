package com.horoscope.repos;

import com.horoscope.models.User;
import com.horoscope.utils.CRUDDaoInterface;
import com.horoscope.utils.ConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepo implements CRUDDaoInterface<User> {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepo.class);
    private Connection conn;

    public UserRepo() {
        try {
            conn = ConnectionManager.getConnection();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }


    @Override
    public int create(User user) {
        String sql = "INSERT INTO users (id, first_name, last_name, email, pass_word, zodiac, mood) VALUES (default, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);


            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getZodiac());
            stmt.setString(6, user.getMood());

            // this executes the sql statement above
            stmt.executeUpdate();

            // this gives us a result set of the row that was just created
            ResultSet rs = stmt.getGeneratedKeys();

            // the cursor is right in front of the first (or only) element in our result set
            // calling rs.next() iterates us into the first row
            rs.next();

            return rs.getInt("id");
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return 0;
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();

        try {
            String sql = "SELECT * FROM users";

            PreparedStatement stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("pass_word"));
                user.setZodiac(rs.getString("zodiac"));
                user.setMood(rs.getString("mood"));

                users.add(user);
            }

        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return users;
    }

    @Override
    public User getByID(int id) {
        try {
            String sql = "SELECT * FROM users WHERE id=?";
            User user = new User();

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("pass_word"));
                user.setZodiac(rs.getString("zodiac"));
                user.setMood(rs.getString("mood"));

                return user;
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    public User getByEmail(String email) {
        try {
            String sql = "SELECT * FROM users WHERE email=?";
            User user = new User();

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("pass_word"));
                user.setZodiac(rs.getString("zodiac"));
                user.setMood(rs.getString("mood"));

                return user;
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    @Override
    public User update(User user) {
        String sql = "UPDATE users SET first_name = ?, last_name = ?, email = ?, pass_word = ?, zodiac = ?, mood = ? WHERE id = ?";
        User updatedUser = new User();

        try {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getZodiac());
            stmt.setString(6, user.getMood());
            stmt.setInt(7, user.getId());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next()) {
                updatedUser.setId(rs.getInt("id"));
                updatedUser.setFirstName(rs.getString("first_name"));
                updatedUser.setLastName(rs.getString("last_name"));
                updatedUser.setEmail(rs.getString("email"));
                updatedUser.setPassword(rs.getString("pass_word"));
                updatedUser.setZodiac(rs.getString("zodiac"));
                updatedUser.setMood(rs.getString("mood"));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return updatedUser;
    }

    @Override
    public boolean delete(User user) {
        String sql = "DELETE FROM users WHERE id = ? first_name = ?, last_name = ?, email = ?, pass_word = ?, zodiac = ?, mood = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, user.getId());
            stmt.setString(2, user.getFirstName());
            stmt.setString(3, user.getLastName());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getPassword());
            stmt.setString(6, user.getZodiac());
            stmt.setString(7, user.getMood());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteByID(int id) {
        User user = getByID(id);
        if (user != null)
            return delete(user);
        return false;
    }
}
