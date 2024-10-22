package command;

import java.util.Arrays;
import java.util.HashMap;

public class BoardCommand {

    static int BoardIndex = 1;

    //board list 구현ㅋㅋㅋㅋㅋ  //-> 보드 기능으로 가야할듯.
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

}
