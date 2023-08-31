package com.thoughtworks.sample.version.view;

import com.thoughtworks.sample.version.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    public HashMap<String,String> getVersion() throws Exception {
        HashMap<String,String> version = new HashMap<>();
        String latestVersion = versionService.getLatestVersion();
        version.put("CurrentVersion",latestVersion);
        return version;

    }
}

