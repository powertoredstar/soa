# soa作业

## 作业目录结构和一些其它说明

自己作业目录中的文件内容和目录结果参考下面例子

```
│  .gitignore
│  homework_from_cshao.md
│  README.md
│  作业1.md
│  作业2.md
│  作业3.md
│  作业4.md
│  作业5.md
│  作业6.md
│  作业7.md
│  作业8.md
│  作业9.md
│
├─from_cshao
│      check.md
│      Comments.md
│
├─imgs
│      3_1.png
│      3_10.png
│      3_11.png
│      3_2.png
│      3_3.png
│      3_4.png
│
│
├─作业代码
│      a.c
│      b.c
│
├─Eclipse-wrokspace
│   │
│   ├─作业1代码
│
└─

```
其中：
- `.gitignore` 是忽略文件 
- `homework_from_cshao.md` 是本文件，由老师设置，不要随便修改
- `README.md` 该文件会在gitlab项目中自动显示，是自述文件。 最终会包含所有作业内容
- `作业x.md` 每次的作业管理文件,后面的x按照作业次数递增。建议用阿拉伯数字，不要用汉字。 每次作业必须有一个对应的md格式文档，编程作业也不例外。 一般作业中，这个md文档存入作业内容，对于编程作业这个文档类似于软件的设计文档。
- `from_cshao` 目录，由老师创建，不要随便修改。其中可能包含时间标签文件`check.md`,评语文件`comments.md`或者其它文件。
- `imgs`目录，存放md文件中使用在图片，该目录名称可由自己决定。这个目录下面可以自由决定是否使用多级目录，比如为每次作业的图片再创建二组目录。
- `作业x代码` 目录，对于编程作业，所有程序源代码存放在这个目录中。这个目录下面可以自由决定是否使用多级目录。
- `Eclipse-wrokspace` 目录，对于使用Eclipse编程的作业，所有项目程序源代码存放在这个目录中。在启动Eclipse时设置该目录为工作区


--------------------
不会用git、gitlab的同学参考下面视频 
https://meeting.tencent.com/crm/2pDL0a52f5    如何注册，创建项目
https://meeting.tencent.com/crm/l6MdyaPN4d    补充知识，包含 git 忽略文件 ，md操作
https://meeting.tencent.com/crm/KwMPjXQNdb 	  作业目录组织	


每周的作业尽量本周完成并上推到服务器，最多拖到下周，否则按未交作业处理。
如果由于服务器没有及时开放而引起的作业无法及时提交时，大家不用担负责任。

一般在每周四中午开服务器。
服务器开启后，会自动在当晚1点（即第二天凌晨1点）自动关闭。 判断服务器是否开放的最简单方法就是访问一下页面 http://10.21.21.251.是否可以正常打开。

## SOA 课堂纪律说明
1. 旷课超过1/3（4次）以上者取消考试资格，请假需要辅导员给出的正式请假条。
2. 所有作业需要独立完成。
3. 所有同学都要参加最后的答辩，不参加者按缺考处理。

## SOA第一次作业
3/20/2026 
1. 登陆网站 "https://10.21.21.251/users/sign_up" 注册账号。
2. 下载安装git软件。 
注意：用户名(username)是自己的学号，项目名称是soa。一定记得要添加 cshao为项目成员，且给足够高的权限（Maintainer）。

3. 本地Git 基本操作学习：
- 所有操作都在本地进行，除了下载软件和上推数据下拉数据需要联网外不需要任何的联网。
- 下载安装 git。
- 编辑个人信息 用户用必须用 **学号+姓名**的格式。
- 图形界面下文件的暂存与提交
- 图形界面下历史的查看 
- 熟悉图形界面的快捷键。

4. 学习md文档格式
5. 将本地保存的数据上传到服务器。
6. 作业文档使用md格式保存信息，文件名为“作业1.md”

