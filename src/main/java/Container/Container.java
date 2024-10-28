package Container;

import command.AccountCommand;
import command.BoardCommand;
import command.PostsCommand;
import java.util.HashMap;
import java.util.Map;



public class Container {

    //클래스 내부에 저장되는
    private Map<Class<?>,Object> container = new HashMap<>();

    //컨테이너 생성자
    public Container() {
        inisi();
    }

    //인스턴스 게터
    public Object getTest(Class<?> clazz) {
        return container.get(clazz);
    }

    //컨테이너 객체 생성
    public void inisi(){
        AccountCommand accountCommand = new AccountCommand();
        BoardCommand boardCommand = new BoardCommand();
        PostsCommand postsCommand = new PostsCommand();

        container.put(BoardCommand.class,boardCommand);
        container.put(PostsCommand.class,postsCommand);
        container.put(AccountCommand.class,accountCommand);
    }

}
