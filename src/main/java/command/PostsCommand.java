package command;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;


public class PostsCommand {

    static int index = 1; //게시글이 생성될 때 마다 1 더해짐.

    //게시글 add 구현
    static public String[] addPost(String BoardId){

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
        post[3] = BoardId;

        //작성일
        Date now = new Date();
        SimpleDateFormat timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        post[4]=timeStamp.format(now);

        //수정일 수정할 때 값이 채워지므로.
        post[5] = "수정되지 않음";
        return post;
    }

    //게시글 get 구현
    static public void lookPost(HashMap<Integer, String[]> postBoard,String boardId){

        String num = boardId;


        //게시글 넘버 쳌
        if(!postBoard.containsKey(Integer.valueOf(num))){
            System.out.println(num + "번 게시글은 존재하지 않습니다.");
            return;
        }

        String[] tempPost = postBoard.get(Integer.valueOf(num));
        System.out.print("게시물 번호 : " + tempPost[0] +"\n작성일 : " + tempPost[4] + "\n수정일 : " + tempPost[5] + "\n제목 : " + tempPost[1] + "\n내용 : " + tempPost[2] + "\n");

    }

    //게시글 remove 구현
    static public void remove(HashMap<Integer, String[]> postBoard,String PostId){

        String num = PostId;

        //게시글 넘버 쳌
        if(!postBoard.containsKey(Integer.valueOf(num))){
            System.out.println(num + "번 게시글은 존재하지 않습니다.");
            return;
        }

        //해당보드의 해당 인덱스 삭제.
        postBoard.remove(Integer.valueOf(num));
    }

    //게시글 edit 구현
    static public String[] editPost(HashMap<Integer, String[]> postBoard,String PostId){
        Scanner sc = new Scanner(System.in);
        String num = PostId;

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


}
