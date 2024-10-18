package account;

public class DefaultAccount extends Account {
    final int AccountLevel = 2;


    public DefaultAccount(String accountID, String accountPW) {
        super(accountID, accountPW);
    }


    public int CheckMyAccountLevel() {
        return this.AccountLevel;
    }

}
