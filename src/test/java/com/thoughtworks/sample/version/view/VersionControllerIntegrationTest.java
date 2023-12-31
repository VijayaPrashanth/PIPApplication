package com.thoughtworks.sample.version.view;

import com.thoughtworks.sample.SampleApplication;
import com.thoughtworks.sample.version.VersionService;
import com.thoughtworks.sample.version.repository.Version;
import com.thoughtworks.sample.version.repository.VersionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = SampleApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@WithMockUser
public class VersionControllerIntegrationTest {

    @Autowired
    private VersionService versionService;
    @Autowired
    private VersionRepository versionRepository;
    @Autowired
    private VersionController versionController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnLatestVersion() throws Exception {
        versionRepository.deleteAll();
        versionRepository.save(new Version(1,"v1"));

        mockMvc.perform(get("/version"))
                .andExpect(status().isOk())
                .andExpect(content().json("{'CurrentVersion':'v1'"+"}"));

    }

}
