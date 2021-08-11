package accountService;

import dbService.DBService;
import dbService.DBServiceException;
import dbService.dataSets.UsersDataSet;

public class AccountService {
    private DBService dbService;

    public AccountService(){
        dbService = new DBService();
    }

    public UsersDataSet getUserDataSetByLogin(String login) throws DBServiceException {
        return dbService.getUserDataSet(login);
    }

    public long addUserDataSet(String login, String password) throws DBServiceException {
        return dbService.addUserDataSet(login, password);
    }
}
