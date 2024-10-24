package Container;

import command.AccountCommand;
import command.BoardCommand;
import command.PostsCommand;
import java.util.HashMap;
import java.util.Map;



public class Container {

    private Map<Class<?>,Object> container = new HashMap<>();

    public Object getTest(Class<?> clazz) {
        return container.get(clazz);
    }

    public Container() {
       inisi();
    }

    public void inisi(){
        AccountCommand accountCommand = new AccountCommand();
        BoardCommand boardCommand = new BoardCommand();
        PostsCommand postsCommand = new PostsCommand();

        container.put(BoardCommand.class,boardCommand);
        container.put(PostsCommand.class,postsCommand);
        container.put(AccountCommand.class,accountCommand);
    }

}
