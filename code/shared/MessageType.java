package shared;

/**
 * @params: Arglength => Length of expected length of the argument.
 */
public enum MessageType {
    LOGIN(0),
    LOGOUT(0),
    VERIFICATION(0),
    DEPOSIT(0),
    WITHDRAW(0),
    TRANSFER(0),
    TRANSACTION_HISTORY(0),
    ADD_ACCOUNT(0),
    DEACTIVATE_ACCOUNT(0),
    ERROR(0),
    UPDATEERROR(0), 
    ACCOUNT_INFO(0);

    private final int argLength;

    MessageType(int argLength) {
        this.argLength = argLength;
    }

    public int getArgLength() {
        return argLength;
    }
}