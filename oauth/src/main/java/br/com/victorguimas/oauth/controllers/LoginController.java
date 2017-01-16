package br.com.victorguimas.oauth.controllers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Validator;
import br.com.caelum.vraptor.view.Results;
import br.com.victorguimas.oauth.daos.AuthDao;
import br.com.victorguimas.oauth.daos.UserDao;
import br.com.victorguimas.oauth.models.Auth;
import br.com.victorguimas.oauth.models.User;

@Controller
@Path("/login")
public class LoginController
{


   @Inject
   private UserDao userDao;
   @Inject
   private AuthDao authDao;
   @Inject
   private Validator validator;
   @Inject
   private Result result;

   
   @Post("")
   @Transactional
   public void save(String username, String password)
   {
	   
	  System.out.println(username);
	  System.out.println(password);
     
	  User userExistent = this.userDao.findByUsernameAndPassword(username, password);
	  
	  if(userExistent != null){
		  
		  //Caso exista autenticacao, irá deletar e criar uma nova
		  
		  Auth auth = this.authDao.findByUser(userExistent);
		  if(auth != null){
			  this.authDao.remove(auth);
		  }
		  
		  //Caso usuário seja válido irá Logar no sistema informando
		  //Token de acesso
		  //refresh_token
		  //client_id
		  //expired_at.
		  
		  //Iremos criar o access_token, o client_id, e o refresh token utilizando um Hash e gerando o Hash a partir
		  //do usuário+senha+nome+timestamp criando assim uma aleatoriedade
		  Timestamp dataDeHoje = new Timestamp(System.currentTimeMillis());
		  
		  String access_token = username.concat(password.concat(dataDeHoje.toString()));
				  
		  String refresh_token = password.concat(username.concat(dataDeHoje.toString()));
		  
		  String client_id = dataDeHoje.toString().concat(username.concat(username.concat(password.concat(userExistent.getId().toString()))));
		  
		  Calendar calendar = Calendar.getInstance();
		  calendar.setTime(new Date());
		  //O segundo paramentro mostra qnts segundos vc quer adicionar!
		  calendar.add(Calendar.SECOND, 3600);
		  
		  Date expires_at = calendar.getTime();
		  
		  Auth newAuth = new Auth();
		  newAuth.setAccess_token(gerarHash(access_token));
		  newAuth.setClient_id(gerarHash(client_id));
		  newAuth.setExpires_at(expires_at);
		  newAuth.setRefresh_token(gerarHash(refresh_token));
		  
		  newAuth.setUser(userExistent);
		  
		  this.authDao.save(newAuth);
		  
		  this.result.use(Results.json()).withoutRoot().from(newAuth).serialize();
	  }else{
		  
		  this.result.use(Results.json()).withoutRoot().from("access_denied").serialize();
	  }
	  
	  
   }
   
   public String gerarHash(String input){
	   
	   MessageDigest md = null;
		
	   try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    md.update(input.getBytes());
	    String hex = (new HexBinaryAdapter()).marshal(md.digest());
	    System.out.println(hex);
	    return hex;
   }


  
}
