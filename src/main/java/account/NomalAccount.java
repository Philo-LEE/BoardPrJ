package account;

public class NomalAccount extends Account {
    final int AccountLevel = 1;

    public NomalAccount(String accountID, String accountPW, String email) {
        super(accountID, accountPW, email);
    }

    @Override
    public int CheckMyAccountLevel() {
        return this.AccountLevel;
    }
}
