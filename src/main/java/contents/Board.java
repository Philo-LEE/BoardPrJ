package contents;

import java.util.HashMap;

public class Board {
    private static Integer boardIndex = 1;
    private static Integer postIndex = 1;
    //각 보드에 할당되는 애들 만큼 인덱스를 정해줌.
    //게시물의 Index != 보드의 postIndex

    private String boardName;
    private Integer index = 0;
    private HashMap<Integer, String[]> contents = new HashMap<>();
    private static HashMap<String,Integer> nameToIndex = new HashMap<>();
    private String createdBy;



    //생성자
    public Board(String boardName) {
        this.boardName = boardName;
        this.index = boardIndex++;

        //이름과 보드넘버를 매칭하는 해시테이블에 값 추가.
        this.nameToIndex.put(boardName,this.index);
    }


    //보드이름 반환
    public String getBoardName() {
        return boardName;
    }

    //보드 인덱스 반환
    public Integer getBoardIndex() {
        return index;
    }

    //보드 째로 반환.
    public HashMap<Integer, String[]> getContents() {
        return contents;
    }

    //보드에 요소 삽입.
    public void put(String[] content) {
        this.contents.put(postIndex++, content);
    }

    //보드이름 정하기
    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    //보드이름 : 보드 인덱스 맵 반환
    public static HashMap<String, Integer> getNameToIndex() {
        return nameToIndex;
    }

    //보드이름 : 보드 인덱스 맵 추가.
    public void editNameToIndex(String newName,String oldName, Integer index) {

        Board.nameToIndex.put(newName, index);
    }

    //누가 보드 만들었는지 알려줌.
    public String getCreatedBy() {
        return createdBy;
    }

    //누가 보드만드는 소리를 내었어 누구인가 누구인가 누구인가
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
