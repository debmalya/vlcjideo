package org.deb.vlcjideo.dto;

import lombok.Data;

@Data
public class APIResponse {
    private String status;
    private String errorCode;
    private String errorMessage;
}
