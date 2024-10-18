package account;

public abstract class Account {
    //Account 생성, 모든 Account의 아이디 관리.
    int AccountLevel = 0;

    static int accountIndex;
    String accountID;
    String accountPW;

    public Account(String accountID, String accountPW) {
        this.accountID = accountID;
        this.accountPW = accountPW;
        accountIndex++;
    }

    //회원등급 반환하기.
    abstract int CheckMyAccountLevel();




}
