package br.com.pcinfo.loja.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@RequestMapping("/home")
	public String index(){
		System.out.println("Carregando os Produtos");
		return "hello-world";
	}
}
