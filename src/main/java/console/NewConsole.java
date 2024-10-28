package console;


import Container.Container;
import account.AccountsBox;
import account.AdminAccount;
import annotation.Mapping;
import command.Command;
import console.Session.Session;
import command.AccountCommand;
import command.BoardCommand;
import command.PostsCommand;
import console.reQuest.Request;
import contents.Board;
import contents.BoardBox;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;




public class NewConsole {

    public static void main(String[] args) {
        //초기화 <- 추후 컨테이너에서 운용하면 좋을듯.
        Scanner sc = new Scanner(System.in); //스캐너헤헤 ㅋㅋ
        BoardBox boardBox = new BoardBox(); // 보드박스 만들기
        Board DefaultBoard = new Board("DefaultBoard"); //디폴트 보드임.
        Session nowsession = new Session(); // 이 콘솔에 로그인한 계정의 정보를 담을 세션 생성.
        nowsession.defaultsessionSet();//세션 초기화
        AccountsBox accountsBox = new AccountsBox(); //계정들이 담길 곳.
        List<Command> commandList = new ArrayList<>();
        Container container = new Container();
        commandList.add((BoardCommand) container.getTest(BoardCommand.class));
        commandList.add((AccountCommand) container.getTest(AccountCommand.class));
        commandList.add((PostsCommand) container.getTest(PostsCommand.class));


        //레벨(0 : admin, 1 : User, 2 :손님)
//        AdminAccount account = new AdminAccount("admin","admin","SUPERPOWER1234@god.god");
//        accountsBox.setAccount(account);

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

            //종료,도움!
            if(urlcommand.equals("exit")){consoleExit();}
            if(urlcommand.equals("help")){help();}

            //스플릿, 유효성검사를 마친 객체를 받을 변수 생성/캐스팅
            InputInspection.URLSplitDone splitDone;
            splitDone = InputInspection.inputInspection(urlcommand);

            //SplitDone이 Null이면 유효성 검사에 실패한 것.
            if(splitDone == null){continue;}

            //Request 객체 생성
            Request request = new Request(nowsession,splitDone);

            //Request 객체에 현재 콘솔의 정보를 넣어준다. <- 추후 세션에 포함시키면 좋을듯.
            request.setBoardBox(boardBox);
            request.setNowboardList(nowBoardList);
            request.setAccountsBox(accountsBox);

            //메서드 호출(리플렉션)
            for(Command command : commandList) { //인터페이스 어노테이션을 상속받은 클래스들로 만든 각 인스턴스를 호출한다.(호출된건 컨테이너에 있다)
                for (Method method : command.getClass().getDeclaredMethods()) { //해당인스턴스의 클래스정보를 받고, 거기서 정의된 메서드를 순회한다. (위 아래의 순서성은 없다)
                    if (method.isAnnotationPresent(Mapping.class)) { //지금 순회하고있는 메소드가 Mapping.class 어노테이션이 붙어있는지 확인함
                        if (method.getAnnotation(Mapping.class).value().equals(splitDone.getmergeCommand())) { //어노테이션이 붙어있다면 그 메소드에 붙은 어노테이션의 값이, getmergeCommand 값이랑 일치한지 확인.
                            try {
                                /*
                                그 메소드(method)를 호출(invoke)한다. public Object invoke(Object obj, Object... args) 해당 인자를 나열해서 넘겨도 되고,
                                Object[] params = {5, "Hello", true}; 이런 식으로 뭉쳐서 넘겨줘도 된다.
                                 */
                                method.invoke(command, request);
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
            }


        }
    }


/*
            //request의 정보를 가지고 해당되는 메서드 실행. 모든 메서드는 request를 받는다.
            try {
                switch (request.getClassification()) {
                    case "accounts":
                        switch (request.getCommand()) {
                            case "signin":
                                AccountCommand.login(request);
                                break;
                           case "signup":
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
                                AccountCommand.help(request);
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
                                BoardCommand.boardSet(request);
                                break;
                            case "list":
                                BoardCommand.boardList(request);
                                break;
                            case "help":
                                BoardCommand.help(request);
                                break;
                        }
                        break;
                    case "posts":
                        switch (request.getCommand()) {
                            case "add":
                                PostsCommand.addPost(request);
                                break;
                            case "edit":
                                PostsCommand.editPost(request);
                                break;
                            case "remove":
                                PostsCommand.remove(request);
                                break;
                            case "get":
                                PostsCommand.lookPost(request);
                                break;
                            case "help":
                                PostsCommand.help(request);
                                break;
                        }
                        break;
                }
            }catch (NullPointerException e){
                System.out.println("존재하지 않는 파라미터 값을 입력했습니다.");

            }

            */



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

    //help 구현
    static void help(){
        System.out.print("""
                                        /accounts/help : 계정관련한 명령어를 봅니다.
                                        /boards/help : 게시물을 수정합니다. 수정한 시간이 찍힙니다.
                                        /posts/help : 해당 번호의 게시물을 삭제합니다.
                                        exit : 콘솔을 종료합니다.""");
    }

}

