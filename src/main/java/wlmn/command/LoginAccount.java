package wlmn.command;

import wlmn.dbeditor.AuthManager;

public class LoginAccount implements Command{
    private String login;
    private String password;

    public LoginAccount(String login, String password){
        this.login = login;
        this.password = password;
    }

    public String execute() {
        return AuthManager.authenticateUser(login, password);
    }
}