package io.github.alwayszmx.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @version 1.0.0
 * @className: CodecUtils
 * @description: TODO 类描述
 * @author: ZhangMaoxing
 * @date: 2023/6/16 16:21
 **/
public class CodecUtils {

    // Unicode编码和解码
    public static String encodeUnicode(String input) {
        StringBuilder out = new StringBuilder();
        for (char c : input.toCharArray()) {
            out.append(String.format("\\u%04x", (int) c));
        }
        return out.toString();
    }

    public static String decodeUnicode(String input) {
        StringBuilder out = new StringBuilder();
        Matcher matcher = Pattern.compile("\\\\u([0-9A-Fa-f]{4})").matcher(input);
        while (matcher.find()) {
            out.append((char) Integer.parseInt(matcher.group(1), 16));
        }
        return out.toString();
    }

    // URL编码和解码
    public static String encodeURL(String input) {
        try {
            return URLEncoder.encode(input, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decodeURL(String input) {
        try {
            return URLDecoder.decode(input, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    // UTF16编码和解码
    public static String encodeUTF16(String input) {
        return new String(input.getBytes(Charset.forName("UTF-8")), Charset.forName("UTF-16"));
    }

    public static String decodeUTF16(String input) {
        return new String(input.getBytes(Charset.forName("UTF-16")), Charset.forName("UTF-8"));
    }

    // Base64编码和解码
    public static String encodeBase64(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes());
    }

    public static String decodeBase64(String input) {
        byte[] decodedBytes = Base64.getDecoder().decode(input);
        return new String(decodedBytes);
    }

    // Hex编码和解码
    public static String encodeHex(String input) {
        StringBuilder out = new StringBuilder();
        byte[] bytes = input.getBytes();
        for (byte b : bytes) {
            out.append(String.format("%02X", b));

        }
        return out.toString();
    }

    public static String decodeHex(String input) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        for (int i = 0; i < input.length(); i += 2) {
            out.write(Integer.parseInt(input.substring(i, i + 2), 16));
        }
        return new String(out.toByteArray());
    }

    // Gzip压缩和解压
    public static String compressGzip(String input) {
        try {
            byte[] gzipData = input.getBytes();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(out);
            gzip.write(gzipData);
            gzip.close();
            byte[] compressedGzipData = out.toByteArray();
            return Base64.getEncoder().encodeToString(compressedGzipData);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decompressGzip(String input) {
        try {
            byte[] compressedGzipData = Base64.getDecoder().decode(input);
            GZIPInputStream gzip = new GZIPInputStream(new ByteArrayInputStream(compressedGzipData));
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = gzip.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            gzip.close();
            out.close();
            return out.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Gzip压缩和解压
    public static byte[] compressGzip(byte[] input) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(out);
            gzip.write(input);
            gzip.close();
            return out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] decompressGzip(byte[] input) {
        try {
            GZIPInputStream gzip = new GZIPInputStream(new ByteArrayInputStream(input));
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = gzip.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            gzip.close();
            out.close();
            return out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
