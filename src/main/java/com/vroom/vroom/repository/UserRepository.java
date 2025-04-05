package com.vroom.vroom.repository;

import com.vroom.vroom.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<User> userRowMapper = (rs, rowNum) -> new User(
            rs.getLong("idUser"),
            rs.getString("firstName"),
            rs.getString("lastName"),
            rs.getString("email"),
            rs.getString("motDePasse"),
            rs.getBytes("photo"),
            rs.getString("numDeTele"),
            rs.getString("roleUser"),
            rs.getInt("isAdmin")
    );

    // List all users in db (for admin)
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM utilisateur";
        return jdbcTemplate.query(sql, userRowMapper);
    }

    // Create a new user (used for signup)
    public int createUser(User user) {
        String sql = "INSERT INTO utilisateur (firstName, lastName, email, motDePasse, photo, numDeTele, roleUser, isAdmin) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, user.getFirstName(), user.getLastName(), user.getEmail(), user.getMotDePasse(),
                user.getPhoto(), user.getNumDeTele(), user.getRoleUser(), user.isAdmin());
    }

    // Find a user by email (used for login)
    public User findUserByEmail(String email) {
        String sql = "SELECT * FROM utilisateur WHERE email = ?";
        try {
            return jdbcTemplate.queryForObject(sql, userRowMapper, email);
        } catch (EmptyResultDataAccessException ex) {
            // No user found with the given email
            return null;
        }
    }

    // Find user by id (used in update account)
    public User findUserById(Long idUser){
        String sql = "SELECT * FROM utilisateur WHERE idUser = ?";
        try {
            return jdbcTemplate.queryForObject(sql, userRowMapper, idUser);
        } catch (EmptyResultDataAccessException ex) {
            // No user found with the given email
            return null;
        }
    }

    /*
    Delete a user:
    - By user if he wants to delete his account.
    - By admin if he wants to suspend a user's account.
    */
    public int deleteUser(Long idUser) {
        String sql = "DELETE FROM utilisateur WHERE idUser = ?";
        return jdbcTemplate.update(sql, idUser);
    }

    // Update user data
    public int updateUser(User user) {
        String sql = "UPDATE utilisateur set firstName=?, lastName=?, email=?, motDePasse=?, photo=?, numDeTele=? WHERE idUser = ? ";
        return jdbcTemplate.update(
                sql,
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getMotDePasse(),
                user.getPhoto(),
                user.getNumDeTele(),
                user.getIdUser()
        );
    }


}
