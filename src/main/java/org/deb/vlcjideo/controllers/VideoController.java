package org.deb.vlcjideo.controllers;

import lombok.extern.slf4j.Slf4j;
import org.deb.vlcjideo.dto.APIResponse;
import org.deb.vlcjideo.dto.VideoDTORequest;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("Modify RTSP video URL and play with JavaFX")
@RequestMapping("api/v1/video")
@Slf4j
public class VideoController {



    @PostMapping("/set")
    public ResponseEntity<APIResponse> setURL(@RequestBody final VideoDTORequest videoChangeRequest){
        if (log.isInfoEnabled()){
            log.info(String.format("Incoming request : %s",videoChangeRequest));
        }
        APIResponse apiResponse = new APIResponse();
        apiResponse.setStatus("OK");
        apiResponse.setErrorCode("");
        apiResponse.setErrorMessage("");

        return ResponseEntity.ok(apiResponse);
    }
}
