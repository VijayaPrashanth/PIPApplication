package com.thoughtworks.sample.version;

import com.thoughtworks.sample.version.repository.VersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VersionService {

    @Autowired
    private VersionRepository versionRepository;

    public String getLatestVersion() {
        List<String> latestVersion = versionRepository.getLatestVersion();
        String version = latestVersion.get(0);
        if (version == null) {
            return null;
        }
        return version;
    }
}
