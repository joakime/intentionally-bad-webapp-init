package org.eclipse.jetty.demo;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.jetty.deploy.DeploymentManager;
import org.eclipse.jetty.deploy.providers.WebAppProvider;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerCollection;

public class ServerMain
{
    public static void main(String[] args) throws Exception
    {
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8080);
        server.addConnector(connector);

        ContextHandlerCollection contexts = new ContextHandlerCollection();

        DeploymentManager deploymentManager = new DeploymentManager();
        deploymentManager.setContexts(contexts);
        WebAppProvider webAppProvider = new WebAppProvider();
        deploymentManager.addAppProvider(webAppProvider);

        Path webappsDir = Paths.get("target/webapps").toAbsolutePath();
        Util.createWebApps(webappsDir);
        webAppProvider.setMonitoredDirName(webappsDir.toString());
        webAppProvider.setScanInterval(1);

        server.addBean(deploymentManager);

        HandlerCollection handlers = new HandlerCollection();
        handlers.addHandler(contexts);
        handlers.addHandler(new DefaultHandler());

        server.setHandler(handlers);
        Util.dumpBeans(server);
        server.start();

        server.join();
    }
}
