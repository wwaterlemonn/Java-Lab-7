package wlmn.command;

import wlmn.dbeditor.AuthManager;

public class RegisterAccount extends Command{
    private String login;
    private String password;

    public RegisterAccount(String login, String password){
        this.login = login;
        this.password = password;
        signature = new Class<?>[]{String.class, String.class};
    }

    public String execute() {
        return AuthManager.registerUser(login, password);
    }

    public static String getMessage(){
        return ("register_account login password: позволяет зарегистрировать новый аккаунт с логином login и паролем password. "
        + "Их следует указать через пробел. После регистрации автоматически производится вход в созданный аккаунт на время этой сессии.");
    }
}
