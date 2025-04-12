package com.vroom.vroom.repository;

import com.vroom.vroom.model.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NotificationRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Notification> notificationRowMapper = (rs, rowNum) -> new Notification(
            rs.getLong("idNotification"),
            rs.getLong("idUser"),
            rs.getString("titre"),
            rs.getString("message"),
            rs.getDate("dateNotification")
    );

    // List all notifications of a single user by his id
    public List<Notification> getNotificationsById(long idUser) {
        String sql = "SELECT * FROM notifications WHERE idUser=?";
        return jdbcTemplate.query(sql, notificationRowMapper, notificationRowMapper);
    }


    // Create a new notification
    public int createNotification(Notification notif) {
        String sql = "INSERT INTO notifications (idUser, titre, message) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, notif.getIdUser(), notif.getTitre(), notif.getMessage());
    }

    // Delete a notification
    public int deleteNotification(long idNotification) {
        String sql = "DELETE FROM notifications WHERE idNotification = ?";
        return jdbcTemplate.update(sql, idNotification);
    }

    public boolean sendNotification(Notification notification) {
        String sql = "INSERT INTO notifications (idUser, titre, message) VALUES (?, ?, ?)";
        int rows = jdbcTemplate.update(sql, notification.getIdUser(), notification.getTitre(), notification.getMessage());
        return rows > 0;
    }
}
