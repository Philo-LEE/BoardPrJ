package console;


import account.AccountsBox;
import account.AdminAccount;
import console.Session.Session;
import command.AccountCommand;
import command.BoardCommand;
import command.PostsCommand;
import console.reQuest.Request;
import contents.Board;
import contents.BoardBox;

import java.util.HashMap;
import java.util.Scanner;


public class NewConsole {

    public static void main(String[] args) {
        //초기화
        Scanner sc = new Scanner(System.in); //스캐너헤헤 ㅋㅋ
        BoardBox boardBox = new BoardBox(); // 보드박스 만들기
        Board DefaultBoard = new Board("DefaultBoard"); //디폴트 보드임.
        Session nowsession = new Session(); // 이 콘솔에 로그인한 계정의 정보를 담을 세션 생성.
        nowsession.defaultsessionSet();//세션 초기화
        AccountsBox accountsBox = new AccountsBox(); //계정들이 담길 곳.
        String[] nowpost;//만들어진 post가 돌아오는 공간.

        //레벨(0 : admin, 1 : User, 2 :손님)
        AdminAccount account = new AdminAccount("admin","admin","SUPERPOWER1234@god.god");
        accountsBox.setAccount(account);

        /*설정한 보드의 실제 상태가 여기에 캐스팅됨. 초기값은 defaultboard로 설정된다.
        posts 명령어에서 boardId를 받지 않고 postId만 받기 때문에 항상 콘솔에서 board가 있어야한다.
        그리고 일반유저는 보드를 만들 권한이 없으므로 미리 하나가 있어야한다.*/
        HashMap<Integer, String[]> nowBoardList = DefaultBoard.getContents();
        boardBox.putboardList(DefaultBoard.getBoardIndex(),DefaultBoard);

        while(true){
            //현재세션의 로그인 아이디가 표시.
            System.out.print(nowsession.getLoginId()+ ">");

            //입력받기
            String urlcommand = sc.nextLine();
            urlcommand = urlcommand.trim();

            //종료면 종료
            if(urlcommand.equals("exit")){consoleExit();}

            //스플릿, 유효성검사를 마친 객체를 받을 변수 생성/캐스팅
            InputInspection.URLSplitDone splitDone;
            splitDone = InputInspection.inputInspection(urlcommand);

            //SplitDone이 Null이면 유효성 검사에 실패한 것.
            if(splitDone == null){continue;}

            //Request 객체 생성
            Request request = new Request(nowsession,splitDone);

            //Request 객체에 현재 콘솔의 정보를 넣어준다.
            request.setBoardBox(boardBox);
            request.setNowboardList(nowBoardList);

            //request의 정보를 가지고 해당되는 메서드 실행. 모든 메서드는 request를 받는다.
            try {
                switch (request.getClassification()) {
                    case "accounts":
                        switch (request.getCommand()) {
                            case "signup":
                                AccountCommand.login(request);
                                break;
                            case "signin":
                                AccountCommand.signIn(request);
                                break;
                            case "signout":
                                AccountCommand.logout(request);
                                break;
                            case "detail":
                                AccountCommand.detail(request);
                                break;
                            case "edit":
                                AccountCommand.edit(request);
                                break;
                            case "remove":
                                AccountCommand.remove(request);
                                break;
                            case "help":
                                System.out.print("""
                                                /accounts/signup : 로그인 기능. 첫 방문자는 계정을 만들어주세요.
                                                /accounts/signin : 회원가입 기능입니다.
                                                /accounts/signout : 로그아웃 합니다.
                                                /accounts/detail?accountId =『아이디』 : 해당 아이디의 정보를 조회합니다.
                                                /accounts/edit?accountId =『아이디』 : 해당 아이디의 정보를 수정합니다.
                                                /accounts/remove?accountId =『아이디』 : 해당 아이디의 정보를 삭제합니다.
                                                """);
                                break;
                        }
                        break;
                    case "boards":
                        switch (request.getCommand()) {

                            case "edit":
                                BoardCommand.editBoard(request);
                                break;
                            case "remove":
                                BoardCommand.removeBoard(request);
                                break;
                            case "add":
                                BoardCommand.newBoard(request);
                                break;
                            case "view":
                                BoardCommand.postList(request);
                                break;
                            case "set":
                                nowBoardList = boardBox.getBoard(Integer.valueOf(request.getParamValue())).getContents();
                                break;
                            case "list":
                                BoardCommand.boardList(request);
                                break;
                            case "help":
                                System.out.print("""
                                                /boards/add : 현재 게시판에 게시물을 생성합니다.
                                                /posts/edit?PostID=『번호』 : 게시글을 수정합니다. 수정한 시간이 추가됩니다. 번호는 현재 설정되어있는 게시판의 게시글 번호입니다.
                                                /posts/remove?PostID=『번호』 : 해당 번호의 게시판의 게시물을 삭제합니다.
                                                /posts/get?PostID=『번호』 : 해당 번호의 게시판의 게시물을 조회합니다.""");
                                break;
                        }
                        break;
                    case "posts":
                        switch (request.getCommand()) {
                            case "add":
                                if(!(boardBox.getBoardList().containsKey(Integer.valueOf(request.getParamValue())))){
                                    System.out.println("해당 보드는 만들어지지 않았습니다. /boards/list를 통해 보드를 확인하세요.");
                                    break;
                                }
                                nowpost = PostsCommand.addPost(request); //1은 보드번호
                                boardBox.getBoard(Integer.valueOf(request.getParamValue())).put(nowpost);
                                break;
                            case "edit":
                                request.setNowboardList(nowBoardList);
                                nowpost = PostsCommand.editPost(request);
                                if (nowpost == null) {
                                    System.out.println("수정할 게시물이 없습니다.");
                                    break;}
                                //해당 postId가 있던 게시판으로 할당되어야 하므로.
                                boardBox.getBoard(Integer.valueOf(request.getParamValue())).put(nowpost);
                                break;
                            case "remove":
                                request.setNowboardList(nowBoardList);
                                PostsCommand.remove(request);
                                break;
                            case "get":
                                request.setNowboardList(nowBoardList);
                                PostsCommand.lookPost(request);
                                break;
                            case "help":
                                System.out.print("""
                                                /posts/add : 현재 게시판에 게시물을 생성합니다.
                                                /posts/edit?PostID=『번호』 : 게시글을 수정합니다. 수정한 시간이 추가됩니다. 번호는 현재 설정되어있는 게시판의 게시글 번호입니다.
                                                /posts/remove?PostID=『번호』 : 해당 번호의 게시판의 게시물을 삭제합니다.
                                                /posts/get?PostID=『번호』 : 해당 번호의 게시판의 게시물을 조회합니다.""");
                                break;
                        }
                        break;

                    case "help":
                        System.out.print("""
                                        /accounts/help : 계정관련한 명령어를 봅니다.
                                        /boards/help : 게시물을 수정합니다. 수정한 시간이 찍힙니다.
                                        /posts/help : 해당 번호의 게시물을 삭제합니다.
                                        exit : 콘솔을 종료합니다.""");
                        break;
                }
            }catch (NullPointerException e){
                System.out.println("존재하지 않는 파라미터 값을 입력했습니다.");
            }
        }
    }

    //exit 구현
    static void consoleExit(){
        System.out.println("3초 후 프로그램을 종료합니다.");
        try {
            Thread.sleep(1000);
            System.out.println("2초...후 프로그램을 종료합니다.");
            Thread.sleep(1000);
            System.out.println("1초...후 프로그램을 종료합니다.");
            Thread.sleep(1000);
            System.out.println("종료합니다.");
        } catch (InterruptedException e) {
            System.exit(0);
        } finally {
            System.exit(0);
        }
    }
}

