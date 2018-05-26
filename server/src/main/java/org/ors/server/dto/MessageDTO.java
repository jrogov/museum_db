package org.ors.server.dto;

import org.springframework.http.ResponseEntity;

public class MessageDTO implements IDTO {
    private String message;


//    public interface ErrorView{};

    //    @JsonView(ErrorView.class)
    public MessageDTO(String msg) {
        this.message = msg;
    }

    public static ResponseEntity<IDTO> response(String message){
        return ResponseEntity.ok(new MessageDTO(message));
    }

    public String getMessage() {
        return message;
    }
}
