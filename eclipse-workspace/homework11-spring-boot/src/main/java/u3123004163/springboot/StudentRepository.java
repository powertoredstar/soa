package u3123004163.springboot;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * 学生仓库类（文件持久化）
 * 学号: 3123004163
 * 姓名: 张逸壕
 *
 * 使用 @Repository 注解标记为 Spring 管理的仓库 Bean。
 * 数据以 JSON 文件形式保存（students.json），实现持久化存储。
 * 使用 LinkedHashMap 保持插入顺序，按学号查找效率为 O(1)。
 */
@Repository
public class StudentRepository {

    private final ObjectMapper objectMapper = new ObjectMapper();

    /** 数据文件路径 */
    private final String dataFilePath;

    /** 内存缓存，key 为 studentId */
    private final Map<String, Student> store = new LinkedHashMap<>();

    public StudentRepository(@Value("${app.data.file:students.json}") String dataFilePath) {
        this.dataFilePath = dataFilePath;
        loadData();
        // 如果文件为空，预置几条数据便于测试
        if (store.isEmpty()) {
            save(new Student("3123004163", "张逸壕", "男", 20, "软件工程"));
            save(new Student("3123004001", "李明", "男", 21, "计算机科学"));
            save(new Student("3123004002", "王芳", "女", 20, "数据科学"));
        }
    }

    /**
     * 从 JSON 文件加载数据到内存
     */
    private void loadData() {
        try {
            File file = resolveDataFile();
            if (file.exists() && file.length() > 0) {
                List<Student> students = objectMapper.readValue(file,
                        new TypeReference<List<Student>>() {});
                for (Student s : students) {
                    store.put(s.getStudentId(), s);
                }
            }
        } catch (IOException e) {
            System.err.println("加载数据文件失败: " + e.getMessage());
        }
    }

    /**
     * 将内存数据保存到 JSON 文件
     */
    private void persistData() {
        try {
            File file = resolveDataFile();
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, new ArrayList<>(store.values()));
        } catch (IOException e) {
            System.err.println("保存数据文件失败: " + e.getMessage());
        }
    }

    /**
     * 解析数据文件路径
     */
    private File resolveDataFile() {
        // 先尝试作为 classpath 资源
        try {
            File file = ResourceUtils.getFile("classpath:" + dataFilePath);
            if (file.canWrite()) {
                return file;
            }
        } catch (Exception ignored) {
        }
        // 回退到项目根目录
        return new File(dataFilePath);
    }

    /** 查询所有学生 */
    public List<Student> findAll() {
        return new ArrayList<>(store.values());
    }

    /** 根据学号查询学生 */
    public Student findById(String studentId) {
        return store.get(studentId);
    }

    /** 新增学生 */
    public Student save(Student student) {
        store.put(student.getStudentId(), student);
        persistData();
        return student;
    }

    /** 修改学生 */
    public Student update(String studentId, Student student) {
        if (!store.containsKey(studentId)) {
            return null;
        }
        student.setStudentId(studentId);
        store.put(studentId, student);
        persistData();
        return student;
    }

    /** 删除学生 */
    public boolean delete(String studentId) {
        Student removed = store.remove(studentId);
        if (removed != null) {
            persistData();
            return true;
        }
        return false;
    }
}
