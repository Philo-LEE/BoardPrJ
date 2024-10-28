package account;

import java.util.HashMap;

public class AccountsBox {

    //계정 정보들이 담길 곳.
    private static HashMap<String, Account> AccountsList = new HashMap<>();

    //Account 객체 반환 게터
    public Account getAccount(String id) {
        return this.AccountsList.get(id);
    }

    //Account 세터
    public void setAccount(Account account) {
        AccountsList.put(account.getAccountID(),account);
    }

    //해시맵 전체를 반환하는 게터
    public static HashMap<String, Account> getAccountsList() {
        return AccountsList;
    }

    //계정이 실제로 있는지(로그인용)
    public static boolean isvalididAccount(String id, String password) {
        if(!isvalidid(id)){
            System.out.println("계정이 없습니다.");
            return false;
        }else if(!isvalidPw(id,password)){
            System.out.println("비밀번호가 틀렸습니다.");
            return false;
        }

        return true;
    }

    //아이디만 확인(회원가입용)
    public static boolean isvalidid(String id) {
        return AccountsBox.getAccountsList().containsKey(id);
    }

    //비밀번호 확인(아이디 필요)
    public static boolean isvalidPw(String id, String password) {
        return AccountsBox.getAccountsList().get(id).checkPw(password);
    }
}
