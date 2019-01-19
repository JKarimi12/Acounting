package karimi.javad.accounting.model;

public class TotalNotebookAccount {
    private String account, money;

    public TotalNotebookAccount(String account, String money) {
        this.account = account;
        this.money = money;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
