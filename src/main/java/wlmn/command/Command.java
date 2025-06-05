package wlmn.command;

import java.io.Serializable;

/**
 * Интерфейс для исполняемых команд.
 */
public abstract class Command implements Serializable{
    protected Class<?>[] signature;
    /**
     * Исполняет команду.
     */
    public abstract String execute();
    public Class<?>[] getSignature(){
        return signature;
    }

    public static String getMessage(){
        return ("У этой команды нет описания.");
    }
}
