package ru.nvn.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.nvn.spring.dao.PersonDAO;
import ru.nvn.spring.files.FileParseUtil;
import ru.nvn.spring.models.FormDataWithFile;
import ru.nvn.spring.models.Person;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController
{

		private final PersonDAO personDAO;


		/**
		 * constructor for PeopleController class
		 *
		 * @param personDAO {@code PersonDAO} -object based on "Data Access Object (DAO)" pattern
		 * @return Response   {@code String } html main page
		 * @author NVN
		 * @since 11.03.2022
		 */
		@Autowired
		public PeopleController(PersonDAO personDAO) {
				this.personDAO = personDAO;
		}


		/**
		 * Get main page
		 *
		 * @param model {@code Model} -Java-5-specific interface that defines a holder for model attributes.
		 *                             Primarily designed for adding attributes to the model. Allows for accessing the overall model as a java.util.Map.
		 * @return Response   {@code String } html main page
		 * @author NVN
		 * @since 11.03.2022
		 */
		@GetMapping()
		public String index(Model model) {
				model.addAttribute("people", personDAO.index());
				return "people/index.html";
		}


		/**
		 * Show personal card by given ID
		 *
		 * @param id {@code int} - id user in DB
		 * @param model {@code Model} -Java-5-specific interface that defines a holder for model attributes.
		 *                             Primarily designed for adding attributes to the model. Allows for accessing the overall model as a java.util.Map.
		 * @return Response   {@code String } selected people page
		 * @author NVN
		 * @since 11.03.2022
		 */
		@GetMapping("/{id}")
		public String show(@PathVariable("id") int id, Model model) {
				model.addAttribute("person", personDAO.show(id));
				return "people/show.html";
		}


		/**
		 * Show page for creating new people
		 *
		 * @param person {@code int} - Person object
		 * @return Response   {@code String } new people page
		 * @author NVN
		 * @since 11.03.2022
		 */
		@GetMapping("/new")
		public String newPerson(@ModelAttribute("person") Person person) {
				return "people/new";
		}

		/**
		 * Method for create new Person and add to DB postgreSQL
		 *
		 * @param person {@code Person} - Person object
		 * @return Response   {@code String } if operations has no errors return page with current users in DB
		 * 																						else
		 * 																							return page for creating new people with errors
		 * @author NVN
		 * @since 11.03.2022
		 */
		@PostMapping("/new")
		public String create(@ModelAttribute("person") @Valid Person person,
				BindingResult bindingResult) {
				if (bindingResult.hasErrors())
						return "people/new.html";

				personDAO.save(person);
				return "redirect:/people";
		}


		/**
		 * Show page for editing selected person
		 *
		 * @param model {@code int} - Person object
		 * @param id {@code int} - @PathVariable for get id  Person from request
		 * @return Response   {@code String } editing page
		 * @author NVN
		 * @since 11.03.2022
		 */
		@GetMapping("/{id}/edit")
		public String edit(Model model, @PathVariable("id") int id) {
				model.addAttribute("person", personDAO.show(id));
				return "people/edit.html";
		}

		/**
		 * Update method in DB
		 *
		 * @PATCH-is used when you want to apply a partial update to the resource and
		 * @PatchMapping annotation for mapping HTTP PATCH requests onto specific handler methods.
		 *
		 * @param person {@code Person} - Person object
		 * @param bindingResult {@code BindingResult} - object for handling errors
		 * @param id {@code int} - @PathVariable for get id  Person from request
		 * @return Response   {@code String } main page
		 * @author NVN
		 * @since 11.03.2022
		 */
		@PatchMapping("/{id}")
		public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
				@PathVariable("id") int id) {
				if (bindingResult.hasErrors())
						return "people/edit.html";

				personDAO.update(id, person);
				return "redirect:/people";

		}


		/**
		 * DELETE method in DB
		 *
		 * @param id {@code int} - @PathVariable for get id  Person from request
		 * @return Response   {@code String } main page
		 * @author NVN
		 * @since 11.03.2022
		 */
		@DeleteMapping("/{id}")
		public String delete(@PathVariable("id") int id) {
				personDAO.delete(id);
				return "redirect:/people";
		}

		/**
		 * Method for parse Excel file and create persons in DB
		 *
		 * @param file {@code MultipartFile} - file from
		 * @param modelMap {@code ModelMap} -Java-5-specific interface that defines a holder for model attributes.
		 * 		                        Primarily designed for adding attributes to the model. Allows for accessing the overall model as a java.util.Map.
		 * @return Response   {@code String } page with info about file
		 * @author NVN
		 * @since 11.03.2022
		 */
		@PostMapping("/dropZoneUpload")
		public String upload(@ModelAttribute("file") final MultipartFile file,final ModelMap modelMap) throws Exception
		{
				modelMap.addAttribute("file", file);
				String fileType = file.getContentType();
				if (FormDataWithFile.EXCEL_TYPE.equals(fileType))
						FileParseUtil.parseFile(file,personDAO);
						return "people/fileUploadView.jsp";
		}

		/**
		 * Method for show page with drag and drop
		 *
		 * @param file {@code MultipartFile} - file from
		 * @return Response   {@code String } page with drag and drop form for upload Excel file
		 * @author NVN
		 * @since 11.03.2022
		 */
		@GetMapping( "/dropZoneUpload")
		public String dragAndDropPage(@ModelAttribute("file") MultipartFile file) {
				return "people/dragAndDrop.html";
		}


}
