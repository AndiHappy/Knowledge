package com.springboot.example.dao;

import org.springframework.stereotype.Repository;

@Repository
//@Component
//
//Spring 2.5 中除了提供 @Component 注释外，还定义了几个拥有特殊语义的注释，
//它们分别是：@Repository、@Service 和 @Controller。在目前的 Spring 版本中，
//这 3 个注释和 @Component 是等效的，但是从注释类的命名上，
//很容易看出这 3 个注释分别和持久层、业务层和控制层（Web 层）相对应。
//虽然目前这 3 个注释和 @Component 相比没有什么新意，
//但 Spring 将在以后的版本中为它们添加特殊的功能。
//所以，如果 Web 应用程序采用了经典的三层分层结构的话，
//最好在持久层、业务层和控制层分别采用 @Repository、@Service 和 @Controller 
//对分层中的类进行注释，而用 @Component 对那些比较中立的类进行注释。
public class UsualDao {

	public String getDao(){
		return "hello";
	}

	public String getDao(String daoName) {
		return "hello," +daoName;
				
	}
}
