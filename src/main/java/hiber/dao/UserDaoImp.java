package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository //Репозиторий, интерфейс - который импользует JPA Entities для взаимодейся с ней Другими словами - аннотация для ДАО
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   @SuppressWarnings("unchecked")
   public User getUserByCar(Car car) {
      String hql = "select u.id, u.firstName, u.lastName, u.email from Car c left join User u on u.id = c.id where c.name = :name and c.series = :series";
      TypedQuery<Object> query = sessionFactory.getCurrentSession().createQuery(hql)
              .setParameter("name",car.getName())
              .setParameter("series",car.getSeries());
      Object[] obj = (Object[]) query.getResultList().get(0);
      return new User(Long.parseLong(obj[0].toString()), obj[1].toString(), obj[2].toString(), obj[3].toString());
   }

}
