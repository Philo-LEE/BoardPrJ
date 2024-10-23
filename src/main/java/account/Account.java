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
        createDate = timeStamp.format(now);


        this.email = email;
        this.accountID = accountID;
        this.accountPW = accountPW;
        accountIndex++;

    }

    //회원등급 반환하기.
    public abstract int CheckMyAccountLevel();





}
