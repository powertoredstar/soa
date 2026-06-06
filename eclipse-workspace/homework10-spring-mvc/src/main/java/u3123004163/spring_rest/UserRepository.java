package u3123004163.spring_rest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

/**
 * 用户仓库类
 * 学号: 3123004163
 * 姓名: 张逸壕
 *
 * 使用 @Repository 注解标记为 Spring 管理的仓库 Bean。
 * 内部使用 ConcurrentHashMap 存储用户数据（内存模拟），
 * 提供增、删、改、查等基本操作。
 */
@Repository
public class UserRepository {

    /** 自增 ID 生成器 */
    private static final AtomicLong idGenerator = new AtomicLong(1L);

    /** 内存存储，线程安全 */
    private final ConcurrentHashMap<Long, User> store = new ConcurrentHashMap<>();

    public UserRepository() {
        // 初始化时预置几条数据，便于测试
        save(new User(null, "3123004163-张逸壕"));
        save(new User(null, "xiaohao"));
        save(new User(null, "xiaoming"));
    }

    /** 查询所有用户 */
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }

    /** 根据 ID 查询用户 */
    public User findById(Long id) {
        return store.get(id);
    }

    /** 新增用户（自动分配 ID） */
    public User save(User user) {
        if (user.getId() == null) {
            user.setId(idGenerator.getAndIncrement());
        }
        store.put(user.getId(), user);
        return user;
    }

    /** 修改用户 */
    public User update(Long id, User user) {
        if (!store.containsKey(id)) {
            return null;
        }
        user.setId(id);
        store.put(id, user);
        return user;
    }

    /** 删除用户 */
    public boolean delete(Long id) {
        return store.remove(id) != null;
    }
}
