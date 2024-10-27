package account;

public class DefaultAccount extends Account {
    final int AccountLevel = 2;



    public DefaultAccount(String accountID, String accountPW, String email) {
        super(accountID, accountPW, email);
    }

    public int checkMyAccountLevel() {
        return this.AccountLevel;
    }

}
