package wlmn.command;

import wlmn.dbeditor.AuthManager;

public class RegisterAccount implements Command{
    private String login;
    private String password;

    public RegisterAccount(String login, String password){
        this.login = login;
        this.password = password;
    }

    public String execute() {
        return AuthManager.registerUser(login, password);
    }
}
