package account.Session;

import account.Account;

public class Session {
    private String loginId;
    private String password;
    private String email;

    private boolean isLogIn;
    private int userLevel;

    public Session() {}

    public void sessionOn(Account account) {
        this.isLogIn = true;
        this.loginId = account.getAccountID();
        this.password = account.getAccountPW();
        this.userLevel = account.checkMyAccountLevel();
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void defaultsessionSet(){
        isLogIn = false;
        userLevel = 2;
        loginId = "손님";
        email = null;
        password = null;
    }

    public String getLoginId() {
        return loginId;
    }
}
