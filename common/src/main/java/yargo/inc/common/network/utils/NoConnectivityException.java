package yargo.inc.common.network.utils;

import java.io.IOException;

public class NoConnectivityException extends IOException {
    @Override
    public String getMessage() {
        return "No conncetivity exception";
    }
}
