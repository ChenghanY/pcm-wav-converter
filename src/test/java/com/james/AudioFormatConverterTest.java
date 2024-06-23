package com.james;

import com.james.common.BaseTest;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;

import static com.james.common.PathConstant.*;

@SpringBootTest
public class AudioFormatConverterTest extends BaseTest {

    /**
     * 1. 从远端下载 pcm 示例文件 sample.pcm  <br/>
     * 2. sample.pcm -> output.wav  <br/>
     * 3. output.wav -> output.pcm  <br/>
     */
    @Test
    public void test() throws IOException {
        downloadPcmFile();
        testPcmToWav();
        testWavToPcm();
    }

    private static void testPcmToWav() throws IOException {
        byte[] pcmBytes = Files.readAllBytes(SAMPLE_PCM_FILE);
        byte[] wavBytes = AudioFormatConverter.pcmToWav(pcmBytes);
        writeBytesToFile(OUTPUT_WAV_FILE, wavBytes);
    }

    public static void testWavToPcm() throws IOException {
        if (! Files.exists(OUTPUT_WAV_FILE)) {
            throw new RuntimeException("请先执行生成wav输出");
        }
        byte[] wavBytes = Files.readAllBytes(OUTPUT_WAV_FILE);
        byte[] pcmBytes = AudioFormatConverter.wavToPcm(wavBytes);
        writeBytesToFile(OUTPUT_PCM_FILE, pcmBytes);
    }
}