package com.vroom.vroom.service;

import com.vroom.vroom.model.Notification;
import com.vroom.vroom.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    // List all notifications of a single user by his id
    public List<Notification> getNotificationsById(long idUser) {
        return notificationRepository.getNotificationsById(idUser);
    }


    // Create a new notification
    public int createNotification(Notification notif) {
        return notificationRepository.createNotification(notif);
    }

    // Delete a notification
    public int deleteNotification(long idNotification) {
        return notificationRepository.deleteNotification(idNotification);
    }
}
