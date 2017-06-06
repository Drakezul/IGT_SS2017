package assignment_2_3;

import java.math.BigDecimal;
import java.sql.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.fasterxml.classmate.AnnotationConfiguration;

import data.Customer;
import data.CustomerOrder;

public class NestedTransaction_Main {

	private static SessionFactory factory;

	public static void main(String[] args) {
		factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

		Session session = factory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			transaction = session.beginTransaction();
			// transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

//	public static void main(String[] args) {
//		Configuration config = new Configuration().configure("hibernate.cfg.xml");
//		config.addAnnotatedClass(data.CustomerOrder.class);
//		config.addAnnotatedClass(data.Customer.class);
//		factory = config.buildSessionFactory();
//
//		Session session = factory.openSession();
//		Transaction transaction = null;
//		try {
//			transaction = session.beginTransaction();
//			//
//			Customer customer1 = new Customer();
//			customer1.setBirthdate(new Date(System.currentTimeMillis()));
//			session.saveOrUpdate(customer1);
//			//
//			CustomerOrder order1 = new CustomerOrder();
//			order1.setPrice(new BigDecimal("+3.50"));
//			customer1.addCustomerOrder(order1);
//			session.saveOrUpdate(order1);
//			//commit
//			transaction.commit();
//		} catch (Exception e) {
//			if (transaction != null) {
//				transaction.rollback();
//			}
//			e.printStackTrace();
//		} finally {
//			session.close();
//			factory.close();
//		}
//	}

}
