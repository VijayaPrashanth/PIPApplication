package com.thoughtworks.sample.version.view;

import com.thoughtworks.sample.version.VersionService;
import com.thoughtworks.sample.version.repository.VersionRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class VersionControllerIntegrationTest {
    @Autowired
    private VersionService versionService;
    @Autowired
    private VersionRepository versionRepository;


}
