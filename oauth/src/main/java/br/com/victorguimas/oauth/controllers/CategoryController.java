package br.com.victorguimas.oauth.controllers;

import javax.inject.Inject;
import javax.validation.Valid;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.validator.Validator;
import javax.transaction.Transactional;

import br.com.victorguimas.oauth.models.Category;
import br.com.victorguimas.oauth.daos.CategoryDao;

@Controller
@Path("/category")
public class CategoryController
{

   @Inject
   private CategoryDao categoryDao;
   @Inject
   private Validator validator;
   @Inject
   private Result result;

   @Get("/form")
   public void formAdd(Category category)
   {
      result.include("category", category);
   }

   @Post("")
   @Transactional
   public void save(@Valid Category category)
   {
      validator.onErrorForwardTo(CategoryController.class).formAdd(category);
      categoryDao.save(category);
      result.redirectTo(CategoryController.class).list(0);
   }

   @Get("/{category.id}")
   public void formUpdate(Category category)
   {
      result.include("category", categoryDao.findById(category.getId()));
   }

   @Get("")
   public void list(Integer page)
   {
      if (page == null)
      {
         page = 0;
      }
      result.include("paginatedList", categoryDao.paginated(page, 10));
   }

   //just because get is easier here. Be my guest if you want to change.
   @Get("/remove/{id}")
   @Transactional
   public void remove(Integer id)
   {
      Category category = categoryDao.findById(id);
      categoryDao.remove(category);
      result.redirectTo(CategoryController.class).list(0);
   }

   @Post("/{id}")
   @Transactional
   public void update(Integer id, @Valid Category category)
   {
      category.setId(id);
      validator.onErrorForwardTo(CategoryController.class).formUpdate(category);

      categoryDao.update(category);
      result.redirectTo(CategoryController.class).list(0);
   }
}
