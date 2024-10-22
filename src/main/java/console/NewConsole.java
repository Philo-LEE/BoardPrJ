package console;


import account.DefaultAccount;
import command.PostsCommand;
import java.util.HashMap;
import java.util.Scanner;


public class NewConsole {

    static int index =1;


    public static void main(String[] args) {
        //-> 명령어(각 사용자들마다 사용할 수 있는 명령어가 다름.)
        //status 레벨(0 : admin, 1 : User, 2 :anonymous)
        DefaultAccount user = new DefaultAccount("","");
        int userLevel = user.CheckMyAccountLevel();
        String userstatus = (userLevel==0) ? "admin" : (userLevel==1) ? "user" : "anonymous";



        //구현부
        // 출력될 post가 잠시 머무를 곳.
//        ArrayList<String> lastpost = new ArrayList<>(); 이제 안쓸듯



        //post가 저장될 게시글 목록
        HashMap<Integer, String[]> postBoard = new HashMap<>(); // post가 저장될 곳 보드 개념.
        HashMap<Integer, HashMap<Integer, String[]>> boardList = new HashMap<>(); //보드들이 있는 게시판

        boardList.put(1,postBoard); //디폴트 게시판 생성.


        HashMap<Integer, String[]> nowBoard = postBoard; //현재 설정된 게시판
        String[] nowpost; //가독성을 위해 반환된 post가 돌아올 공간.
        String paramsValue; //ParamsValue가 전달됨.
        Scanner sc = new Scanner(System.in); //스캐너헤헤 ㅋㅋ
        while(true){
            System.out.print(userstatus + ">");
            NewConsole testuser = new NewConsole(); //콘솔핸들러 생성. 콘솔 주체 / user.// Develop 한다면 여기 testuser에 Login 기능 만들어야할듯.

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
                            break;
                        case "remove":
                            break;
                        case "add":
                            break;
                        case "view":
                            break;
                        case "help":
                            break;
                    }
                    break;
                case "posts":
                    switch (command) {
                        case "add":
                            nowpost = PostsCommand.addPost(String.valueOf(1)); //1은 보드번호
                            nowBoard.put(nowpost[0], nowpost);
                            break;
                        case "edit":
                            paramsValue = paramsHash.get("postId");
                            nowpost = PostsCommand.editPost(nowBoard, paramsValue);
                            nowBoard.put(nowpost[0], nowpost);
                            break;
                        case "remove":
                            paramsValue = paramsHash.get("postId");
                            PostsCommand.remove(nowBoard, paramsValue);
                            break;
                        case "get":
                            paramsValue = paramsHash.get("postId");
                            PostsCommand.lookPost(nowBoard, paramsValue);
                            break;
                        case "help":
                            System.out.print(
                                    "/posts/add : 게시물을 생성합니다. \n" +
                                    "/posts/edit?PostID=『번호』 : 게시물을 수정합니다. 수정한 시간이 찍힙니다. \n" +
                                    "/posts/remove?PostID=『번호』 : 해당 번호의 게시물을 삭제합니다. \n" +
                                    "/posts/get?PostID=『번호』 : 해당 번호의 게시물을 조회합니다.  \n");
                            break;
                    }
                    break;

                case "help":
                    System.out.print(
                        "/accounts/help : 계정관련한 명령어를 봅니다. \n" +
                        "/board/help : 게시물을 수정합니다. 수정한 시간이 찍힙니다. \n" +
                        "/posts/help : 해당 번호의 게시물을 삭제합니다. \n"+
                        "exit : 콘솔을 종료합니다.");
                break;
            }




            //작성중
            //입력

            /*


            try {
                switch (InputInspection.inputInspection(urlcommand)) { //inputInspection은 명령을 입력받고, 유효한 명령어를 뱉는다.
                    case "add":
                        lastpost = testuser.addPost(); // 마지막으로 건든파일
                        postBoard.put(Integer.valueOf(lastpost[0]), lastpost);// 받아온
                        break;
                    case "exit":
                        consoleExit();
                        break;
                    case "get":
                        lookPost(postBoard);
                        break;
                    case "remove"://
                        remove(postBoard);
                        break;
                    case "edit":
                        lastpost = editPost(postBoard);
                        postBoard.put(Integer.valueOf(lastpost[0]), lastpost); //마지막
                        break;
                    case "list" :
                        postList(postBoard);
                        break;
                    case "":
                        System.out.println("유효하지 않은 명령어");
                    case "help":
                        break;
                }
            }catch (NullPointerException e){
//                System.out.println("범위를 벗어났습니다.");
                System.out.println("NPE 발생함" + e.getMessage());
            }catch (IndexOutOfBoundsException e){
                System.out.println("유효하지 않은 접근");
            }
            */
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

