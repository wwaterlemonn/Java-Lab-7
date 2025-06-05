package wlmn.command;

public abstract class ModificationCommand extends Command{
    protected String login = null;

    public void setLogin(String login) {
        if (this.login == null && login != null){
            this.login = login;
        }
    }
}
