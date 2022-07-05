package org.eclipse.jetty.demo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.jetty.util.IO;
import org.eclipse.jetty.util.component.ContainerLifeCycle;

public final class Util
{
    public static void createWebApps(Path webappsDir) throws IOException
    {
        ensureDirExists(webappsDir);
        Path badappDir = webappsDir.resolve("badapp");
        ensureDirExists(badappDir);

        copyFile(Paths.get("src/test/resources/webapps/badapp.xml"),
                webappsDir.resolve("badapp.xml"));

        Path webinfDir = badappDir.resolve("WEB-INF");
        ensureDirExists(webinfDir);

        copyFile(Paths.get("src/main/webapp/WEB-INF/web.xml"),
                webinfDir.resolve("web.xml"));

        Path classesDir = webinfDir.resolve("classes");
        ensureDirExists(classesDir);

        copyClass(classesDir, BadServletInit.class.getName());
    }

    public static void copyClass(Path classesDir, String className) throws IOException
    {
        String classNamePath = className.replace(".", "/") + ".class";
        Path srcFile = Paths.get("target/classes", classNamePath);
        Path destFile = classesDir.resolve(classNamePath);
        ensureDirExists(destFile.getParent());
        copyFile(srcFile, destFile);
    }

    public static void copyFile(Path srcPath, Path destPath) throws IOException
    {
        try (InputStream inputStream = Files.newInputStream(srcPath);
             OutputStream outputStream = Files.newOutputStream(destPath))
        {
            IO.copy(inputStream, outputStream);
        }
    }

    public static void dumpBeans(ContainerLifeCycle lifeCycle)
    {
        lifeCycle.getBeans().stream().forEach((bean)->
        {
            System.out.println("### BEAN: " + bean);
        });
    }

    public static void ensureDirExists(Path dir) throws IOException
    {
        if (Files.notExists(dir))
        {
            Files.createDirectories(dir);
        }
    }
}
