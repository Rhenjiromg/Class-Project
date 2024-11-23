package resources;

/**
 * @params: Arglength => Length of expected length of the argument.
 */
public enum MessageType {
    LOGOUT(0),
	VERIFICATION(0),
    DEPOSIT(0),
    WITHDRAW(0),
    TRANSFER(0),
    TRANSACTION_HISTORY(0),
    ERROR(0),
    SUCCESS(0);

    private final int argLength;

    MessageType(int argLength) {
        this.argLength = argLength;
    }

    public int getArgLength() {
        return argLength;
    }
}