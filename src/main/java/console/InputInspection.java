package console;

import java.util.*;

public class InputInspection {

    //해당 클래스를 리팩토링할 것.
    //현재 클래스의 역할은 입력을 확인(검사)하고 올바른 값을 Console로 넘기는 것.
     /*
        전처리 되지 않은 명령어 받음.(trim만 진행되어있음)
        분류 체크.
        명령어 체크. (어쨌든 명령어로 돌아가니까.)
        유효하지 않은 URL은 여기 메소드에서 에러를 뽑을 것.
        유효하지 않은 URL:
                시작이 "/"이 아닐 때,
                해당하는 분류가 없을 때,
                해당하는 명령어가 없을 때, 그렇다면 위의 inputInspection()은 명령어 검사기가 되겠군(당연하지 ;;).
                지금 이 메서드는 분류체크와 해당 분류의 메서드가 유효한지 판단하고 넘기는 단.
                보안검사는 여기서 하지 않을 것.
                일단 URL을 자르는게 필요함.
        */

    //commandTable
    static String[] classificationList = {"accounts","boards","posts","help"};
    static String[] boardCommandList = {"edit","remove","add","view"};
    static String[] accountsCommandList = {"signup","signin","signout","detail","edit","remove"};
    static String[] commandList = {"add","edit","remove","get","exit","board","list","help"}; //view는 List와 역할이 같고 파라미터로 구분.

    //ParamsTable 3개의 자식노드를 갖는다.
    static String[] boardsTree =
            {"boards","boardId","boardName",null,"edit","remove",null,"view",null,null,null,null,null};
    static String[] postsTree =
            {"posts","boardId","postId",null,"add",null,null,"remove","edit","view",null,null,null};
    static String[] accountsTree =
            {"accounts","accountId",null,null,"detail","edit","remove",null,null,null,null,null,null};

    static ArrayList<String[]> classificationArray = new ArrayList<>(List.of(boardsTree,postsTree,accountsTree));




    //해당 클래스는 스태틱 메소드만 존재함.
    private InputInspection() {
    }

    static String inputInspection() {
        Scanner sc = new Scanner(System.in);
        String urlcommand = sc.nextLine();
        urlcommand = urlcommand.trim(); // 앞뒤 공백 제거.

        //간단한 URL의 형식이 맞는지 검사. 애초에 맞지 않는데 쪼개기 하면 에러남.
        if(!urlcommand.contains("/")){
            System.out.println("올바르지 않은 URL 입니다.");
            return "";
        }


        //URL 쪼개기(분류,명령, 파라메터의 값이 Hash로 들어있음.
        URLSplitDone splitDone = urlSpliter(urlcommand);

        String classification = splitDone.classification;
        String command = splitDone.command;
        HashMap<String, String> paramsHash = splitDone.paramsHash;


        //분류 검사,커맨드 검사,파라미터 검사.
        if (!checkClassification(classification)) {//분류가 올바르지 않을시.
            return "분류 에러";
        }else if(!checkCommand(classification, command)) { //커맨드가 올바르지 않을 시,
            return "분류에 해당하지 않는 명령어";
        }else if(checkParams(classification, command, paramsHash)) {
            return "파라미터 오류입니다.";
        }



        /* 커맨드 리스트(유효성검사)
        add : addpost 호출(return ArryList<String>) \n
        edit : 수정 - 미구현 \n
        remove : 삭제 - 미구현 \n
        lookPost : 조회 - 미구현 \n
        board : board 진입 \n
        exit : 콘솔종료 \n
         */


        if(urlcommand.equals("help")){
            System.out.print(
                    "add : 게시물을 생성합니다. \n" +
                            "edit : 해당 번호의 게시물을 수정합니다. 수정한 시간이 찍힙니다. \n" +
                            "remove : 해당 번호의 게시물을 삭제합니다. \n" +
                            "lookPost : 해당 번호의 게시물을 조회합니다.  \n" +
                            "board : board 진입 - 미구현\n" +
                            "exit : 콘솔을 종료합니다. \n");
        }

        return "예기치 못한 에러";

    }




    //분류 검사기.
    static boolean checkClassification(String urlSplit){
        for(String temp: classificationList){
            if(urlSplit.equals(temp)){
                return true;
            }
        }
        return false;
    }

