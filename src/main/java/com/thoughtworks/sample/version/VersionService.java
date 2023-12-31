package com.thoughtworks.sample.version;

import com.thoughtworks.sample.exception.VersionInvalidException;
import com.thoughtworks.sample.version.repository.Version;
import com.thoughtworks.sample.version.repository.VersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VersionService {

    @Autowired
    private VersionRepository versionRepository;

    public VersionService(VersionRepository versionRepository) {
        this.versionRepository = versionRepository;
    }

    public String getLatestVersion() throws Exception{
        List<Version> latestVersion = versionRepository.getVersionDetails();
        if(latestVersion.isEmpty())
            throw new VersionInvalidException();
        Version version = latestVersion.get(0);
        String versionName = version.getName();
        return versionName;

    }
}
