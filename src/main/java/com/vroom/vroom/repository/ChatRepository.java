package com.vroom.vroom.repository;

import com.vroom.vroom.model.Chat;
import com.vroom.vroom.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public class ChatRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Chat> chatRowMapper = (rs, rowNum) -> new Chat(
            rs.getLong("idChat"),
            rs.getLong("idExpediteur"),
            rs.getLong("idDestinataire")
    );

    // List all chat conversations
    public List<Chat> getAllChats() {
        String sql = "SELECT * FROM chat";
        return jdbcTemplate.query(sql, chatRowMapper);
    }

    // Create a new chat
    // after a user sends a first message, both sender and receivers will have new chat created
    public int createChat(long idExpediteur, long idDestinaraire) {
        String sql = "INSERT INTO chat (idExpediteur, idDestinataire) VALUES (?, ?)";
        return jdbcTemplate.update(sql, idExpediteur, idDestinaraire);
    }

    // Delete a chat
    public int deleteChat(long idChat) {
        String sql = "DELETE FROM chat WHERE idChat = ?";
        return jdbcTemplate.update(sql, idChat);
    }
}
