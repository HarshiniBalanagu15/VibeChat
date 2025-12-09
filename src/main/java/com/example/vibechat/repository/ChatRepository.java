package com.example.vibechat.repository;

import com.example.vibechat.model.Chat;
import com.example.vibechat.service.SparqlService;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ChatRepository {
    private final SparqlService sparqlService;

    public ChatRepository(SparqlService sparqlService){
        this.sparqlService = sparqlService;
    }

    public void createChatRoom(String chatRoomId){
        String query = String.format("""
                PREFIX : <http://example.com/schema#>
                
                INSERT DATA {
                    :ChatRoom_%s a :ChatRoom ;
                        :chatRoomId "%s" .
                }
                """, chatRoomId, chatRoomId);
        sparqlService.update(query);
    }

    public void updateChat(String chatRoomId, Chat chat){
        String messageId = String.valueOf(System.currentTimeMillis());
        String chatURI = "http://example.com/schema#Chat_" + messageId;
        String query = String.format("""
                PREFIX : <http://example.com/schema#>
                
                INSERT DATA{
                    <%s> a :Chat;
                        :sender "%s";
                        :message "%s";
                        :time "%s".
                    :ChatRoom_%s :hasMessage <%s>
                }
                """, chatURI, chat.getSender(),chat.getMessage(),chat.getTime(),chatRoomId,chatURI);
        sparqlService.update(query);
    }

    public List<Map<String, String>> getChat(String chatRoomId) {
        String query = String.format("""
                    PREFIX : <http://example.com/schema#>
                
                    SELECT ?chat ?sender ?message ?time
                    WHERE{
                    :ChatRoom_%s :hasMessage ?chat.
                
                    ?chat a :Chat;
                        :sender ?sender;
                        :message ?message;
                        :time ?time.
                    }
                    ORDER BY ASC(?time)
                """, chatRoomId);
        return sparqlService.select(query);
    }
}