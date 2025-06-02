package wlmn.command;

import java.io.Serializable;

/**
 * Интерфейс для исполняемых команд.
 */
public interface Command extends Serializable{
    /**
     * Исполняет команду.
     */
    public String execute();
}
