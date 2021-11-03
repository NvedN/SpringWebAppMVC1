package ru.nvn.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.nvn.spring.dao.PersonDAO;

import java.util.ArrayList;

@Controller
@RequestMapping("/people")
public class PeopleController {

		private PersonDAO personDAO;

		@Autowired
		public  PeopleController(PersonDAO personDAO){
				this.personDAO = personDAO;
		}

		@GetMapping()
		public String index(Model model){
				//Получаем всех людей из DAO и передаем на отображение в представление
				model.addAttribute("people",personDAO.index());
				return "people/index";
		}

		@GetMapping("/{id}")
		public String show(@PathVariable("id") int id, Model model) {
				model.addAttribute("person", personDAO.show(id));
				return "people/show";
		}

}