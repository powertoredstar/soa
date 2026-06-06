package u3123004163.spring_rest;

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
 * 用户 REST 控制器
 * 学号: 3123004163
 * 姓名: 张逸壕
 *
 * @RestController = @Controller + @ResponseBody
 * 该控制器处理 /api/rest/ 路径下的所有 RESTful 请求，
 * 返回的数据自动以 JSON 格式放在 HTTP Response Body 中。
 */
@RestController
@RequestMapping("/rest")
public class UserRestController {

    @Autowired
    private UserRepository userRepository;

    /**
     * 获取所有用户列表
     * GET /api/rest/users
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> list() {
        return userRepository.findAll();
    }

    /**
     * 根据 ID 获取单个用户
     * GET /api/rest/users/{id}
     */
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public User get(@PathVariable Long id) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new UserNotFoundException(id);
        }
        return user;
    }

    /**
     * 新增用户
     * POST /api/rest/users
     */
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody User user) {
        return userRepository.save(user);
    }

    /**
     * 修改用户
     * PUT /api/rest/users/{id}
     */
    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
    public User update(@PathVariable Long id, @RequestBody User user) {
        User updated = userRepository.update(id, user);
        if (updated == null) {
            throw new UserNotFoundException(id);
        }
        return updated;
    }

    /**
     * 删除用户
     * DELETE /api/rest/users/{id}
     */
    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!userRepository.delete(id)) {
            throw new UserNotFoundException(id);
        }
    }

    /** 404 异常 */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    static class UserNotFoundException extends RuntimeException {
        UserNotFoundException(Long id) {
            super("User not found: " + id);
        }
    }
}
