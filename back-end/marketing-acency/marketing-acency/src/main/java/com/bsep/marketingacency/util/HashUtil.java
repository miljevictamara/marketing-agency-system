package com.bsep.marketingacency.util;

import org.apache.commons.codec.digest.DigestUtils;

public class HashUtil {
    public static String hashUserId(String userId) {
        return DigestUtils.sha256Hex(userId);
    }

    public static String hash(String input) {
        return DigestUtils.sha256Hex(input);
    }
}
