package com.ias.common.utils.id;

import java.util.UUID;

import com.ias.common.utils.string.StringUtil;

public class UUIDUtils {
    
    private UUIDUtils() {}

    /**
     * 生成UUID
     * 
     * @return
     */
    public static String createUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    /**
     * 生成无“-”的UUID
     * 
     * @return
     */
    public static String createSystemDataPrimaryKey() {
        return StringUtil.replace(createUUID(), "-", "");
    }
}
