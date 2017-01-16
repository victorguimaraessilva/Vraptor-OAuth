package br.com.victorguimas.oauth.daos;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import br.com.victorguimas.oauth.models.Auth;
import br.com.victorguimas.oauth.models.User;

public class AuthDao
{

   @Inject
   private EntityManager manager;

   public List<Auth> all()
   {
      return manager.createQuery("select u from Auth u", Auth.class).getResultList();
   }

   public void save(Auth auth)
   {
      manager.persist(auth);
   }

   public Auth findById(Integer id)
   {
      return manager.find(Auth.class, id);
   }
   
   public Auth findByUser(User user)
   {
	   Auth auth = null;
	   try{
		   auth = manager.createQuery("select u from Auth u where u.user = ?1", Auth.class).setParameter(1, user).getSingleResult();
	   }
	   catch (NoResultException nre){
	   //Ignore this because as per your logic this is ok!
	   }

	   if(auth == null){
	     return null;
	   }else{
		 return auth;
	   }
   }
   
   public void remove(Auth auth)
   {
      manager.remove(auth);
   }

   public void update(Auth auth)
   {
      manager.merge(auth);
   }

}
