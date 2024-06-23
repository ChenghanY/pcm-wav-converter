package com.james;

public class PcmAudioAppender {

    /**
     * 将 frame1 和 frame2 按顺序拼接到一起成为新的字节数组
     */
    public static byte[] append(byte[] frame1, byte[] frame2) {
        byte[] result = new byte[frame1.length + frame2.length];
        System.arraycopy(frame1, 0, result, 0, frame1.length);
        System.arraycopy(frame2, 0, result, frame1.length, frame2.length);
        return result;
    }
}
