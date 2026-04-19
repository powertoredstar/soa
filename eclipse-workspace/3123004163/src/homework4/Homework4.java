package homework4;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * 第四次作业：读取第三次作业生成的 UTF-8 文本文件到控制台；基于 Scanner、StringBuilder 的简单运算器。
 */
public class Homework4 {

    /** 与 {@link homework3.Homework3} 输出文件名一致 */
    private static final String DATA_FILE = "homework3_output.txt";

    public static void main(String[] args) {
        readHomework3File();
        runCalculator();
    }

    /** 输入流链：FileInputStream → BufferedInputStream → InputStreamReader(UTF-8) → BufferedReader */
    static void readHomework3File() {
        try (InputStream raw = new FileInputStream(DATA_FILE);
                InputStream buffered = new BufferedInputStream(raw);
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(buffered, StandardCharsets.UTF_8))) {
            String line1 = reader.readLine();
            String line2 = reader.readLine();
            if (line1 == null || line2 == null) {
                System.err.println("文件内容不完整。请先运行 Homework3 生成 " + DATA_FILE);
                return;
            }
            int i = Integer.parseInt(line1.trim());
            String sno = line2.trim();
            System.out.println("【读取 " + DATA_FILE + "】");
            System.out.println("整数 = " + i);
            System.out.println("学号 = " + sno);
        } catch (IOException e) {
            System.err.println("读取失败（请先运行 Homework3 生成 " + DATA_FILE + "）：" + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("首行不是合法 int：" + e.getMessage());
        }
    }

    /** 简单运算器：Scanner 读入，StringBuilder 拼接结果行 */
    static void runCalculator() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("简单运算器, 作者 3123004163");
            System.out.println("+ 加法");
            System.out.println("- 减法");
            System.out.println("* 乘法");
            System.out.println("/ 除法");
            System.out.println("q 退出程序");
            System.out.print("输入你的选择（字符 +, -, *, / 或 q）：");
            String choiceLine = sc.nextLine().trim();
            if (choiceLine.isEmpty()) {
                continue;
            }
            char op = choiceLine.charAt(0);
            if (op == 'q' || op == 'Q') {
                System.out.println("再见。");
                break;
            }
            String opName;
            switch (op) {
            case '+':
                opName = "加法";
                break;
            case '-':
                opName = "减法";
                break;
            case '*':
                opName = "乘法";
                break;
            case '/':
                opName = "除法";
                break;
            default:
                System.out.println("无效选择，请输入 +、-、*、/ 或 q。");
                continue;
            }

            System.out.print("你选择的命令是" + opName + "，输入两个整数，用空格分隔：");
            String nums = sc.nextLine().trim();
            String[] parts = nums.split("\\s+");
            if (parts.length < 2) {
                System.out.println("需要两个整数，请重试。");
                continue;
            }
            int a;
            int b;
            try {
                a = Integer.parseInt(parts[0]);
                b = Integer.parseInt(parts[1]);
            } catch (NumberFormatException ex) {
                System.out.println("输入不是合法整数，请重试。");
                continue;
            }

            if (op == '/' && b == 0) {
                System.out.println("除数不能为 0，请重试。");
                continue;
            }

            long result;
            switch (op) {
            case '+':
                result = (long) a + b;
                break;
            case '-':
                result = (long) a - b;
                break;
            case '*':
                result = (long) a * b;
                break;
            default:
                result = a / b;
                break;
            }

            StringBuilder line = new StringBuilder();
            line.append(a).append(' ').append(op).append(' ').append(b).append(" = ").append(result);
            System.out.println(line);
        }
    }
}
