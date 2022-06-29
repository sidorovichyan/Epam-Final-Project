package com.epam.ynairlineepam.command.util;

import org.apache.log4j.Logger;

public class CommandHelper {
    private final static Logger logger = Logger.getLogger(CommandHelper.class);

    public static int getInt(String innerString) {
        if (innerString != null) {
            try {
                int value = Integer.parseInt(innerString);
                return value;
            } catch (NumberFormatException e) {
                logger.error("Wrong string for getting number");
                return -1;
            }
        } else {
            return -1;
        }
    }
}
