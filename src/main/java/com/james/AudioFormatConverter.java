package com.james;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class AudioFormatConverter {

    /**
     * 采样率
     */
    private static final Integer RATE = 8000;

    /**
     * 声道
     */
    private static final Integer CHANNELS = 1;

    public static byte[] pcmToWav(byte[] pcmBytes) {
        return addHeader(pcmBytes, buildHeader(pcmBytes.length));
    }

    public static byte[] wavToPcm(byte[] wavBytes) {
        return removeHeader(changeFormatToWav(wavBytes));
    }

    private static byte[] addHeader(byte[] pcmBytes, byte[] headerBytes) {
        byte[] result = new byte[44 + pcmBytes.length];
        System.arraycopy(headerBytes, 0, result, 0, 44);
        System.arraycopy(pcmBytes, 0, result, 44, pcmBytes.length);
        return result;
    }

    private static byte[] changeFormatToWav(byte[] audioFileContent) {
        AudioFormat format = new AudioFormat(
                RATE,
                16,
                CHANNELS,
                true,
                false
        );

        try (final AudioInputStream originalAudioStream = AudioSystem.getAudioInputStream(new ByteArrayInputStream(audioFileContent));
             final AudioInputStream formattedAudioStream = AudioSystem.getAudioInputStream(format, originalAudioStream);
             final AudioInputStream lengthAddedAudioStream = new AudioInputStream(formattedAudioStream, format, audioFileContent.length);
             final ByteArrayOutputStream convertedOutputStream = new ByteArrayOutputStream()) {
            AudioSystem.write(lengthAddedAudioStream, AudioFileFormat.Type.WAVE, convertedOutputStream);
            return convertedOutputStream.toByteArray();
        } catch (UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] removeHeader(byte[] audioFileContent) {
        return Arrays.copyOfRange(audioFileContent, 44, audioFileContent.length);
    }

    private static byte[] buildHeader(Integer dataLength) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                writeChar(bos, new char[]{'R', 'I', 'F', 'F'});
                writeInt(bos, dataLength + (44 - 8));
                writeChar(bos, new char[]{'W', 'A', 'V', 'E'});
                writeChar(bos, new char[]{'f', 'm', 't', ' '});
                writeInt(bos, 16);
                writeShort(bos, 0x0001);
                writeShort(bos, CHANNELS);
                writeInt(bos, AudioFormatConverter.RATE);
                writeInt(bos, (short) (CHANNELS * 2) * RATE);
                writeShort(bos, (short) (CHANNELS * 2));
                writeShort(bos, 16);
                writeChar(bos, new char[]{'d', 'a', 't', 'a'});
                writeInt(bos, dataLength);
                return bos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeShort(ByteArrayOutputStream bos, int s) throws IOException {
        byte[] arr = new byte[2];
        arr[1] = (byte) ((s << 16) >> 24);
        arr[0] = (byte) ((s << 24) >> 24);
        bos.write(arr);
    }

    private static void writeInt(ByteArrayOutputStream bos, int n) throws IOException {
        byte[] buf = new byte[4];
        buf[3] = (byte) (n >> 24);
        buf[2] = (byte) ((n << 8) >> 24);
        buf[1] = (byte) ((n << 16) >> 24);
        buf[0] = (byte) ((n << 24) >> 24);
        bos.write(buf);
    }

    private static void writeChar(ByteArrayOutputStream bos, char[] id) {
        for (char c : id) {
            bos.write(c);
        }
    }
}