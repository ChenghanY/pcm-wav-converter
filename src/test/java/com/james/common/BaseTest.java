package com.james.common;

import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import static com.james.common.PathConstant.SAMPLE_PCM_FILE;

@SpringBootTest
public class BaseTest {

    protected static void downloadPcmFile() throws IOException {
        if (Files.exists(SAMPLE_PCM_FILE)) {
            return;
        }
        mkdirIfNotExist(SAMPLE_PCM_FILE.getParent());
        try (ReadableByteChannel channel = Channels.newChannel(new URL("https://sis-sample-audio.obs.cn-north-1.myhuaweicloud.com/8k16bit.pcm").openStream());
             FileOutputStream fos = new FileOutputStream(SAMPLE_PCM_FILE.toFile())) {
            fos.getChannel().transferFrom(channel, 0, Long.MAX_VALUE);
        }
    }

    protected static void mkdirIfNotExist(Path path) {
        if (! Files.isDirectory(path)) {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected static void writeBytesToFile(Path path, byte[] data) {
        try {
            // 存在文件就先删除
            if (Files.exists(path)) {
                Files.delete(path);
            }

            mkdirIfNotExist(path.getParent());
            // 字节流写入文件
            Files.write(path, data, StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}