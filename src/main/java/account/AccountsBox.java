package account;

import java.util.HashMap;

public class AccountsBox {

    //계정 정보들이 담길 곳.
    private static HashMap<String, Account> AccountsList = new HashMap<>();

    public Account getAccount(String id) {
        return this.AccountsList.get(id);
    }

    public void setAccount(Account account) {
        AccountsList.put(account.getAccountID(),account);
    }

    public static HashMap<String, Account> getAccountsList() {
        return AccountsList;
    }

    public static boolean isvalididAccount(String id, String password) {
        if(!AccountsBox.getAccountsList().containsKey(id)){
            System.out.println("계정이 없습니다.");
            return false;
        }
        if(!AccountsBox.getAccountsList().get(id).checkPw(password)){
            System.out.println("비밀번호가 틀렸습니다.");
            return false;
        }

        return true;
    }
}
