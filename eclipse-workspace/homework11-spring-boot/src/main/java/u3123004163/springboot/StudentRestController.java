package u3123004163.springboot;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 学生 REST 控制器
 * 学号: 3123004163
 * 姓名: 张逸壕
 *
 * @RestController = @Controller + @ResponseBody
 * 该控制器处理 /api/students 路径下的所有 RESTful 请求，
 * 返回的数据自动以 JSON 格式放在 HTTP Response Body 中。
 */
@RestController
@RequestMapping("/api/students")
public class StudentRestController {

    @Autowired
    private StudentRepository studentRepository;

    /**
     * 获取所有学生列表
     * GET /api/students
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Student> list() {
        return studentRepository.findAll();
    }

    /**
     * 根据学号获取单个学生
     * GET /api/students/{studentId}
     */
    @RequestMapping(value = "/{studentId}", method = RequestMethod.GET)
    public Student get(@PathVariable String studentId) {
        Student student = studentRepository.findById(studentId);
        if (student == null) {
            throw new StudentNotFoundException(studentId);
        }
        return student;
    }

    /**
     * 新增学生
     * POST /api/students
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Student create(@RequestBody Student student) {
        return studentRepository.save(student);
    }

    /**
     * 修改学生
     * PUT /api/students/{studentId}
     */
    @RequestMapping(value = "/{studentId}", method = RequestMethod.PUT)
    public Student update(@PathVariable String studentId, @RequestBody Student student) {
        Student updated = studentRepository.update(studentId, student);
        if (updated == null) {
            throw new StudentNotFoundException(studentId);
        }
        return updated;
    }

    /**
     * 删除学生
     * DELETE /api/students/{studentId}
     */
    @RequestMapping(value = "/{studentId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String studentId) {
        if (!studentRepository.delete(studentId)) {
            throw new StudentNotFoundException(studentId);
        }
    }
}
