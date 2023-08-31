package com.thoughtworks.sample.version;

import com.thoughtworks.sample.version.repository.VersionRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class VersionServiceTest {

    @Autowired
    private VersionService versionService;

    @Autowired
    private VersionRepository versionRepository;



}
