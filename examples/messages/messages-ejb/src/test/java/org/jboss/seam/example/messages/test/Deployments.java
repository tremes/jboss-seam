package org.jboss.seam.example.messages.test;

import java.io.File;

import org.jboss.seam.example.messages.Message;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

public class Deployments
{
   
   public static WebArchive messagesDeployment() {
      
      // use profiles defined in 'maven.profiles' property in pom.xml
      String profilesString = System.getProperty("maven.profiles");
      String[] profiles = profilesString != null ? profilesString.split(", ?") : new String[0];
      
      File[] libs = Maven.resolver().loadPomFromFile("pom.xml", profiles)
            .importCompileAndRuntimeDependencies()
            // force resolve jboss-seam, because it is provided-scoped in the pom, but we need it bundled in the WAR
            .resolve("org.jboss.seam:jboss-seam").withTransitivity().asFile();
              
      

      return ShrinkWrap.create(WebArchive.class, "seam-messages.war")
              .addPackage(Message.class.getPackage())
              .addAsWebInfResource("META-INF/ejb-jar.xml", "ejb-jar.xml")
              .addAsWebInfResource("components.xml", "components.xml")
              .addAsWebInfResource("persistence.xml", "classes/META-INF/persistence.xml")
              .addAsWebInfResource("import.sql", "classes/import.sql")
              .addAsWebInfResource("jboss-deployment-structure.xml", "jboss-deployment-structure.xml")
              .addAsWebInfResource("seam.properties", "classes/seam.properties")
              .addAsWebInfResource("web.xml", "web.xml")
              .addAsLibraries(libs);
      
   }

}