    //커맨드 검사기
    static boolean checkCommand(String urlClassification, String command){
        switch(urlClassification){
            case "accounts":
                String[] params = {"detail","edit","remove"};
                for(String temp: accountsCommandList){
                    if(command.equals(temp)){
                        return true;
                    }
                }
                break;
            case "boards":
                for(String tempList: boardCommandList){
                    if(command.equals(tempList)){
                        return true;
                    }
                }
                break;
            case "posts":
                for(String temp: commandList) {
                    if (command.equals(temp)) {
                        return true;
                    }
                }
                break;
            case "help":
                        return true;
        }
     return false;
    }

    //파라미터 검사기
    static boolean checkParams(String classification,String command, HashMap<String, String> params){

        //파라미터가 여러개 기입된 경우 "?"으로 여러개를 넣은 경우. false
        String[] paramstemp = params.keySet().toArray(new String[0]);
        if (paramstemp.length > 1){
            return false;
        }

        //명령어와 잘못된 파라미터() (해당 명령어가 가져야할 파라미터가 아닌 경우)
        int commandIndex = 0;
        String[] tempTable = new String[13];


        //분류에 해당하는 트리 호출, command Index 지정
        for (int i = 0; i < 3; i++) {
            if(classificationArray.get(i)[0].equals(classification)){
                tempTable = classificationArray.get(i);
                commandIndex = Arrays.asList(tempTable).indexOf(command); //정해진 인덱스 반환. 인덱스가 없으면 -1
            }
        }

        //명령어가 파라미터를 안가지고 있어도 되는 경우, commandIndex 가 -1인 경우.(명령어검사가 끝나면 이쪽으로 오기에)
        if(commandIndex == -1){
            params.clear(); // 파라미터 해시테이블을 삭제해버림.
            return true; // 보내줘야함 파라미터를 볼 필요가 없다.
        }


        //트리 위에서 입력한 URL의 명령노드가, 매칭되는 파라미터를 가지고 있지 않을경우 false
        if(!tempTable[(commandIndex-1)/3].equals(paramstemp[0])){
            return false;
        }

        return false;
    }


    //URL 스플리터
    static URLSplitDone urlSpliter(String urlcommand){
        /*
        Url을 스플릿하는 기능. URLSplitDone으로 사용가능함.
        문제 : 무결성 검사를 한 메소드에서 다 진행할 수 없다.(메소드의 양이 많아지고, 그 메소드의 파라미터도 유효성을 검사해야한다.)
        그래서 URL을 스플릿하고(못해도 command까지만)
        두덩이로 나누면 어떨까.
        분류와 그 커맨드.
        커맨드와 그 파라미터.
        단순 url 스플리터는 파라미터와 그 값까지만 파싱하는 역할
        커맨드와 파라미터는, 커맨드가 수행될 때 파라미터 검사를 할 것, HashMap에 저장하기. 나중에 메소드 단에서 Try-catch로 해결해볼것
        URL 앞단을 "/"단위로 쪼갠다.
         */
        String[] urlSplit = urlcommand.split("/",2);//urlSplit에 나눠담는다.
        String classification = urlSplit[0]; //분류 빼기
        String command = urlSplit[1].split("\\?",2)[0]; //명령어(command) 빼기
        String params = urlSplit[1].split("\\?",2)[1]; //파라미터 부분이 담긴다.

        //파라미터가 hashMap으로 파싱.
        HashMap<String, String> paramsHash = new HashMap<>();

        //파라미터 & 기호로 나누기. "파라미터=값"이 통째로 입력된 횟수만큼 만큼들어있다.
        String[] paramsarray = params.split("&");

        //파라미터 = 기호로 나누기[파라미터 : 값]으로 파싱된다. 중복되는 파라미터는 마지막에 선언된 값으로 덮어쓰여짐
        for(int i = 0;i<paramsarray.length;i++){
            paramsHash.put(paramsarray[i].split("=")[0],paramsarray[i].split("=")[1]);
        }


        //자 다했다 이제 옮겨 주자 class로 통째로 옮겨준다.


        //내부 클래스에 값 탑재시키기
        URLSplitDone SplitDone = new URLSplitDone(classification, command, paramsHash);


        return SplitDone;
    }

    //URL 스플리터를 반환하기 위한 포장지
    static class URLSplitDone {
        String classification;
        String command;
        HashMap<String, String> paramsHash;
        public URLSplitDone(String classification, String command, HashMap<String, String> paramsHash) {
            this.classification = classification;
            this.command = command;
            this.paramsHash = paramsHash;
        }
    }
}
