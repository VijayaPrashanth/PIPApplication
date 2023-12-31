package com.thoughtworks.sample.version.view;

import com.thoughtworks.sample.handlers.ErrorResponse;
import com.thoughtworks.sample.version.VersionService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.HashMap;


@RestController
@RequestMapping("/version")
public class VersionController {

    @Autowired
    private VersionService versionService;

    public VersionController(VersionService versionService) {
     this.versionService=versionService;
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Fetched admin details successfully"),
            @ApiResponse(code = 404, message = "Record not found", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Something failed in the server", response = ErrorResponse.class)
    })
    @GetMapping()
    public ResponseEntity<HashMap<String,String>> getVersion() throws Exception {
        HashMap<String,String> version = new HashMap<>();
        String latestVersion = versionService.getLatestVersion();
        version.put("CurrentVersion",latestVersion);
        return new ResponseEntity<>(version, HttpStatus.OK);
    }
}

