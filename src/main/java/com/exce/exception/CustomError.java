package com.exce.exception;

public enum CustomError {
    OK("99SYS00000", 0, "Completed successfully"),
    INVALID_REQ("99SYS00001", 1, "Invalid request"),
    AUTH_ERROR("99SYS00002", 2, "Authentication error"),
    PLAYER_AUTH_ERROR("99SYS00003", 3, "Player authentication error"),
    PLAYER_REGISTER_ERROR("99SYS00004", 4, "Player registration error"),
    TRANSFER_FAIL("99SYS00005", 5, "Transaction failed"),
    API_ERROR("99SYS00006", 6, "API error"),
    MAINTENANCE("99SYS00007", 7, "Maintenance"),
    LOGIN_FAIL("99SYS00008", 8, "Login Failed"),
    NOT_ENOUGH_FUNDS("99SYS00009", 9, "Insufficient funds"),
    HTTP_CLIENT_SERVER_ERROR("99SYS00010", 10, "Http client/server error exception"),
    NOT_FOUND_GAME("99SYS00014", 14, "Search game not found"),
    NOT_FOUND_USER("99SYS00015", 15, "Search user not found"),
    NOT_FOUND_BALANCE("99SYS00016", 16, "Search balance not found"),
    NOT_FOUND_TRANSACTION_DETAILS("99SYS00017", 17, "Search transaction not found"),
    NOT_FOUND_BETORDERDETAIL("99SYS00018", 18, "Search betOrderDetail not found"),
    ;

    private final String errorCode;
    private final int returnCode;
    private final String errorDesc;

    private CustomError(String code, int returnCode, String description) {
        this.errorCode = code;
        this.returnCode = returnCode;
        this.errorDesc = description;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public int getReturnCode() {
        return returnCode;
    }

    @Override
    public String toString() {

        return errorCode + ":" + errorDesc;
    }
}
