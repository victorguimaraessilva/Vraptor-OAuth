package br.com.victorguimas.oauth.controllers;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Validator;
import br.com.victorguimas.oauth.daos.UserDao;
import br.com.victorguimas.oauth.models.Product;
import br.com.victorguimas.oauth.models.User;

@Controller
@Path("/register")
public class RegisterController
{


   @Inject
   private UserDao userDao;
   @Inject
   private Validator validator;
   @Inject
   private Result result;

   //Se o usuário entrar na URL /register/newUser ele irá encontrar o formulário de novo usuário
  
   @Get("/newUser")
   @Transactional
   public void registerPage()
   {
     
	   
   }

   @Post("")
   @Transactional
   public void save(@Valid User user)
   {
	   
	  System.out.println(user.getName());
	  System.out.println(user.getUsername());
	  System.out.println(user.getPassword());
      //Caso usuário nao seja válido irá retornar para a página de formulário de novo usuário
	  validator.onErrorForwardTo(RegisterController.class).registerPage();
	  
	  //Agora iremos verificar se já existe usuário com este username
	  
	  User userExistent = this.userDao.findByUsername(user.getUsername());
	  
	  if(userExistent != null){
		  //Caso usuário seja válido e porém já existir, irá redirecionar para o login
	      this.result.redirectTo(HomeController.class).index();
	  }else{
		  
		  //Caso usuário seja válido e não existir, irá salvar o usuário e irá redirecionar para o login
	      this.userDao.save(user);
	      this.result.redirectTo(HomeController.class).index();
		  
	  }
	  
	  
   }


  
}