## SOA第二次作业
4/3/2026 
安装配置java开发环境
1. 下载安装jdk   https://download.oracle.com/java/25/latest/jdk-25_windows-x64_bin.exe
2. 下载安装eclipse 
	1. https://www.eclipse.org/downloads/packages/release/2025-12/r
	2. https://www.eclipse.org/downloads/download.php?file=/technology/epp/downloads/release/2025-12/R/eclipse-jee-2025-12-R-win32-x86_64.zip
		1. 解压版本，先解压到某个目录
		2. 选择 eclipse.exe 鼠标右键
		3. 选择`发送到`--> `桌面快捷方式`

3. 配置eclipse启动参数
	1. 在自己作业目录（git管理的目录）建立一个专门保存eclipse项目的工作区目录，比如 `eclipse-workspace`
	2. 双击启动 eclipse.exe
	3. 在`选择工作区目界面(Select a directory as workspace)`选择上面创建的目录
4. Eclipse创建第一个java项目
	1. 菜单`File->New->Java Project` 或 菜单`File->New->Other...`选择 Java->JavaProject
	2. 在创建项目（New a java project, Create a project name）窗口 
		1. `Project name` 位置输入项目名字，
		2. 不要勾选`Create module-info.java file`,
		3. 点击`Finish`
5. 为新项目增加第一个主类
	1. 在 `Package Explorer`窗口选择新建立的项目， 
		1. 如果没有`Package Explorer`窗口，可以主菜单在Windows->Show View->Package Explorer 打开这个窗口
	2. 鼠标右键->New->Class 或菜单New->Class 打开New Java Class窗口
	3. 在`Package` 处填写自己的学号作为包名
		1. 注意：因为标识符不能以数字开始，在学号前增加一具字母，比较`u31xxxx`。
	4. 在`Name`处输入主类的名字，比如  `Homework2Main`
	4. 勾选 `public static void main(String[] args)`
	5. 勾选 `Generate comments`
	6. 点击 Finish

6. 运行
	1. 在新创建的函数中增加语句 `System.out.println("31xxxxxxx");` 其中31xxxxxxx是自己的学号，如下
```
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("31xxxxxxx");
	}
```	
	2. 在 `Package Explorer`窗口选择新建立的项目，
	3. 鼠标右键，选择`Run As->Java Application`
7.  在Eclipse中使用git
	1.  因为工作区是自己的作业目录，它已被git管理，所以在创建第一个java项目时，eclipse会自动在工作区的根目录以及这个项目根目录下面添加`.gitignore`。
		1.  自学`git忽略文件`相关内容
	2.  Eclipse菜单: Windows->Show View-->Git->Git Staging 显示提交窗口
		1.  这个窗口与 git gui的窗口类似，可以在这里进行提交
	3. 建议源文件有了重要修改或重要内容增加都要进行提交
	
6. 上述操作截图撰写作业文档，使用md格式保存信息，文件名为“作业2.md”


## SOA第三次作业
4/13/2026 
本次作业主要是熟悉Java中流的概念，使用流编程。
1. 学习、编程使用BufferedOutputStream, FileOutputStream, OutputStream等流的配合，实现将Java中的基本数据类型输出到指文件中。输出内容至少包含：
	1. 比如 int i= 12;
	2. 自己的学号字符串信息 String sno= "31xxxxxxx"; 
2. 详细论述作业的实现的基本原理，特别是对这程序中使用的几个流类的分析。
3. 尝试更多流的级联。
4. 将上述内容撰写作业文档，使用md格式保存信息，文件名为“作业3.md”
5. 本周四（4月16日）服务器开机上时上交。
6. 修改前面作业中出现的错误或不足之处。


## SOA第四次作业
4/17/2026
1. 学习输入流，将上次作业中存入文件中的数据读取并输出到控制台。
2. 使用Scanner,System.io,StringBuilder等类，编写简单的文本界面计算器。
3. 所有内容用git管理，每增加一定功能就是有一次提交。
3. 将上述内容撰写作业文档，使用md格式保存信息，文件名为“作业4.md”
4. 修改前面作业中出现的错误或不足之处。
5. 下周四（4月23日）服务器开机上时上交。


