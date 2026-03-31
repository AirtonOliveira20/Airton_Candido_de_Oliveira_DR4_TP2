package org.sammancoaching;

import org.sammancoaching.dependencies.*;

public class PipelineRefactor {

    private final Config config;
    private final Emailer emailer;
    private final Logger log;

    public PipelineRefactor(Config config, Emailer emailer, Logger log) {
        this.config = config;
        this.emailer = emailer;
        this.log = log;
    }

    public void run(Project project) {
        boolean testsPassed;
        boolean deploySuccessful;

        testsPassed = isTestsPassed(project);

        deploySuccessful = isDeploySuccessful(project, testsPassed);
        sendEmailSummary(testsPassed, deploySuccessful);
    }

    private void sendEmailSummary(boolean testsPassed, boolean deploySuccessful) {
        if (config.sendEmailSummary()) {
            log.info("Sending email");
            emailer.send(buildEmailMessage(testsPassed, deploySuccessful));
        } else {
            log.info("Email disabled");
        }
    }


    private String buildEmailMessage(boolean testsPassed, boolean deploySuccessful){
        if (!testsPassed) {
            return "Tests failed";
        }

        if (deploySuccessful) {
            return "Deployment completed successfully";
        }

        return "Deployment failed";
    }

    private boolean isDeploySuccessful(Project project, boolean testsPassed) {
        boolean deploySuccessful;
        if (testsPassed) {
            if (project.deploy() == DeployStatus.SUCCESS) {
                log.info("Deployment successful");
                deploySuccessful = true;
            } else {
                log.error("Deployment failed");
                deploySuccessful = false;
            }
        } else {
            deploySuccessful = false;
        }
        return deploySuccessful;
    }

    private boolean isTestsPassed(Project project) {
        boolean testsPassed;
        if (project.hasTests()) {
            if (project.runTests() == DeployStatus.SUCCESS) {
                log.info("Tests passed");
                testsPassed = true;
            } else {
                log.error("Tests failed");
                testsPassed = false;
            }
        } else {
            log.info("No tests");
            testsPassed = true;
        }
        return testsPassed;
    }
}