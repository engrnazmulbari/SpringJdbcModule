package edu.mum.app;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.mum.dao.CircleDaoImpl;
import edu.mum.dao.HibernateDaoImpl;
import edu.mum.dao.SimpleJdbcTemplate;
import edu.mum.model.Circle;

public class App {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
		CircleDaoImpl circleDao = context.getBean("circleDaoImpl",CircleDaoImpl.class);
		//Circle circle = circleDao.getCircle(1);
		System.out.println("Circle Name: "+circleDao.gettCircle(1));
		System.out.println(circleDao.getCircleCount());
		Circle circle = circleDao.getCircleObject(1);
		System.out.println(circle.getName());
		circleDao.insertCircle(new Circle(3,"Third circle"));
		circleDao.insertNamedCircle(new Circle(4,"Forth circle"));
		List<Circle> ci = circleDao.getAllCircle();
		for(Circle c:ci){
			System.out.println("Id: "+c.getId()+" Name: "+c.getName());
		}
		
		SimpleJdbcTemplate dao = context.getBean("simpleJdbcTemplate",SimpleJdbcTemplate.class);
		System.out.println("Total Number of Circle: "+dao.circleCount());
		//HibernateDaoImpl hibDao = context.getBean("hibernateDaoImpl",HibernateDaoImpl.class);
		//System.out.println("Hibernate Count: "+hibDao.circleCount());

	}

}
