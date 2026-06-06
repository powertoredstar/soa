package u3123004163.springboot;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 学生未找到异常
 * 学号: 3123004163
 * 姓名: 张逸壕
 *
 * 当按学号查询、修改、删除的学生不存在时抛出，
 * Spring 自动返回 HTTP 404 状态码。
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class StudentNotFoundException extends RuntimeException {

    public StudentNotFoundException(String studentId) {
        super("Student not found: studentId=" + studentId);
    }
}
