package u3123004163.springboot;

/**
 * 学生实体类（JavaBean）
 * 学号: 3123004163
 * 姓名: 张逸壕
 *
 * 包含学号（studentId）、姓名（name）、性别（gender）、年龄（age）、专业（major）属性。
 * Spring Boot + Jackson 会自动将 Student 对象序列化为 JSON。
 */
public class Student {

    /** 学号 */
    private String studentId;

    /** 姓名 */
    private String name;

    /** 性别 */
    private String gender;

    /** 年龄 */
    private Integer age;

    /** 专业 */
    private String major;

    public Student() {}

    public Student(String studentId, String name, String gender, Integer age, String major) {
        this.studentId = studentId;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.major = major;
    }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }

    @Override
    public String toString() {
        return "Student{studentId='" + studentId + "', name='" + name + "', gender='" + gender
                + "', age=" + age + ", major='" + major + "'}";
    }
}