编写简单的文本界面计算器，要求如下：
提示信息：

```
简单运算器，作者 31xxxxxx
+ 加法
- 减法
* 乘法
\ 除法
q 退出程序
输入你的选择（字符 +,-,*,/ 或 q）： 
```
当用户输入某一个运行命令后，比如 +，提示用户输入用空格分隔的两个整数，最后计算、输出出它们的和。下面是输出例子（其中的花括号内容是提示内容）

```
简单运算器，作者 31xxxxxx  {自己的学号}
+ 加法
- 减法
* 乘法
\ 除法
q 退出程序
输入你的选择（字符 +,-,*,/ 或 q）： + {回车} 

你选择的命令是加法，输入两个整数，用空格分隔： 1 2  {回车} 
1+2 = 3

```

然后循环显示提示信息，直到用户选择退出。


## SOA第五次作业
4/24/2026 
1. 学习java语言的Socket， ServerSocket （也可以使用其它语言提供的相应Socket）
2. 编写一个应答服务器，可以实现用户输入应答功能。
2. git管理所有内容，注意每次增加、修改重要内容（代码或文档）都要有一次提交。
3. 将上述内容撰写文档，使用md格式保存信息，文件名为“作业5.md”
4. 修改前面作业中出现的错误或不足之处。
5. 下周四（4月30日）服务器开机上时上交。


编写简单的应答服务器，要求如下：
1. 需要有自己的人个信息，比如 学号信息
1. 用户输入 q 或 Q 退出程序，
2. 用户输入其它信息，则返回用户输入
3. 使用telnet 作为客户端与服务器通信.windows启动telnet ，打开cmd窗口 运行telnet 

代码基本框架参考
```
ServerSocket server = new ServerSocket(12345);
System.out.println("server listening: ");
while(true)
{
Socket client = server.accept();
PrintWriter out = new PrintWriter(client.getOutputStream());
Scanner sc = new Scanner(new BufferedInputStream(client.getInputStream()));
while (true) {
	out.println("Hi, I am  31xxxxx.\r\n Enter 'q' or 'Q' to exit.");  // 这里是自己的学号
	String inputLine = sc.nextLine().trim();
	if ("Q".compareToIgnoreCase(inputLine) == 0)
	{
		out.println("bye!");
		break;
	}
	else
	{	
		out.println("Your input is: " + inputLine);
	}
}
}
```
需要注意的是上面代码片段省略了异常处理，其它内容需要自己补充完整。


## SOA第六次作业
4/30/2026 
1. 修改应答服务器为聊天服务器，可以实现多用户相互发送消息
2. git管理所有内容，注意每次增加、修改重要内容（代码或文档）都要有一次提交。
3. 按照课程设计要求撰写设计实现文档，使用md格式保存信息，文件名为“作业6.md”
4. 修改前面作业中出现的错误或不足之处。


编写聊天服务器，要求如下：
1. 需要有自己的人个信息，比如 学号信息
1. 服务器可以提供至少以下几个功能
	1. 支持新用户的注册
	2. 支持用户登录功能
	3. 支持查找用户功能
	4. 支持向某一个用户发送消息功能
	5. 支持退出功能
3. 使用telnet 作为客户端与服务器通信，模拟多个用户相互通信.
	1. windows启动telnet ，打开cmd窗口 运行telnet

