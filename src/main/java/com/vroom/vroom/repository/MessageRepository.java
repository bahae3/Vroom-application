package com.vroom.vroom.repository;

import com.vroom.vroom.model.Message;
import com.vroom.vroom.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public class MessageRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Message> messageRowMapper = (rs, rowNum) -> new Message(
            rs.getLong("idMessage"),
            rs.getLong("idExpediteur"),
            rs.getLong("idDestinataire"),
            rs.getString("contenu"),
            rs.getDate("dateEnvoi")
    );

    // Store a message after it's sent
    public int storeMessage(long idSender, int idReceiver, String content){
        String sql = "INSERT INTO messages (idExpediteur, idDestinataire, contenu) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, idSender, idReceiver, content);
    }

    // If a user wished to modify a message
    public int updateMessage(long idMessage, String contenu){
        String sql = "UPDATE messages set contenu=? WHERE idMessage = ? ";
        return jdbcTemplate.update(
                sql,
                contenu,
                idMessage
        );
    }

    // If a user wishes to delete a message
    public int deleteMessage(long idMessage) {
        String sql = "DELETE FROM messages WHERE idMessage = ?";
        return jdbcTemplate.update(sql, idMessage);
    }
}
