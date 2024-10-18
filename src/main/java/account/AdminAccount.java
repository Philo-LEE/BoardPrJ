package account;

public class AdminAccount extends Account {
    final int AccountLevel = 0;

    public AdminAccount(String accountID, String accountPW) {
        super(accountID, accountPW);
    }

    @Override
    int CheckMyAccountLevel() {
        return this.AccountLevel;
    }
}
