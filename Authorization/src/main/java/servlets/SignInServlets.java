package servlets;

import accountService.AccountService;
import dbService.DBServiceException;
import dbService.dataSets.UsersDataSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignInServlets extends HttpServlet {
    private AccountService accountService;

    public SignInServlets(AccountService accountService){
        this.accountService = accountService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String pass = req.getParameter("pass");

        resp.setContentType("text/html;charset=utf-8");
        if (login == null || pass == null){
            resp.getWriter().println("Unauthorized");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        UsersDataSet usersDataSet;
        try {
            usersDataSet = accountService.getUserDataSetByLogin(login);
        } catch (DBServiceException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        if (usersDataSet == null){
            resp.getWriter().println("Unauthorized");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        if (!usersDataSet.getPassword().equals(pass)){
            resp.getWriter().println("Unauthorized");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        resp.getWriter().println("Authorized: " + usersDataSet.getLogin());
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
