package org.deb.vlcjideo.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Data
public class URLService {

    @Value("${videoURL}")
    private String URL;


}
