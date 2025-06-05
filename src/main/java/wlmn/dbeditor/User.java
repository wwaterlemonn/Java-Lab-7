package wlmn.dbeditor;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * Класс для хранения информации о пользователях - логинов и паролей. Хранится в базе данных.
 */
@Entity
public class User {
    @Id
    private String login;

    private byte[] password;

    public String getLogin() {
        return login;
    }

    public byte[] getPassword() {
        return password;
    }

    public User(){

    }

    public User(String login, byte[] password){
        this.login = login;
        this.password = password;
    }
}
