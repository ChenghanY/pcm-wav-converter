package com.james;

import com.james.common.BaseTest;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

import static com.james.common.PathConstant.OUTPUT_DOUBLE_LENGTH_WAV_FILE;
import static com.james.common.PathConstant.SAMPLE_PCM_FILE;

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
    public void testAppendByBytes() throws Exception {
        downloadPcmFile();
        testPcmAudioCanAppendByBytes();
    }

    /**
     * 对byte[]进行base64编码。byte[]场景下，两段base64字符串拼接后，无法正常解码为byte[]
     * 期望抛出 {@link IllegalArgumentException}
     */
    @Test
    public void testThrowExceptionWhenAppendByBase64() throws Exception {
        downloadPcmFile();

        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> {
            // 对二进制进行 base64
            byte[] pcmBytes = Files.readAllBytes(SAMPLE_PCM_FILE);
            // 两段 base64 拼接
            String base64 = Base64.getEncoder().encodeToString(pcmBytes) + Base64.getEncoder().encodeToString(pcmBytes);
            // 解码
            Base64.getDecoder().decode(base64);
        });

    }

    private static void testPcmAudioCanAppendByBytes() throws IOException {
        byte[] pcmBytes = Files.readAllBytes(SAMPLE_PCM_FILE);
        // 双倍的字节流拼接
        byte[] doublePcmBytes = PcmAudioAppender.append(pcmBytes, pcmBytes);
        // 转化成wav并写入文件, 文件内容变成了两倍
        byte[] wavBytes = AudioFormatConverter.pcmToWav(doublePcmBytes);
        writeBytesToFile(OUTPUT_DOUBLE_LENGTH_WAV_FILE, wavBytes);
    }

}