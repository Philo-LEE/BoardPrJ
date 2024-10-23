package command;

import contents.Board;
import contents.BoardBox;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class BoardCommand {


        //add
        public static Board newBoard(String boardName){
            Board createdBoard = new Board(boardName);
            System.out.print("보드가 성공적으로 생성되었습니다.\n번호 : " +
                    createdBoard.getBoardIndex()+ "\n이름 : " +
                    createdBoard.getBoardName()+"\n");
            return createdBoard;
        }

        //remove
        public static void removeBoard(BoardBox boardBox,Integer boardIndex){
            boardBox.getBoardList().remove(boardIndex);
            System.out.println("성공적으로 삭제했습니다.");
            boardList(boardBox);
        }


        //board view 구현ㅋㅋㅋㅋㅋ  //-> 보드 기능으로 가야할듯.
        public static void postList(HashMap<Integer, String[]> postBoard){

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



        //list
        public static void boardList(BoardBox boardBox){
            Integer[] boardIndex = boardBox.getBoardIndexList();
            Board tempBoard;

            System.out.println("------------------------");

            for(Integer i : boardIndex){
                tempBoard = boardBox.getBoard(i);

                System.out.println("Board Id :" + i + " | BoardName : " + tempBoard.getBoardName() );

            }

            System.out.println("----------마지막----------");

        }


        //edit
        public static void editBoard(BoardBox boardBox,Integer boardIndex){
            Scanner sc = new Scanner(System.in);
            System.out.print("수정할 이름을 입력하세요 : ");
            //원래 쓰던 보드 넘버 oldBoardName 받아두기.
            String oldBoardName = boardBox.getBoard(boardIndex).getBoardName();
            String newName = sc.nextLine();

            //새로운 이름으로 보드이름 변경.
            boardBox.getBoard(boardIndex).setBoardName(newName);

            //새로운 board와 boardId 맵 업데이트.
            boardBox.getBoard(boardIndex).editNameToIndex(newName,oldBoardName,boardIndex);



        }

        //set







}













