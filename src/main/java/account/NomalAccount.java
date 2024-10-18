package account;

public class NomalAccount extends Account {
    final int AccountLevel = 1;

    public NomalAccount(String accountID, String accountPW) {
        super(accountID, accountPW);
    }

    @Override
    int CheckMyAccountLevel() {
        return this.AccountLevel;
    }
}
