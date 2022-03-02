package ru.nvn.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.nvn.spring.dao.PersonDAO;
import ru.nvn.spring.models.FormDataWithFile;
import ru.nvn.spring.models.Person;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController
{

		private final PersonDAO personDAO;

		@Autowired
		public PeopleController(PersonDAO personDAO) {
				this.personDAO = personDAO;
		}

		@GetMapping()
		public String index(Model model) {
				model.addAttribute("people", personDAO.index());
				return "people/index.html";
		}

		@GetMapping("/{id}")
		public String show(@PathVariable("id") int id, Model model) {
				model.addAttribute("person", personDAO.show(id));
				return "people/show.html";
		}

		@GetMapping("/new")
		public String newPerson(@ModelAttribute("person") Person person) {
				return "people/new";
		}

		@PostMapping("/new")
		public String create(@ModelAttribute("person") @Valid Person person,
				BindingResult bindingResult) {
				if (bindingResult.hasErrors())
						return "people/new.html";

				personDAO.save(person);
				return "redirect:/people";
		}

		@GetMapping("/{id}/edit")
		public String edit(Model model, @PathVariable("id") int id) {
				model.addAttribute("person", personDAO.show(id));
				return "people/edit.html";
		}

		@PatchMapping("/{id}")
		public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
				@PathVariable("id") int id) {
				if (bindingResult.hasErrors())
						return "people/edit.html";

				personDAO.update(id, person);
				return "redirect:/people";

		}

		@DeleteMapping("/{id}")
		public String delete(@PathVariable("id") int id) {
				personDAO.delete(id);
				return "redirect:/people";
		}

		////////////////////////////////////////////////////////////////////////////
		@GetMapping(value = "/fileUpload")
		public String displayForm(@ModelAttribute("file")  MultipartFile file) {
				return "people/upload.html";
		}

		@PostMapping("/fileUpload")
		public String submit(@ModelAttribute("file")  final MultipartFile file, final ModelMap modelMap) {
				modelMap.addAttribute("file", file);
				return "people/fileUploadView.jsp";
		}

		@PostMapping( "/uploadMultiFile")
		public String submit(@ModelAttribute("files") final MultipartFile[] files, final ModelMap modelMap) {

				modelMap.addAttribute("files", files);
				return "people/fileUploadView.jsp";
		}

		@PostMapping( "/uploadFileWithAddtionalData")
		public String submit(@ModelAttribute("file") @RequestParam final MultipartFile file, @RequestParam final String name, @RequestParam final String email, final ModelMap modelMap) {
				modelMap.addAttribute("name", name);
				modelMap.addAttribute("email", email);
				modelMap.addAttribute("file", file);
				return "people/fileUploadView.jsp";

		}

		@PostMapping( "/uploadFileModelAttribute")
		public String submit(@ModelAttribute final FormDataWithFile formDataWithFile, final ModelMap modelMap) {
				modelMap.addAttribute("formDataWithFile", formDataWithFile);
				return "people/fileUploadView.jsp";

		}


}
