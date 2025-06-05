package wlmn.command;

import wlmn.dbeditor.AuthManager;

public class LoginAccount extends Command{
    private String login;
    private String password;

    public LoginAccount(String login, String password){
        this.login = login;
        this.password = password;
    }

    public String execute() {
        return AuthManager.authenticateUser(login, password);
    }

    public static String getMessage(){
        return ("register_account login password: позволяет войти в аккаунт с логином login и паролем password. "
        + "Их следует указать через пробел.");
    }
}