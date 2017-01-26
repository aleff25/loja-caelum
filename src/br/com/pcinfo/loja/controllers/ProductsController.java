package br.com.pcinfo.loja.controllers;

import java.io.IOException;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.pcinfo.loja.dao.ProductDao;
import br.com.pcinfo.loja.models.BookType;
import br.com.pcinfo.loja.models.FileSaver;
import br.com.pcinfo.loja.models.Product;


@Controller
@Transactional
@RequestMapping("/produtos")
public class ProductsController {

	@Autowired
	private ProductDao products;
	@Autowired
	private FileSaver fileSaver;
	
	@InitBinder
    protected void initBinder(WebDataBinder binder) {
		//binder.setValidator(new ProductValidator());
    }	
	

	@RequestMapping(method=RequestMethod.POST)
	@CacheEvict(value="lastProducts", allEntries=true)
	public ModelAndView save(MultipartFile summary,@ModelAttribute("product") @Valid Product product,BindingResult bindingResult,RedirectAttributes redirectAttributes) throws IOException{
		if(bindingResult.hasErrors()){
			return form(product);
		}
		
		//Sera que passo o product como parametro?
		String webPath = fileSaver.write("uploaded-images",summary);
		product.setSummaryPath(webPath);
		products.save(product);
		
		redirectAttributes.addFlashAttribute("success", "Produto cadastrado com sucesso");
		return new ModelAndView("redirect:produtos");
	}
	
	@RequestMapping("/form")
	public ModelAndView form(@ModelAttribute Product product){
		ModelAndView modelAndView = new ModelAndView("produtos/cadastro-produtos");
		modelAndView.addObject("bookTypes", BookType.values());
		return modelAndView;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	@Cacheable(value="lastProducts")
	public ModelAndView list(){
		ModelAndView modelAndView = new ModelAndView("produtos/lista-livros");
		modelAndView.addObject("products", products.lista());
		return modelAndView;
	}
	
	
	@RequestMapping("/{id}")
	public ModelAndView show(@PathVariable("id") Integer id){
		ModelAndView modelAndView = new ModelAndView("produtos/show");
		Product product = products.find(id);
		modelAndView.addObject("product", product);
		return modelAndView;
	}

	
}
