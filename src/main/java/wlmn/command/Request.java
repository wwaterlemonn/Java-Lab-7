package wlmn.command;

import java.io.Serializable;

public class Request implements Serializable{
    //private String login;
    //private String password;
    private String key;
    private Object[] args;

    public Request(String key, Object[] args){
        this.key = key;
        this.args = args;
    }

    public String getKey() {
        return key;
    }

    public Object[] getArgs() {
        return args;
    }

    // public String getLogin() {
    //     return login;
    // }

    // public String getPassword() {
    //     return password;
    // }
}
