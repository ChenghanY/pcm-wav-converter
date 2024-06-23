package com.james.common;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PathConstant {

    /**
     * 测试文件地址根目录
     */
    public static final Path DIR_ROOT = Paths.get("src/test/resources/");

    /**
     * 从远端下载的 pcm 示例文件
     */
    public static final Path SAMPLE_PCM_FILE = DIR_ROOT.resolve("sample.pcm");

    /**
     * pcm 输出文件
     */
    public static final Path OUTPUT_PCM_FILE = DIR_ROOT.resolve("output.pcm");

    /**
     * wav 输出文件
     */
    public static final Path OUTPUT_WAV_FILE = DIR_ROOT.resolve("output.wav");


    /**
     * wav 输出文件 (经过pcm时长加倍处理)
     */
    public static final Path OUTPUT_DOUBLE_LENGTH_WAV_FILE = DIR_ROOT.resolve("output-double-length.wav");
}
