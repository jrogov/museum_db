package org.ors.server.dto;

import org.springframework.http.ResponseEntity;

public class ErrorDTO implements IDTO {
    private String errorMsg;


//    public interface ErrorView{};

//    @JsonView(ErrorView.class)
    public ErrorDTO(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public static ResponseEntity<IDTO> response(String message){
        return ResponseEntity.badRequest().body(new ErrorDTO(message));
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
