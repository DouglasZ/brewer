package com.algaworks.brewer.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.brewer.model.Cerveja;

@Controller
public class CervejasController
{
	// Por padrão é GET
	// Por padrão essa chamada é Forward
	@RequestMapping( "/cervejas/novo" )
	public String novo( Cerveja cerveja ) // O spring consegue identificar o objeto no formulário
	{
		return "cerveja/CadastroCerveja";
	}

	/**
	 * 
	 * @param cerveja
	 * @param result vem do BindingResult onde pega as validações do @Valid para poder apresentar os erros
	 * @return
	 */
	//Quando for especificado o método eu posso usar a mesma chamada
	@RequestMapping( value = "/cervejas/novo", method = RequestMethod.POST )
	public String cadastrar( @Valid Cerveja cerveja, BindingResult result, Model model, RedirectAttributes attributes )
	{
		if ( result.hasErrors() )
		{
			//Se não usar o redirect ele se torna Forward por padrão
			//Forward recebe a requisição e retorna direto
			return "cerveja/CadastroCerveja";
		}
		
		// Salvar no banco de dados...
		
		//Quando usado o redirect deve usar o "RedirectAttributes" pois quando a página é carregada ele não perde a mensagem
		//É criado uma sessão temporária para aparecer essa mensagem
		attributes.addFlashAttribute( "mensagem", "Cerveja salva com sucesso!" );
		System.out.println( ">>> sku: " + cerveja.getSku() );
		//Redirect faz um redirecionamento para uma URL, ou seja, é feito uma nova requisição
		return "redirect:/cervejas/novo";
	}
}