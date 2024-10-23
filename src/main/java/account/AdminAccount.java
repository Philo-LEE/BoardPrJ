package account;

public class AdminAccount extends Account {
    final int AccountLevel = 0;

    public AdminAccount(String accountID, String accountPW,String email) {
        super(accountID, accountPW, email);
    }

    @Override
    public int checkMyAccountLevel() {
        return this.AccountLevel;
    }
}
