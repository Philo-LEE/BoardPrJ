package command;

import console.reQuest.Request;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;


public class PostsCommand {

    static int index = 1; //게시글이 생성될 때 마다 1 더해짐.

    //게시글 add 구현
    static public String[] addPost (Request request){


        //게시물 인덱스
        String[] post = new String[7];
        post[0] = String.valueOf(index++);
        Scanner sc = new Scanner(System.in);

        //title
        System.out.print("제목을 입력하세요 : ");
        String title = sc.nextLine();
        post[1] = title;

        //text
        System.out.print("내용을 입력하세요 : ");
        String text = sc.nextLine();
        post[2] = text;

        //어디 게시판에 들어있는지
        post[3] = request.getParamValue();

        //작성일
        Date now = new Date();
        SimpleDateFormat timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        post[4]=timeStamp.format(now);

        //수정일 수정할 때 값이 채워지므로.
        post[5] = "수정되지 않음";

        //작성자

        post[6] = request.getSession().getLoginId();

        return post;
    }

    //게시글 get 구현
    static public void lookPost(Request request){

        Integer num = Integer.valueOf(request.getParamValue());


        //게시글 넘버 쳌
        if(!request.getNowboardList().containsKey(num)){
            System.out.println(num + "번 게시글은 존재하지 않습니다.");
            return;
        }

        String[] tempPost = request.getNowboardList().get(num);
        System.out.printf("""
                            게시물 번호 : %s
                            작성일 : %s
                            수정일 : %s
                            제목 : %s
                            내용 :  %s
                            작성자 : %s
                            """, tempPost[0],tempPost[4],tempPost[5],tempPost[1],tempPost[1], tempPost[6]);

    }

    //게시글 remove 구현
    static public void remove(Request request){

        String num = request.getParamValue();

        //게시글 넘버 쳌
        if(!request.getNowboardList().containsKey(Integer.valueOf(num))){
            System.out.println(num + "번 게시글은 존재하지 않습니다.");
            return;
        }



        //해당보드의 해당 인덱스 삭제.
        request.getNowboardList().remove(Integer.valueOf(num));

        //삭제할 때 삭제할 넘버 표시하기
        System.out.println(num+"번 게시물 삭제완료.");
    }

    //게시글 edit 구현
    static public String[] editPost(Request request){
        Scanner sc = new Scanner(System.in);
        Integer num = Integer.valueOf(request.getParamValue());

        //게시글 넘버 쳌
        if(!request.getNowboardList().containsKey(num)){
            System.out.println(num + "번 게시글은 존재하지 않습니다.");
            return null;
        }

        //뽑은 포스트 담길 곳 tempPost
        String[] tempPost = request.getNowboardList().get(num);

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


}
