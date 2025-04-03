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

    public List<User> getAllUsers() {
        String sql = "SELECT * FROM utilisateur";
        return jdbcTemplate.query(sql, userRowMapper);
    }

    public int createUser(User user) {
        String sql = "INSERT INTO utilisateur (firstName, lastName, email, motDePasse, photo, numDeTele, roleUser, isAdmin) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, user.getFirstName(), user.getLastName(), user.getEmail(), user.getMotDePasse(),
                user.getPhoto(), user.getNumDeTele(), user.getRoleUser(), user.isAdmin());
    }


    public User findUserByEmail(String email) {
        String sql = "SELECT * FROM utilisateur WHERE email = ?";
        try {
            return jdbcTemplate.queryForObject(sql, userRowMapper, email);
        } catch (EmptyResultDataAccessException ex) {
            // No user found with the given email
            return null;
        }
    }

    public int deleteUser(Long idUser) {
        String sql = "DELETE FROM utilisateur WHERE idUser = ?";
        return jdbcTemplate.update(sql, idUser);
    }
}
