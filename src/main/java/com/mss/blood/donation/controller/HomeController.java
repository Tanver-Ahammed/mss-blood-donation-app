package com.mss.blood.donation.controller;

import com.mss.blood.donation.services.impl.BloodDonationHistoryServiceImpl;
import com.mss.blood.donation.services.impl.FileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class HomeController {

    @Autowired
    private FileServiceImpl fileService;

    @Value("${project.image}")
    private String path;

    // home page api
    @GetMapping(path = {"/", "/home"})
    public String home() {
        return "home";
    }

    // developer's page api
    @GetMapping(path = "/developer")
    public String developer() {
        return "developer";
    }

    // get image
    @GetMapping(value = "/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response
    ) throws IOException {
        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }

}
