package homework3;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 扩展：多级流包装（级联）示例——DataOutputStream → BufferedOutputStream → FileOutputStream，
 * 仍以 OutputStream 抽象类型接收最外层，写出二进制形式的 int 与 UTF-8 字符串。
 */
public class Homework3StreamChain {

    public static void main(String[] args) {
        int i = 12;
        String studentId = "3123004163";

        try (OutputStream raw = new FileOutputStream("homework3_binary.dat");
                OutputStream buffered = new BufferedOutputStream(raw);
                DataOutputStream data = new DataOutputStream(buffered)) {
            data.writeInt(i);
            data.writeUTF(studentId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
