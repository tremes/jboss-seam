package org.jboss.seam.example.common.test;

import java.io.File;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;

public class DeploymentResolver {

    public static Archive<?> createDeployment() {
        String examplesHome = System.getProperty("seam.examples.home");
        String deploymentPath = SeamGrapheneTest.getProperty("DEPLOYMENT_PATH");
        String deploymentName = deploymentPath.substring(deploymentPath.lastIndexOf("/") + 1);

        Class<? extends Archive<?>> deploymentClass = deploymentName.endsWith(".war")?WebArchive.class:EnterpriseArchive.class;
        
        return ShrinkWrap.create(ZipImporter.class, deploymentName)
                .importFrom(new File(examplesHome + "/" + deploymentPath))
                .as(deploymentClass);
    }
}
