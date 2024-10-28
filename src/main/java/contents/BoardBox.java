package contents;

import java.util.Arrays;
import java.util.HashMap;

public class BoardBox {

    HashMap<Integer, Board> boardList = new HashMap<>(); //보드들이 있는 게시판

    //보드 게터
    public Board getBoard(Integer id) {
        return boardList.get(id);
    }

    //보드 인덱스 정렬 후 반환.
    public Integer[] getBoardIndexList() {

        Integer[] indexSet  = new Integer[this.boardList.size()];
        int j=0;

        for(Integer i : boardList.keySet()){
            //indexSet 배열이 만들어진다.(빈 값은 알아서 안들어온다)
            indexSet[j++] = i;
        }
        Arrays.sort(indexSet);


        return indexSet;
    }

    //보드박스 세터
    public void putboardList(Integer boardId, Board boardList) {
        this.boardList.put(boardId, boardList);
    }

    //보드박스 게터
    public HashMap<Integer, Board> getBoardList() {
        return boardList;
    }

}
