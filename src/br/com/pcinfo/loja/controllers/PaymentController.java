package br.com.pcinfo.loja.controllers;

import java.math.BigDecimal;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import br.com.pcinfo.loja.models.PaymentData;
import br.com.pcinfo.loja.models.ShoppingCart;
import br.com.pcinfo.loja.models.SystemUser;
import sirius.web.mails.MailService.MailSender;

@Controller
@RequestMapping("/payment")
public class PaymentController {

	@Autowired
	private ShoppingCart shoppingCart;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private MailSender mailer;

	@RequestMapping(value = "/checkout", method = RequestMethod.POST)
	public @ResponseBody Callable<ModelAndView> checkout(@AuthenticationPrincipal SystemUser user) {
		return () -> {
			BigDecimal total = shoppingCart.getTotal();
			String uriToPay = "http://book-payment.herokuapp.com/payment";
			try {
				restTemplate.postForObject(uriToPay, new PaymentData(total), String.class);
				sendNewPurchaseMail(user);
				return new ModelAndView("redirect:/payment/success");
			} catch (HttpClientErrorException exception) {
				return new ModelAndView("redirect:/payment/error");
			}

		};
	}

	private void sendNewPurchaseMail(SystemUser user) {
		SimpleMailMessage email = new SimpleMailMessage();
		email.setFrom("compras@casadocodigo.com.br");
		email.setTo(user.getLogin());
		email.setSubject("Nova compra");
		email.setText("corpodo email");
		mailer.send(email);
	}
}
