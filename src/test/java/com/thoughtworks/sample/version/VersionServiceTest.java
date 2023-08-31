package com.thoughtworks.sample.version;

import com.thoughtworks.sample.exception.VersionInvalidException;
import com.thoughtworks.sample.version.repository.Version;
import com.thoughtworks.sample.version.repository.VersionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class VersionServiceTest {

    @Autowired
    private VersionService versionService;

    @Autowired
    private VersionRepository versionRepository;

    @Test
    public void shouldReturnLatestVersion() throws Exception {
        versionRepository = mock(VersionRepository.class);
        versionService = new VersionService(versionRepository);
        Version version = new Version(1,"v1");
        List<Version> versions = new ArrayList<>();
        String expectedVersion = "v1";

        versions.add(version);
        when(versionRepository.getLatestVersion()).thenReturn(versions);
        versionRepository.save(version);
        String latestVersion = versionService.getLatestVersion();

        assertThat(latestVersion,is(equalTo(expectedVersion)));
    }
    @Test
    void shouldThrowExceptionIfVersionInvalid() throws VersionInvalidException {
        versionRepository = mock(VersionRepository.class);
        versionService = new VersionService(versionRepository);
        Version version = new Version(1,"null");

        assertThrows(VersionInvalidException .class, () -> versionService.getLatestVersion());
    }

}
