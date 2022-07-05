package org.eclipse.jetty.demo;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BadServletInit extends HttpServlet
{
    @Override
    public void init() throws ServletException
    {
        ServletException cause = new ServletException("This servlet is intentionally failing init()");
        cause.printStackTrace(System.err);
        throw cause;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println("You should not be able to see this.");
    }
}
