package org.deb.vlcjideo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.net.URL;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoDTORequest {
    @NonNull
    private String url;
    private String title;
}
