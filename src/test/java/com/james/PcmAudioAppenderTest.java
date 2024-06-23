package com.james;

import com.james.common.BaseTest;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;

import static com.james.common.PathConstant.*;

/**
 * pcm字节流拼接测试
 */
@SpringBootTest
public class PcmAudioAppenderTest extends BaseTest {

    /**
     * 1. 从远端下载 pcm 示例文件 sample.pcm <br/>
     * 2. sample.pcm -> output-double-length.wav <br/>
     * 3. 执行后可验证 output-double-length.wav 播放时长为  sample.pcm 两倍且内容无误 <br/>
     */
    @Test
    public void test() throws Exception {
        downloadPcmFile();
        testPcmAudioCanAppend();
    }

    @Test
    public void test2() throws Exception {
        downloadPcmFile();
        testWavAudioCanAppend();
    }

    private static void testWavAudioCanAppend() throws IOException {
        byte[] pcmBytes = Files.readAllBytes(OUTPUT_WAV_FILE);
        // 双倍的字节流拼接
        byte[] doublePcmBytes = PcmAudioAppender.append(pcmBytes, pcmBytes);
        // 转化成wav并写入文件, 文件内容变成了两倍
        byte[] doublePcm2 = AudioFormatConverter.pcmToWav(doublePcmBytes);
        writeBytesToFile(OUTPUT_DOUBLE_LENGTH_WAV_FILE, doublePcm2);
    }

    private static void testPcmAudioCanAppend() throws IOException {
        byte[] pcmBytes = Files.readAllBytes(SAMPLE_PCM_FILE);
        // 双倍的字节流拼接
        byte[] doublePcmBytes = PcmAudioAppender.append(pcmBytes, pcmBytes);
        // 转化成wav并写入文件, 文件内容变成了两倍
        byte[] wavBytes = AudioFormatConverter.pcmToWav(doublePcmBytes);
        writeBytesToFile(OUTPUT_DOUBLE_LENGTH_WAV_FILE, wavBytes);
    }

}