/**
 * test.js - 前端 JavaScript，使用 fetch() 异步调用 REST API
 * 学号: 3123004163
 * 姓名: 张逸壕
 *
 * 使用 js 的 fetch() 方法（异步处理技术）访问后端 RESTful API，
 * 并通过 DOM 操作动态生成 HTML 表格展示数据。
 * 这种方式称为单页面应用技术（SPA）。
 */

/**
 * 获取用户1的信息，动态创建表格展示
 */
function getUser1() {
    fetch('api/rest/users/1')
        .then(function(response) {
            if (!response.ok) {
                throw new Error('HTTP error, status: ' + response.status);
            }
            return response.json();
        })
        .then(function(user) {
            var container = document.getElementById('user1Result');
            var html = '<table>';
            html += '<tr><th>ID</th><th>Name</th></tr>';
            html += '<tr><td>' + user.id + '</td><td>' + user.name + '</td></tr>';
            html += '</table>';
            container.innerHTML = html;
        })
        .catch(function(error) {
            document.getElementById('user1Result').innerHTML =
                '<p style="color:red;">Error: ' + error.message + '</p>';
        });
}

/**
 * 获取所有用户列表，动态创建表格展示
 */
function getAllUsers() {
    fetch('api/rest/users')
        .then(function(response) {
            if (!response.ok) {
                throw new Error('HTTP error, status: ' + response.status);
            }
            return response.json();
        })
        .then(function(users) {
            var container = document.getElementById('allUsersResult');
            var html = '<table>';
            html += '<tr><th>ID</th><th>Name</th></tr>';
            for (var i = 0; i < users.length; i++) {
                html += '<tr>';
                html += '<td>' + users[i].id + '</td>';
                html += '<td>' + users[i].name + '</td>';
                html += '</tr>';
            }
            html += '</table>';
            container.innerHTML = html;
        })
        .catch(function(error) {
            document.getElementById('allUsersResult').innerHTML =
                '<p style="color:red;">Error: ' + error.message + '</p>';
        });
}

/**
 * 新增用户
 */
function addUser() {
    var nameInput = document.getElementById('newUserName');
    var name = nameInput.value.trim();
    if (!name) {
        alert('请输入用户名');
        return;
    }

    fetch('api/rest/users', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ name: name })
    })
    .then(function(response) {
        if (!response.ok) {
            throw new Error('HTTP error, status: ' + response.status);
        }
        return response.json();
    })
    .then(function(user) {
        document.getElementById('addResult').innerHTML =
            '<p style="color:green;">添加成功！ID=' + user.id + ', Name=' + user.name + '</p>';
        nameInput.value = '';
        // 刷新用户列表
        getAllUsers();
    })
    .catch(function(error) {
        document.getElementById('addResult').innerHTML =
            '<p style="color:red;">Error: ' + error.message + '</p>';
    });
}
