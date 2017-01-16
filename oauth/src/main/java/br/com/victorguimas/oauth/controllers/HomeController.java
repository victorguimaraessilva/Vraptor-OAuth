package br.com.victorguimas.oauth.controllers;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Path;

@Controller
public class HomeController
{

   @Inject
   private Result result;

   @Path("/")
   public void index()
   {
      result.include("msg1", "1º Registrar Usuário");
      result.include("msg2", "2º Logar no Sistema");
   }
}