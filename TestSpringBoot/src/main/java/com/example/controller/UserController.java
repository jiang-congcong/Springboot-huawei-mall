package com.example.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.dao.Getsqlsession;
import com.example.entity.Goods;
import com.example.entity.User;


/*@RestController
@EnableAutoConfiguration*/

@Controller
public class UserController {
	/*@RequestMapping("/")
	public String index() {
	return "Hello Spring Boot!";
	}
	@RequestMapping("/hello")
	String test1(String username) {
	return "Hello " + username;
	}*/
	
	/*@Autowired
	User user;
	@RequestMapping("/details/{id}")
	public User test2(@PathVariable int id) {
	user.setId(id); user.setUsername("zhang"); user.setPassword("123456");
	return user;
	}
	@RequestMapping("/show/{username}/{password}")
	public Map<String, String> test3(@PathVariable("username") String username,
	@PathVariable("password") String password) {
	Map<String, String> map = new HashMap<>();
	map.put("username", username); map.put("password", password);
	return map;
	}*/
	
	
	@Autowired
	User user;
	@RequestMapping("/show")
	public String test4(Model model) {
	user.setId(1); user.setUsername("zhang"); user.setPassword("123456");
	model.addAttribute("user",user);
	return "show";
	}
	@RequestMapping("/")
	@ResponseBody
	public String index() {
	return "Hello Spring Boot!";
	}
	
	@Autowired
	User user1;
	@Autowired
	Getsqlsession sqlsession;
	@RequestMapping("/login")
	//@ResponseBody
	public String login(String user,String pass,Model model) {	
		

		/*String resource = "mybatis/mybatis-config.xml";   
        InputStream inputStream=null;
		try {
			inputStream = Resources.getResourceAsStream(resource);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();*/
		
		SqlSession sqlSession=sqlsession.getsqlsession();
        String statement = "mapper.userMapper.getUser";    //namespace+id找到映射sql串
        String user2 = sqlSession.selectOne(statement, 1);   
        sqlSession.close();
        if(user.equals("zz")) {
              model.addAttribute("errormsg","无效");
        
              return "index";
        }
		
        //return "index";
        return "Main";
		//return user2;
	}
	
	
	@RequestMapping("/first")
	public String login1() {	
	return "index";
	}
	
	@RequestMapping("/register1")
	public String register1() {	
	     return "register";
	}
	
	@RequestMapping("/honor10")
	public String honor10() {	
	     return "honor-10";
	}
	
	
	@RequestMapping("/shoppingcar")
	@ResponseBody
	public String shoppingcar(String price,String color,String version) {
		
		Goods good = new Goods();
		good.setGoods("荣耀10");
		good.setPrice(price);
		good.setColor(color);
		good.setVersion(version);
		SqlSession sqlSession1=sqlsession.getsqlsession();
        String statement1 = "mapper.userMapper.addshoppingcar";    //namespace+id找到映射sql串
        sqlSession1.insert(statement1,good);
        sqlSession1.commit();
        sqlSession1.close();
		//return price+color+version;
        return "<input type='submit' value='登录' id='login' style='width: 20%;'/>";
	}
	
	
	@RequestMapping("/register")
	public String register(String name,String password,String password_confirm,Model model) {
		if(!password.equals(password_confirm)) {
			model.addAttribute("errorregistermsg","密码不一致");
			return "register";
		}
		
		else if(name.length()==0||password.length()==0){
			model.addAttribute("errorregistermsg","用户名或密码必填");
			return "register";
		}
		
		
		SqlSession sqlSession=sqlsession.getsqlsession();
        String statement = "mapper.userMapper.checkUser";    //namespace+id找到映射sql串
        User user = sqlSession.selectOne(statement, name);   
        sqlSession.close();
        
        if(user!=null) {
        	model.addAttribute("errorregistermsg","此用户名已注册！");
        	return "register";
        }
  
        User u=new User();
        u.setUsername(name);
        u.setPassword(password);
        SqlSession sqlSession1=sqlsession.getsqlsession();
        String statement1 = "mapper.userMapper.addUser";    //namespace+id找到映射sql串
        int mid = sqlSession1.insert(statement1, u);
        sqlSession1.commit();
        sqlSession1.close();             
		return "index";
	}
	

}
