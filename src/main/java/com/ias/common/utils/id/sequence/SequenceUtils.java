package com.ias.common.utils.id.sequence;

public class SequenceUtils {
    /**
     * 工作机器ID(0~31)
     * @type long
     * @date 2018年1月8日 下午8:24:17
     * @author jiuzhou.hu
     */
    public static long workerId = 0;
    
    /**
     * 数据中心ID(0~31)
     * @type long
     * @date 2018年1月8日 下午8:24:29
     * @author jiuzhou.hu
     */
    public static long datacenterId = 0;
    
    private static Sequence idWorker;
    
    public static long genId() {
        return getInstance().nextId();
    }
    
    public static Sequence getInstance() {
        if(idWorker == null) {
            idWorker = new Sequence(workerId, datacenterId);
        }
        return idWorker;
    }
}
