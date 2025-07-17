package com.globalogic.GestorUsuarios.util.helper;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class TimestampHelper {
    public static String getNowDate() {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a");
        return dateFormat.format(currentTimestamp);
    }
}
