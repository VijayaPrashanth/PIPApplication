package com.thoughtworks.sample.version;

import com.thoughtworks.sample.version.repository.VersionRepository;
import com.thoughtworks.sample.version.view.VersionController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class VersionControllerTest {

    @InjectMocks
    private VersionController versionController;

    @Mock
    private VersionService versionService;

    @Mock
    private VersionRepository versionRepository;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnVersion() throws Exception {

        versionController = new VersionController(versionService);
        String mockVersion = "v2";
        when(versionService.getLatestVersion()).thenReturn(mockVersion);

        ResponseEntity<HashMap<String, String>> responseEntity = versionController.getVersion();
        HashMap<String, String> responseBody = responseEntity.getBody();

        assertEquals(mockVersion, responseBody.get("CurrentVersion"));

    }
}


