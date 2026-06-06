package u3123004163.spring_rest;

/**
 * 用户实体类（JavaBean）
 * 学号: 3123004163
 * 姓名: 张逸壕
 *
 * 包含用户的基本属性 id 和 name，
 * 提供 Getter/Setter 方法，用于 JSON 序列化。
 */
public class User {

    private Long id;
    private String name;

    public User() {
    }

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", name='" + name + "'}";
    }
}
