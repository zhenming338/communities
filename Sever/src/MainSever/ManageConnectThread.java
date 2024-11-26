package MainSever;

import java.util.HashMap;
@SuppressWarnings("all")
public class ManageConnectThread {
    static  HashMap<String, ConnectThread> threadList=new HashMap<String, ConnectThread>();
    public ManageConnectThread() {
         threadList= new HashMap<>();
    }
    public static HashMap getThreadList(){
        return threadList;
    }
    public static  ConnectThread getThreadByName(String name){
        return threadList.get(name);
    }
    public static void addConnectThread(ConnectThread connectThread,String name){
        threadList.put(name,connectThread);
    }
    public static void removeThreadByName(String name){
        threadList.remove(name);
    }
}
