package homework3;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 第三次作业：使用 OutputStream 多态引用，以 FileOutputStream + BufferedOutputStream
 * 将 int 与学号（UTF-8 文本）写入 homework3_output.txt。
 */
public class Homework3 {

    public static void main(String[] args) {
        int i = 12;
        String studentId = "3123004163";
        Path path = Paths.get("homework3_output.txt");

        try (OutputStream out = new BufferedOutputStream(new FileOutputStream(path.toFile()))) {
            String firstLine = i + System.lineSeparator();
            String secondLine = studentId + System.lineSeparator();
            out.write(firstLine.getBytes(StandardCharsets.UTF_8));
            out.write(secondLine.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