编写提示（参考）：
1. 创建为每个客户提供服务的类，实现Runnable接口, 代码片段如下
```
class ClientService implements Runnable
{
	// 这里保存对应的客户socket
	private Socket client
	public ClientService(Socket client)
	{
		this.client = client;
	}
	@Override
	public void run() {
		// 这里是实现与指定客户通信的代码
	}
}
``` 
2. 修改服务器代码，将单线程的服务代码修改为多线程处理方式,代码片段如下
```
while (true) {
	Socket client = server.accept();
		new Thread(new ClientService(client))).start(); 
	}
```
> 建议学习使用线程池
> ```
> ExecutorService pool = Executors.newFixedThreadPool(30);
> while (true) {
> 	Socket client = server.accept();
> 	pool.submit(new ClientService(client))); 
> }
> ```
3. 设计客户与服务器之间的数据通信协议和命令格式
	1. 数据格式: A,B
		1. A : 为3位数字字符，代表命令；B：与命令相关的数据
	2. 命令（参考）有：
		1. 000： 无意义
		2. 001： 用户注册命令，数据为： id, password  发送方为客户
		3. 002: 用户登录命令，数据为： id, password 发送方为客户
		4. 003： 用户退出命令，数据为: id 发送方为客户
		5. 004： 查找用户命令，数据为: 查找模式 发送方为客户
		5. 005： 发送消息命令，数据为: srcId, desId, msg, 发送方为客户
		6. 101: 用户注册结果，数据为: id, 结果数据, 发送方为服务器
			1. 结果数据： 成功或失败，如果是失败给出失败原因，比如id已被其它用户（包括）使用
		7. 102: 用户登录结果，数据为: id, 结果数据 , 发送方为服务器
			1. 结果数据： 成功或失败，如果是失败给出失败原因，比如id或passowrd错误
		8. 103： 用户退出结果，数据为: id, 发送方为服务器
		9. 104： 查找用户命令结果，数据为: id,id1,id2,....  发送方为服务器
			1. id : 发送命令的用户id，
			2. 后面是服务器中已注册的用户id列表
		9. 105： 服务器群发命令，数据为: , msg，发送方为服务器
		10. 106：服务器转发命令，数据为: srcId, desId, msg，发送方为服务器

4. 用户之间的消息发送采用服务器转发方式实现
	1. 用户A需要将消息msg发送给用户B时，需要向服务器发送数据： 005,A,b,msg
	2. 服务器收到消息后将数据 A,b,msg 保存到消息队列中
	3. 服务器发送消息的线程将 消息队列中的消息发送给
	4. 实现原理
		1. 服务器维护一个队列，队列元素中记录 “srcId, desId, msg”等数据。
		2. 设计一个消息发送类 class sendMsgService implements Runnable
			1. 该类 run()方法实现转发队列中的消息。
		2. 服务器在调用accept()前，需要创建 sendMsgService线程。
		2. 创建客户服务线程 ClientService 时，需要将这个队列引用传递进去。
		3. sendMsgService线程与 sendMsgService线程在访问消息队列时采用消费/生产者模式实现操作同步。
5. 所有用户信息都记录在内存中，且只记录已登录用户。 （选作：可以将数据保存到数据库）
6. 系统的所用关键操作都记录并保存到日志文件中。 
> 注意：所有数据均为字符串，id为用户标识且中间不能有空格或逗号，数据串中的逗号是数据部分的分隔字符，
> 由于用户发送的消息中id为普通字符串（用户名），socket在发送消息时需要使用的是IP：Port，
> 所以服务器需要维护一个id与socket之间的映射关系，并提供两者之间的相互转换功能。
4. 该作业为大作业，在最后的实验课前完成，以上内容仅供参考，可以根据实际情况增加或调整相关内容。

## SOA第七次作业
5/8/2026  
1. 自学http协议
2. 使用 ServerSocket 编程实现显示浏览器http命令内容。
3. 使用抓包软件查看http各种命令的数据格式
3. 将上述内容撰写文档，使用md格式保存信息，文件名为“作业7.md”
4. 修改前面作业中出现的错误或不足之处。
5. 下周四上交作业

代码参考
```
server = new ServerSocket(80);
Socket client = server.accept();
InputStream in = client.getInputStream();
in = new BufferedInputStream(in);
InputStreamReader reader = new InputStreamReader(in, "iso-8859-1");
Scanner scanner = new Scanner(reader);
	while (true) {
		String line = scanner.nextLine();
		if (line.isEmpty())
			break;
		System.out.println(line);
		}

```

-------------------------
广东工业大学计算机学院 cshao@gdut.edu.cn

<style>
body {
    background-image: url("cshao.png");
    background-repeat: repeat;
    opacity: 0.95;
}
</style>

