package console.reQuest;

import console.InputInspection;
import console.Session.Session;
import contents.BoardBox;

import java.util.HashMap;

public class Request {
    private Session session;
    private InputInspection.URLSplitDone splitedURL;
    private HashMap<Integer, String[]> nowboardList;
    private BoardBox boardBox;
    private boolean isLogin;

    public Request(Session session, InputInspection.URLSplitDone splitedURL) {
        this.session = session;
        this.splitedURL = splitedURL;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public InputInspection.URLSplitDone getSplitedURL() {
        return splitedURL;
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

    public String getParamValue(){
        return this.splitedURL.getParamsHash().get(this.splitedURL.getParamsHash().keySet().iterator().next());
    }

    public HashMap<Integer, String[]> getNowboardList() {
        return nowboardList;
    }

    public void setNowboardList(HashMap<Integer, String[]> nowboardList) {
        this.nowboardList = nowboardList;
    }

    public BoardBox getBoardBox() {
        return boardBox;
    }

    public void setBoardBox(BoardBox boardBox) {
        this.boardBox = boardBox;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }
}

