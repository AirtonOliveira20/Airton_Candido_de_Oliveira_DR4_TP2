package org.sammancoaching;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sammancoaching.dependencies.*;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import static org.mockito.Mockito.*;

class PipelineTest {

    private Config config;
    private Emailer emailer;
    private Logger log;
    private Project project;
    private PipelineRefactor pipeline;;

    @BeforeEach
    void setUp() {
        config = mock(Config.class);
        emailer = mock(Emailer.class);
        log = mock(Logger.class);
        project = mock(Project.class);

        pipeline = new PipelineRefactor(config, emailer, log);
    }

    @Test
    void shouldRunTestsDeployAndSendSuccessEmail() {
        when(project.hasTests()).thenReturn(true);
        when(project.runTests()).thenReturn(DeployStatus.SUCCESS);
        when(project.deploy()).thenReturn(DeployStatus.SUCCESS);
        when(config.sendEmailSummary()).thenReturn(true);

        pipeline.run(project);

        verify(log).info("Tests passed");
        verify(log).info("Deployment successful");
        verify(log).info("Sending email");
        verify(emailer).send("Deployment completed successfully");
    }
}

