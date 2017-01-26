package br.com.pcinfo.loja.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.pcinfo.loja.dao.ProductDao;
import br.com.pcinfo.loja.models.BookType;
import br.com.pcinfo.loja.models.Product;
import br.com.pcinfo.loja.models.ShoppingCart;
import br.com.pcinfo.loja.models.ShoppingItem;

//@Scope(value=WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping("/shopping")
public class ShoppingCartController {

	@Autowired
	private ProductDao productDao;

	@Autowired
	private ShoppingCart shoppingCart;

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView add(Integer productId, BookType bookType) {
		ShoppingItem item = createItem(productId, bookType);
		shoppingCart.add(item);
		return new ModelAndView("redirect:produtos");
	}

	private ShoppingItem createItem(Integer productId, BookType bookType) {
		Product product = productDao.find(productId);
		ShoppingItem item = new ShoppingItem(product, bookType);
		return item;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String items(){
		return "shoppingCart/items";
	}
}
