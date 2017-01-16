package br.com.victorguimas.oauth.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.inject.Inject;
import br.com.victorguimas.oauth.models.User;

public class UserDao
{

   @Inject
   private EntityManager manager;

   public List<User> all()
   {
      return manager.createQuery("select u from User u", User.class).getResultList();
   }

   public void save(User user)
   {
      manager.persist(user);
   }

   public User findById(Integer id)
   {
      return manager.find(User.class, id);
   }
   
   public User findByUsername(String username)
   {
	   
	   User user = null;
	   try{
		   user = manager.createQuery("select u from User u where u.username = ?1", User.class).setParameter(1, username).getSingleResult();
	   }
	   catch (NoResultException nre){
	   //Ignore this because as per your logic this is ok!
	   }

	   if(user == null){
	     return null;
	   }else{
		 return user;
	   }
   }
   
   public User findByUsernameAndPassword(String username, String password)
   {
	   
	   User user = null;
	   try{
		   user = manager.createQuery("select u from User u where u.username = ?1 and u.password =?2", User.class).setParameter(1, username).setParameter(2, password).getSingleResult();
	   }
	   catch (NoResultException nre){
	   //Ignore this because as per your logic this is ok!
	   }

	   if(user == null){
	     return null;
	   }else{
		 return user;
	   }
   }

   public void remove(User user)
   {
      manager.remove(user);
   }

   public void update(User user)
   {
      manager.merge(user);
   }

}
