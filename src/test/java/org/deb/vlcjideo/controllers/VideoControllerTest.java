package org.deb.vlcjideo.controllers;

import org.deb.vlcjideo.dto.VideoDTORequest;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.net.URL;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class VideoControllerTest {

    public static final String API_URL = "/api/v1/video/set/";

    @InjectMocks
    private VideoController videoController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(videoController).build();
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void setURL() throws Exception {

        VideoDTORequest videoDTORequest = new VideoDTORequest();
        videoDTORequest.setUrl("https://www.youtube.com/watch?v=gMFL5TY5D0E");
        String json = "{\n" +
                "    \"url\":\"rtsp://live:cyc10P11v3@192.168.137.43:554/video.h264\"\n" +
                "}";
                mockMvc.perform(post(API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)).andExpect(status().isOk());

    }
}