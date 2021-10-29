package ru.nvn.spring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/first")
public class FirstController
{

		@GetMapping("/hello")
		public String helloPage(@RequestParam(value = "firstName", required = false) String firstName,
				@RequestParam(value = "lastName", required = false) String lastName,
				Model model)
		{
				//				System.out.println("Hello, " + firstName + " " + lastName);
				model.addAttribute("message", "Hello, " + firstName + " " + lastName);
				return "first/hello";
		}

		@GetMapping("/goodbye")
		public String goodByePage()
		{
				return "first/goodbye";
		}

		@GetMapping("/calculator")
		public String calculator(@RequestParam(value = "a", required = false) Integer a,
				@RequestParam(value = "b", required = false) Integer b,
				@RequestParam(value = "action", required = false) String action, Model model)
		{
				double result;
				switch (action)
				{
				case "multiplication":
						result = a * b;
						break;
				case "division":
						result = a / (double) b;
						break;
				case "substraction":
						result = a - b;
						break;
				case "adiction":
						result = a+b;
						break;
				default:
						result = 0;
						break;
				}
				model.addAttribute("result",result);
				return "first/calculator";
		}
}
