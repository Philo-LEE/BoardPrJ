package console.reQuest;

import account.AccountsBox;
import console.InputInspection;
import console.Session.Session;
import contents.BoardBox;

import java.util.HashMap;

public class Request {
    private Session session;
    private InputInspection.URLSplitDone splitedURL;
    private HashMap<Integer, String[]> nowboardList;
    private BoardBox boardBox;
    private AccountsBox accountsBox;

    //생성자
    public Request(Session session, InputInspection.URLSplitDone splitedURL) {
        this.session = session;
        this.splitedURL = splitedURL;
    }

    //게터
    public Session getSession() {
        return session;
    }

    public String getParamValue(){
        return this.splitedURL.getParamsHash().get(this.splitedURL.getParamsHash().keySet().iterator().next());
    }

    public HashMap<Integer, String[]> getNowboardList() {
        return nowboardList;
    }

    public BoardBox getBoardBox() {
        return boardBox;
    }

    public AccountsBox getAccountsBox() {
        return accountsBox;
    }

    //세터
    public void setNowboardList(HashMap<Integer, String[]> nowboardList) {
        this.nowboardList = nowboardList;
    }

    public void setBoardBox(BoardBox boardBox) {
        this.boardBox = boardBox;
    }

    public void setAccountsBox(AccountsBox accountsBox) {
        this.accountsBox = accountsBox;
    }

    /* 어노테이션 이후 사용 안함.
    public InputInspection.URLSplitDone getSplitedURL() {
        return splitedURL;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void setSplitedURL(InputInspection.URLSplitDone splitedURL) {
        this.splitedURL = splitedURL;
    }

    public String getClassification(){
        return this.splitedURL.getClassification();
    }

    public String getCommand(){
        return this.splitedURL.getCommand();
    }

    public String getPram(){
        return this.splitedURL.getParamsHash().keySet().iterator().next();
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }
*/

}


