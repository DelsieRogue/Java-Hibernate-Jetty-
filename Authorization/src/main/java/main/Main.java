package main;

import accountService.AccountService;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.SignInServlets;
import servlets.SignUpServlets;

import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) throws Exception {
        AccountService accountService = new AccountService();

        ServletContextHandler servletContextHandler = new ServletContextHandler();
        servletContextHandler.addServlet(new ServletHolder(new SignInServlets(accountService)), "/signin");
        servletContextHandler.addServlet(new ServletHolder(new SignUpServlets(accountService)), "/signup");

        Server server = new Server(8080);
        server.setHandler(servletContextHandler);

        server.start();
        Logger.getGlobal().info("Server started");
        server.join();
    }
}
