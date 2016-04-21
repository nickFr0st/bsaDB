package constants;

/**
 * User: nmalloch
 */
public enum ConnectionConst {

    NO_SERVER_CONNECTION(1),
    NO_DATABASE_CONNECTION(2),
    CONNECTION_GOOD(3),
    ;

    private int choice;

    ConnectionConst(int choice) {
        this.choice = choice;
    }

    public int getChoice() {
        return choice;
    }
}
