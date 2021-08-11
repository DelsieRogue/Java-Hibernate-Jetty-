package servlets;

import accountService.AccountService;
import dbService.DBServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignUpServlets extends HttpServlet {
    private AccountService accountService;

    public SignUpServlets(AccountService accountService){
        this.accountService = accountService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String pass = req.getParameter("password");

        resp.setContentType("text/html;charset=utf-8");
        if (login == null || pass == null){
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        try {
            accountService.addUserDataSet(login, pass);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (DBServiceException e) {
            e.printStackTrace();
        }
    }
}
