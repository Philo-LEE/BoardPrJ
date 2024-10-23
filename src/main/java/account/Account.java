package account;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Account {
    //Account 생성, 모든 Account의 아이디 관리.
    final int AccountLevel = 2;

    static int accountIndex;
    private String accountID;
    private String accountPW;
    private String email;
    private String createDate;



    public Account(String accountID, String accountPW, String email) {
        //만든 시간 삽입
        Date now = new Date();
        SimpleDateFormat timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.createDate = timeStamp.format(now);


        this.email = email;
        this.accountID = accountID;
        this.accountPW = accountPW;
        accountIndex++;

    }

    //회원등급 반환하기.
    public abstract int checkMyAccountLevel();

    //비밀번호 확인하기
    public boolean checkPw(String accountPW){
        return this.accountPW.equals(accountPW);
    }

    //


    public String getAccountID() {
        return accountID;
    }

    public String getAccountPW() {
        return accountPW;
    }

    public String getEmail() {
        return email;
    }

    public String getCreateDate() {
        return createDate;
    }
}
