<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
  	        <div class="form-group">
          <label for="name">name</label>
          <div class="input-group">
            <span class="input-group-addon"><i class="glyphicon glyphicon-unchecked"></i></span><input type='text' name='product.name' id='product.name' value='${product.name}'/>
          </div>
        </div>
        <div class="form-group">
          <label for="description">description</label>
          <div class="input-group">
            <span class="input-group-addon"><i class="glyphicon glyphicon-unchecked"></i></span><input type='text' name='product.description' id='product.description' value='${product.description}'/>
          </div>
        </div>
        <div class="form-group">
          <label for="price">price</label>
          <div class="input-group">
            <span class="input-group-addon"><i class="glyphicon glyphicon-unchecked"></i></span><input type='text' name='product.price' id='product.price' value='${product.price}'/>
          </div>
        </div>
        <div class="form-group">
          <label for="category.id">category.id</label>
          <div class="input-group">
            <span class="input-group-addon"><i class="glyphicon glyphicon-unchecked"></i></span><select name='product.category.id' id='product.category.id'>
<c:forEach items='${categoryList}' var='currentItem'>
<option value='${currentItem.id}' ${currentItem.id == product.category.id ? 'selected' : ''}>${currentItem.name}</option>
</c:forEach>
</select>
          </div>
        </div>
