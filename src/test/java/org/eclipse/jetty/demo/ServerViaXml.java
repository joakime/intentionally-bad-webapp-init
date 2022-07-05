package org.eclipse.jetty.demo;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.xml.XmlConfiguration;

public class ServerViaXml
{
    public static void main(String[] args) throws Exception
    {
        Path serverXml = Paths.get("src/test/resources/server.xml");
        Path webappsDir = Paths.get("target/webapps").toAbsolutePath();
        Util.createWebApps(webappsDir);

        try (InputStream inputStream = Files.newInputStream(serverXml))
        {
            XmlConfiguration xml = new XmlConfiguration(inputStream);
            xml.getIdMap().put("jetty.deploy.monitoredDir", webappsDir.toUri().toASCIIString());
            System.err.println("### Before Configure");
            Object obj = xml.configure();
            if (obj instanceof Server)
            {
                Server server = (Server) obj;
                server.setDumpAfterStart(true);
                if (!server.isRunning())
                {
                    System.err.println("### Before Start");
                    Util.dumpBeans(server);
                    server.start();
                }
                System.err.println("### Before Join");
                server.join();
                System.err.println("### After Join");
            }
            else
            {
                throw new RuntimeException("Unable to find Server");
            }
        }
    }
}
