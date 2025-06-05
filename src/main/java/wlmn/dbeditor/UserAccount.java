package wlmn.dbeditor;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Класс для хранения информации о пользователях - логинов и паролей. Хранится в базе данных.
 */
@Entity
@Table(name = "user_account")
public class UserAccount {
    @Id
    private String login;

    private String password;

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public UserAccount(){

    }

    public UserAccount(String login, String password){
        this.login = login;
        this.password = password;
    }
}
