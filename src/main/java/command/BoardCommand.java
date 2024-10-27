package command;

import annotation.Mapping;
import console.reQuest.Request;
import contents.Board;
import contents.BoardBox;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class BoardCommand implements Command {

        //add
        @Mapping(value = "/boards/add")
        public static void newBoard(Request request) {
            BoardBox boardBox = request.getBoardBox();
            String boardName = request.getParamValue();
            Board createdBoard = new Board(boardName);

            //Filter
            if(!PermissionCheck(request)) return;


            if (!Board.getNameToIndex().containsKey(request.getParamValue())) {
                System.out.println("에러! 이름이 중복됩니다.");
                return;
            }

            System.out.print("보드가 성공적으로 생성되었습니다.\n" +
                    "번호 : " +createdBoard.getBoardIndex()+ "\n" +
                    "이름 : " +createdBoard.getBoardName()+"\n" +
                    "작성자 : " + request.getSession().getLoginId() + "\n");

            boardBox.putboardList(createdBoard.getBoardIndex(), createdBoard);
        }

        //remove
        @Mapping(value = "/boards/remove")
        public static void removeBoard(Request request){

            //Filter
            if(!PermissionCheck(request)) return;

            Integer boardIndex = Integer.valueOf(request.getParamValue());
            BoardBox boardBox = request.getBoardBox();

            boardBox.getBoardList().remove(boardIndex);
            System.out.println("성공적으로 삭제했습니다.");
            boardList(request);
        }

        //board view 원래 post기능이었음
        @Mapping(value = "/boards/view")
        public static void postList(Request request){
            Integer boardIndex = Board.getNameToIndex().get(request.getParamValue());
            HashMap<Integer, String[]> postBoard = request.getBoardBox().getBoard(boardIndex).getContents();

            System.out.println("총 게시글은 "+ postBoard.size() +"개 입니다.\n");

            //Board의 키셋 받고 정렬 후 for문 돌기
            //인덱스 담을 배열 선언.
            Integer[] indexSet  = new Integer[postBoard.size()];

            //indexSet에 차례대로 담겨야 하므로 j 선언,(for -each)에서는 j가 추가선언이 되지 않는다.ㅠ
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

                System.out.println("글 번호    |글 제목  |작성일");
                System.out.printf("""
            %s     |%s    |%s
            """,tempPost[0],tempPost[1],tempPost[4]);

            }
        }

        //list
        @Mapping(value = "/boards/list")
        public static void boardList(Request request){

            BoardBox boardBox = request.getBoardBox();
            Integer[] boardIndex = boardBox.getBoardIndexList();
            Board tempBoard;

            System.out.println("------------------------");

            for(Integer i : boardIndex){
                tempBoard = boardBox.getBoard(i);

                System.out.println("Board Id :" + i + " | BoardName : " + tempBoard.getBoardName() );

            }

            System.out.println(" ---------마지막--------- ");

        }

        //edit
        @Mapping(value = "/boards/edit")
        public static void editBoard(Request request){

            Integer boardIndex = Integer.valueOf(request.getParamValue());
            BoardBox boardBox = request.getBoardBox();

            //Filter
            if(!PermissionCheck(request)) return;

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

        //set <- 이건 걍 콘솔에서 구현해도 될듯... 회원인증기능까지 추가된다면 몰라..
        @Mapping(value = "/boards/help")
        public static void help(Request request){
            System.out.print("""
                /boards/add : 현재 게시판에 게시물을 생성합니다.
                /boards/remove?boardID= 『번호』 : 해당 번호의 게시판을 삭제합니다.
                /boards/edit?boardID = 『번호』 : 해당 번호의 게시판을 수정합니다.
                /boards/set?boardId = 『번호』 : 해당 번호의 게시판을 불러옵니다.
                /boards/view?boardName =『이름』 : 해당 이름의 게시판 내용을 요약해서 봅니다.
                /boards/list : 게시판의 목록을 봅니다.
                /boards/help : 게시판 사용법을 봅니다.
                """);
        }

        @Mapping(value = "/boards/set")
        public static void boardSet(Request request){
            BoardBox boardBox = request.getBoardBox();
            request.setNowboardList(boardBox.getBoard(Integer.valueOf(request.getParamValue())).getContents());
        }

        private static boolean PermissionCheck(Request request){
            //Filter
            if(!request.getSession().getisLogIn()&&request.getSession().getUserLevel()!=0){
                return false;
            }

            return true;
        }


}













