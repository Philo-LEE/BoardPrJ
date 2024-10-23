package console;


import account.Account;
import account.DefaultAccount;
import command.BoardCommand;
import command.PostsCommand;
import contents.Board;
import contents.BoardBox;

import java.util.HashMap;
import java.util.Scanner;


public class NewConsole {

    public static void main(String[] args) {
        //-> 명령어(각 사용자들마다 사용할 수 있는 명령어가 다름.)
        //status 레벨(0 : admin, 1 : User, 2 :anonymous)
        Account user = new DefaultAccount("","","");
        int userLevel =  user.CheckMyAccountLevel();
        String userstatus = (userLevel==0) ? "admin" : (userLevel==1) ? "user" : "anonymous";



        //초기화
        BoardBox boardBox = new BoardBox(); // 보드박스 만들기
        Board DefaultBoard = new Board("DefaultBoard"); //디폴트 보드임.



        // 설정한 보드의 실제 상태가 여기에 캐스팅됨. 초기값은 defaultboard가 담김
        // posts 명령어에서 boardId를 받지 않고 postId만 받기 때문에 항상 콘솔에서 board가 있어야한다.
        HashMap<Integer, String[]> nowBoardList = DefaultBoard.getContents();
        boardBox.putboardList(DefaultBoard.getBoardIndex(),DefaultBoard); // 디폴트 보드를 보드박스안에 넣기.


        //현재 설정된 게시판
        String[] nowpost; //가독성을 위해 만들어진 post가 돌아올 공간.
        String paramsValue; //ParamsValue가 전달됨.


//        HashMap<Integer, String[]> postBoard = boardBox.getBoardList()// post가 저장될 곳 보드 개념.

        //HashMap<Integer, HashMap<Integer, String[]>> boardList = new HashMap<>(); //보드들이 있는 게시판




        Scanner sc = new Scanner(System.in); //스캐너헤헤 ㅋㅋ
        while(true){
            System.out.print(userstatus + ">");

            //입력받기
            String urlcommand = sc.nextLine();
            urlcommand = urlcommand.trim();

            //종료면 종료ㄱ
            if(urlcommand.equals("exit")){consoleExit();}

            //작성중
            //스플릿 명령을 받을 클래스 생성
            InputInspection.URLSplitDone splitDone;



            splitDone = InputInspection.inputInspection(urlcommand);

            //SplitDone이 Null이면(입력부터 문제가 있는 경우. 다시.
            if(splitDone == null){continue;}


            //스플릿클래스의 필드를 캐스팅
            String classification = splitDone.classification;
            String command = splitDone.command;
            HashMap<String, String> paramsHash = splitDone.paramsHash;

            //받아온 명령들을 실제 메소드와 매핑시킨다.

            try {
                switch (classification) {
                    case "accounts":
                        switch (command) {
                            case "signup":
                                break;
                            case "signin":
                                break;
                            case "signout":
                                break;
                            case "detail":
                                break;
                            case "edit":
                                break;
                            case "remove":
                                break;
                            case "help":
                                break;
                        }
                        break;
                    case "boards":
                        switch (command) {
                            case "edit":
                                paramsValue = paramsHash.get("boardId");
                                BoardCommand.editBoard(boardBox,Integer.valueOf(paramsValue));
                                break;
                            case "remove":
                                paramsValue = paramsHash.get("boardId");
                                BoardCommand.removeBoard(boardBox, Integer.valueOf(paramsValue));
                                break;
                            case "add":
                                paramsValue = paramsHash.get("boardName");
                                if (Board.getNameToIndex().containsKey(paramsValue)) {
                                    System.out.println("에러! 이름이 중복됩니다.");
                                    break;
                                }
                                Board tempBoard = BoardCommand.newBoard(paramsValue);
                                boardBox.putboardList(tempBoard.getBoardIndex(), tempBoard);
                                break;
                            case "view":
                                paramsValue = paramsHash.get("boardName");
                                Integer boardIndex = Board.getNameToIndex().get(paramsValue);
                                BoardCommand.postList(boardBox.getBoard(boardIndex).getContents());
                                break;
                            case "set":
                                paramsValue = paramsHash.get("boardId");
                                nowBoardList = boardBox.getBoard(Integer.valueOf(paramsValue)).getContents();
                                break;
                            case "list":
                                BoardCommand.boardList(boardBox);
                                break;
                            case "help":

                                break;
                        }
                        break;
                    case "posts":
                        switch (command) {
                            case "add":
                                paramsValue = paramsHash.get("boardId");
                                if(!(boardBox.getBoardList().containsKey(Integer.valueOf(paramsValue)))){
                                    System.out.println("해당 보드는 만들어지지 않았습니다. /boards/list를 통해 보드를 확인하세요.");
                                    break;
                                }
                                nowpost = PostsCommand.addPost(paramsValue); //1은 보드번호
//                                nowBoardList.put(Integer.valueOf(nowpost[0]), nowpost);
                                boardBox.getBoard(Integer.valueOf(paramsValue)).put(nowpost);
                                break;
                            case "edit":
                                paramsValue = paramsHash.get("postId");
                                nowpost = PostsCommand.editPost(nowBoardList, paramsValue);
                                if (nowpost == null) {
                                    System.out.println("수정할 게시물이 없습니다.");
                                    break;}
                                //해당 postId가 있던 게시판으로 할당되어야 하므로.
                                boardBox.getBoard(Integer.valueOf(paramsValue)).put(nowpost);
                                break;
                            case "remove":
                                paramsValue = paramsHash.get("postId");
                                PostsCommand.remove(nowBoardList, paramsValue);
                                break;
                            case "get":
                                paramsValue = paramsHash.get("postId");
                                PostsCommand.lookPost(nowBoardList, paramsValue);
                                break;
                            case "help":
                                System.out.print(
                                        "/posts/add : 현재 게시판에 게시물을 생성합니다.  \n" +
                                                "/posts/edit?PostID=『번호』 : 게시글을 수정합니다. 수정한 시간이 추가됩니다. 번호는 현재 설정되어있는 게시판의 게시글 번호입니다. \n" +
                                                "/posts/remove?PostID=『번호』 : 해당 번호의 게시판의 게시물을 삭제합니다. \n" +
                                                "/posts/get?PostID=『번호』 : 해당 번호의 게시판의 게시물을 조회합니다.  \n");
                                break;
                        }
                        break;

                    case "help":
                        System.out.print(
                                "/accounts/help : 계정관련한 명령어를 봅니다. \n" +
                                        "/boards/help : 게시물을 수정합니다. 수정한 시간이 찍힙니다. \n" +
                                        "/posts/help : 해당 번호의 게시물을 삭제합니다. \n" +
                                        "exit : 콘솔을 종료합니다.");
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
            System.out.println("2초...");
            Thread.sleep(1000);
            System.out.println("1초...");
            Thread.sleep(1000);
            System.out.println("종료합니다.");
        } catch (InterruptedException e) {
            System.exit(0);
        } finally {
            System.exit(0);
        }
    }
}

