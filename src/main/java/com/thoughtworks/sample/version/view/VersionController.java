package com.thoughtworks.sample.version.view;

import com.thoughtworks.sample.version.VersionService;
import com.thoughtworks.sample.version.repository.VersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/version")
public class VersionController {

    @Autowired
    private VersionService versionService;

    public VersionController(VersionService versionService) {
     this.versionService=versionService;
    }

    @GetMapping
    public ResponseEntity<String> getVersion() {
        String latestVersion = versionService.getLatestVersion();
        if (latestVersion != null) {
            return ResponseEntity.ok().body(Map.of("CurrentVersion", latestVersion).toString());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

