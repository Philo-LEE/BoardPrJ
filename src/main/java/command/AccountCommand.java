package command;

import account.AccountsBox;
import account.NomalAccount;
import annotation.Mapping;
import console.Session.Session;
import console.reQuest.Request;

import java.util.Scanner;

public class AccountCommand implements Command {

    @Mapping(value = "/accounts/signin")
    public static void login(Request request) {

        Session session = request.getSession();

        if(session.getisLogIn()) {
            System.out.println("이미 로그인되어있습니다. 로그아웃 후 다시 시도해 주세요");
            return;
        }
        Scanner sc = new Scanner(System.in);
        System.out.print("아이디를 입력하세요 : ");
        String id = sc.nextLine();
        System.out.print("비밀번호를 입력하세요 : ");
        String password = sc.nextLine();
        if(!AccountsBox.isvalididAccount(id, password)){
            return;
        }
        session.sessionOn(AccountsBox.getAccountsList().get(id));
        System.out.println("성공적으로 로그인 됐습니다.");

    }

    //signout 로그아웃(로그인 상태가 아닌데 로그아웃요청시 예외처리)
    @Mapping(value = "/accounts/signout")
    public static void logout(Request request) {
        Session session = request.getSession();
        if(!session.getisLogIn()) {
            System.out.println("로그인되어있지 않습니다.");
            return;
        }
        session.defaultsessionSet();
        System.out.println("성공적으로 로그아웃 되었습니다.");
    }

    //signup 회원등록
    @Mapping(value = "/accounts/signup")
    public static void signIn(Request request){
        Session session = request.getSession();

        if(session.getisLogIn()) {
            System.out.println("로그인상태에서는 회원을 만들 수 없습니다.");
            return;
        }
        Scanner sc = new Scanner(System.in);
        System.out.print("아이디를 입력하세요 : ");
        String id = sc.nextLine();
        if(AccountsBox.isvalidid(id)){
            System.out.println("이미 등록된 아이디가 있습니다.");
            return;
        }
        System.out.print("비밀번호를 입력하세요 : ");
        String pw = sc.nextLine();
        System.out.print("이메일을 입력하세요 : ");
        String email = sc.nextLine();

        NomalAccount newAccount = new NomalAccount(id, pw, email);

        AccountsBox.getAccountsList().put(newAccount.getAccountID(),newAccount);
        System.out.println("정상적으로 등록되었습니다. 로그인 해주세요");
    }

    //detail - accountId / accountId에 해당하는 계정정보 조회해서 뿌려줌 accountId 회원 : 계정/ 이메일/ 가입일
    @Mapping(value = "/accounts/detail")
    public static void detail(Request request) {
        Session session = request.getSession();
        String id = request.getParamValue();


        String[] introduce = AccountsBox.getAccountsList().get(id).letMeIntroduce();

        System.out.printf(
                """
                        계정 권한 레벨 : %d
                        계정 생성번호 : %s
                        계정 ID : %s
                        계정 email : %s
                        계정 가입일 : %s
                        """,
                AccountsBox.getAccountsList().get(id).checkMyAccountLevel(), introduce[0],introduce[1],introduce[2],introduce[3]);

    }

    //edit - accountId / accountId에 해당하는 계정의 정보를 변경함. 비번, 이메일만 변경 가능. 그 외는 불가.
    @Mapping(value = "/accounts/edit")
    public static void edit(Request request) {

        Session session = request.getSession();
        String id = request.getParamValue();

        if(!AccountsBox.getAccountsList().get(id).getAccountID().equals(id)){
            System.out.println("해당 ID의 유저는 없습니다.");
            return;
        }

        System.out.println("계정정보를 수정합니다.(해당항목을 변경하고 싶지 않을경우 엔터만 치시면 됩니다.");
        Scanner sc = new Scanner(System.in);

        System.out.print("새로운 이메일 주소를 입력하세요.");
        String newEmail = sc.nextLine();
        if (!newEmail.isEmpty()){
            AccountsBox.getAccountsList().get(id).setEmail(newEmail);
            System.out.println("이메일이 변경되었습니다.");
        }

        System.out.print("새로운 비밀번호를 입력하세요.");
        String newPw = sc.nextLine();
        if (!newPw.isEmpty()){
            AccountsBox.getAccountsList().get(id).setAccountPW(newPw);
            System.out.println("비밀번호가 변경되었습니다.");
        }

        if(newEmail.isEmpty()&&newPw.isEmpty()){
            System.out.println("변경하지 않습니다.");
        }

    }

    //remove - accountId / accountId에 해당하는 계정을 탈퇴처리함. 계정에 대한 정보 재요청.=(아이디, 비번 재입력하도록 함)
    @Mapping(value = "/accounts/remove")
    public static void remove(Request request) {
        Session session = request.getSession();
        String id = request.getParamValue();

        if(!AccountsBox.getAccountsList().containsKey(id)){
            System.out.println("삭제할 아이디가 존재하지 않습니다.");
            return;
        }

        AccountsBox.getAccountsList().remove(id);
        System.out.println("삭제완료");

    }

    @Mapping(value = "/accounts/help")
    public static void help(Request request) {
        System.out.print("""
                                                /accounts/signup : 로그인 기능. 첫 방문자는 계정을 만들어주세요.
                                                /accounts/signin : 회원가입 기능입니다.
                                                /accounts/signout : 로그아웃 합니다.
                                                /accounts/detail?accountId =『아이디』 : 해당 아이디의 정보를 조회합니다.
                                                /accounts/edit?accountId =『아이디』 : 해당 아이디의 정보를 수정합니다.
                                                /accounts/remove?accountId =『아이디』 : 해당 아이디의 정보를 삭제합니다.
                                                """);
    }



}

