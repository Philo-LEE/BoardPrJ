package command;

import account.Account;
import account.AccountsBox;
import account.NomalAccount;
import account.Session.Session;

import java.util.HashMap;
import java.util.Scanner;

public class AccountCommand {

    //login 로그인
    //signup 회원등록
    //signout 로그아웃(로그인 상태가 아닌데 로그아웃요청시 예외처리)
    //detail - accountId / accountId에 해당하는 계정정보 조회해서 뿌려줌 accountId 회원 : 계정/ 이메일/ 가입일
    //edit - accountId / accountId에 해당하는 계정의 정보를 변경함. 비번, 이메일만 변경 가능. 그 외는 불가.
    //remove - accountId / accountId에 해당하는 계정을 탈퇴처리함. 계정에 대한 정보 재요청.=(아이디, 비번 재입력하도록 함)


    //Login id 체크하고,
    public static boolean Login(Session session) {
        Scanner sc = new Scanner(System.in);
        System.out.print("아이디를 입력하세요 : ");
        String id = sc.nextLine();
        System.out.print("비밀번호를 입력하세요 : ");
        String password = sc.nextLine();
        if(!AccountsBox.isvalididAccount(id, password)){
            return false;
        }
        session.sessionOn(AccountsBox.getAccountsList().get(id));
        System.out.println("성공적으로 로그인 됐습니다.");
        return true;
    }

}
