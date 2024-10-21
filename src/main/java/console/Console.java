package console;


import account.Account;
import account.DefaultAccount;

import java.text.SimpleDateFormat;
import java.util.*;

public class Console {

    static int index =1;


    public static void main(String[] args) {
        //-> 명령어(각 사용자들마다 사용할 수 있는 명령어가 다름.)
        //status 레벨(0 : admin, 1 : User, 2 :anonymous)
        DefaultAccount user = new DefaultAccount("","");
        int userLevel = user.CheckMyAccountLevel();
        String userstatus = (userLevel==0) ? "admin" : (userLevel==1) ? "user" : "anonymous";
        new DefaultAccount("","");//기본 손님 유저 생성


        //구현부
        // 출력될 post가 잠시 머무를 곳.
//        ArrayList<String> lastpost = new ArrayList<>(); 이제 안쓸듯
        String[] lastpost;


        //post가 저장될 게시글 목록
        HashMap postBoard = new HashMap<Integer, String[]>(); // post가 저장될 곳.


        while(true){
            System.out.print(userstatus + ">");
            Console testuser = new Console(); //콘솔 인스턴스 생성. 콘솔 주체 / user가 될듯.// Develop 한다면 여기 testuser에 Login 기능 만들어야할듯.


            //입력
            try {
                switch (inputInspection()) {
                    case "add":
                        lastpost = testuser.addPost(); // 마지막으로 건든파일
                        postBoard.put(Integer.valueOf(lastpost[0]), lastpost);
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

        }

    }



    //Board에 저장하면 Index가 어떻게 추가될지.




    //Console 입력 구현
    //명령의 유효성검사진행.
    static String inputInspection() {
        Scanner sc = new Scanner(System.in);
        String command = sc.nextLine();
        command = command.trim();


        /* 커맨드 리스트(유효성검사)
        add : addpost 호출(return ArryList<String>) \n
        edit : 수정 - 미구현 \n
        remove : 삭제 - 미구현 \n
        lookPost : 조회 - 미구현 \n
        board : board 진입 \n
        exit : 콘솔종료 \n
         */
        String[] commandList = {"add","edit","remove","get","exit","board","list","help"};

        if(command.equals("help")){
            System.out.print(
                    "add : 게시물을 생성합니다. \n" +
                    "edit : 해당 번호의 게시물을 수정합니다. 수정한 시간이 찍힙니다. \n" +
                    "remove : 해당 번호의 게시물을 삭제합니다. \n" +
                    "lookPost : 해당 번호의 게시물을 조회합니다.  \n" +
                    "board : board 진입 - 미구현\n" +
                    "exit : 콘솔을 종료합니다. \n");
            }


        for(String tempList: commandList){
            if(command.equals(tempList)){
                return command;
            }
        }

        return "";

    }

    //Hash맵 생성기(Board만들기)
//    static

    //게시글 add 구현
    public String[] addPost(){

        //게시물 인덱스
        String[] post = new String[6];
        post[0] = String.valueOf(index);
        index++;
        Scanner sc = new Scanner(System.in);

        //title
        System.out.print("제목을 입력하세요 : ");
        String title = sc.nextLine();
        post[1] = title;

        //text
        System.out.print("내용을 입력하세요 : ");
        String text = sc.nextLine();
        post[2] = text;

        //게시판 Num : 미구현
        post[3] = null;

        //작성일
        Date now = new Date();
        SimpleDateFormat timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        post[4]=timeStamp.format(now);



        //수정일 수정할 때 값이 채워지므로.
        post[5] = "수정되지 않음";
        return post;
    }

    //게시글 lookPost 구현
    static void lookPost(HashMap<Integer, String[]> postBoard){
        System.out.print("게시글 몇 번을 조회할까요?");
        Scanner sc = new Scanner(System.in);
        String num = sc.nextLine();

        //게시글 넘버 쳌
        if(!postBoard.containsKey(Integer.valueOf(num))){
            System.out.println(num + "번 게시글은 존재하지 않습니다.");
            return;
        }

        String[] tempPost = postBoard.get(Integer.valueOf(num));
        System.out.print("게시물 번호 : " + tempPost[0] +"\n작성일 : " + tempPost[4] + "\n수정일 : " + tempPost[5] + "\n제목 : " + tempPost[1] + "\n내용 : " + tempPost[2] + "\n");

    }

    //게시글 remove 구현
    static void remove(HashMap<Integer, String[]> postBoard){
        System.out.print("게시글 몇 번을 삭제할까요?");
        Scanner sc = new Scanner(System.in);
        String num = sc.nextLine();

        //게시글 넘버 쳌
        if(!postBoard.containsKey(Integer.valueOf(num))){
            System.out.println(num + "번 게시글은 존재하지 않습니다.");
            return;
        }

        //해당보드의 해당 인덱스 삭제.
        postBoard.remove(Integer.valueOf(num));
    }

    //게시글 edit 구현
    static String[] editPost(HashMap<Integer, String[]> postBoard){
        System.out.print("게시글 몇 번을 조회할까요?");
        Scanner sc = new Scanner(System.in);
        String num = sc.nextLine();

        //게시글 넘버 쳌
        if(!postBoard.containsKey(Integer.valueOf(num))){
            System.out.println(num + "번 게시글은 존재하지 않습니다.");
            return null;
        }

        //뽑은 포스트 담길 곳 tempPost
        String[] tempPost = postBoard.get(Integer.valueOf(num));

        System.out.print("게시물 번호 : " + tempPost[0] +"\n작성일 : " + tempPost[4] + "\n수정일 : " + tempPost[5] + "\n제목 : " + tempPost[1] + "\n내용 : " + tempPost[2] + "\n");

        System.out.println("원문------↑ ↓------수정");

        //수정단
        System.out.print("수정할 제목을 입력하세요 : ");
        String title = sc.nextLine();
        tempPost[1] = title;

        System.out.print("수정할 내용을 입력하세요 : ");
        String text = sc.nextLine();
        tempPost[2] = text;

        //타이무 스탬뿌
        Date now = new Date();
        SimpleDateFormat timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        tempPost[5] = timeStamp.format(now);
        return tempPost;
    }

    //board list 구현ㅋㅋㅋㅋㅋ
    static void postList(HashMap<Integer, String[]> postBoard){

        System.out.println("총 게시글은 "+ postBoard.size() +"개 입니다.\n");

        //post Board의 키셋 받고 정렬 후 for문 돌기
        //인덱스 담을 배열 선언.
        Integer[] indexSet  = new Integer[postBoard.size()];

        // indexSet에 차례대로 담겨야 하므로 j 선언,(for -each)에서는 j가 추가선언이 되지 않는다.ㅠ
        int j=0;

        //i에 posrtBoard의 KeySet이 담긴다.(HashMap은 비순서성)
        for(Integer i : postBoard.keySet()){
            //indexSet 배열이 만들어진다.(빈 값은 알아서 안들어온다)
            indexSet[j++] = i;
        }

        // indexSet을 오름차순으로 정렬한다.
        // postBoard의 Index는 오름차순으로 삭제한거 제외하고 담긴다.
        Arrays.sort(indexSet);

        // indexSet의 인덱스를 차례대로 순회. 삭제한 것은 자동으로 넘어간다.
        for(Integer i : indexSet){

            // tempPost의 요소를 출력
            String[] tempPost = postBoard.get(i);

            System.out.print("게시물 번호 : " + tempPost[0] +"\n작성일 : " + tempPost[4] + "\n수정일 : " + tempPost[5] + "\n제목 : " + tempPost[1] + "\n내용 : " + tempPost[2] + "\n\n");

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
