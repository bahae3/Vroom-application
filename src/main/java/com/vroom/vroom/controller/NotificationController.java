package com.vroom.vroom.controller;

import com.vroom.vroom.model.Notification;
import com.vroom.vroom.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    // Get all notifications for a specific user by his id
    @GetMapping("/user/{idUser}")
    public ResponseEntity<List<Notification>> getNotificationsByUserId(@PathVariable long idUser) {
        List<Notification> notifications = notificationService.getNotificationsById(idUser);
        return ResponseEntity.ok(notifications);
    }

    // Create a new notification
    @PostMapping
    public ResponseEntity<String> createNotification(@RequestBody Notification notification) {
        int result = notificationService.createNotification(notification);
        if (result > 0) {
            return ResponseEntity.ok("Notification created successfully.");
        } else {
            return ResponseEntity.status(500).body("Error creating notification.");
        }
    }

    // Delete a notification by its id
    @DeleteMapping("/{idNotification}")
    public ResponseEntity<String> deleteNotification(@PathVariable long idNotification) {
        int result = notificationService.deleteNotification(idNotification);
        if (result > 0) {
            return ResponseEntity.ok("Notification deleted successfully.");
        } else {
            return ResponseEntity.status(404).body("Notification not found.");
        }
    }
}
