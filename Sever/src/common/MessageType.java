package common;

public interface MessageType {
    int MESSAGE_EXIT=-1;
    int MESSAGE_LOGIN_SUCCESS=0;
    int MESSAGE_LOGIN_FAIL=1;

    int MESSAGE_GET_ONLINE=2;
    int MESSAGE_RET_ONLINE=3;

    int MESSAGE_SEND_DATA=4;
    int MESSAGE_RECEIVER_DATA=5;
    int MESSAGE_REGISTER_SUCCESS=6;
    int MESSAGE_REGISTER_FAIL=7;
}
