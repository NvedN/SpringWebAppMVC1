package ru.nvn.spring.files;

import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;
import ru.nvn.spring.dao.PersonDAO;
import ru.nvn.spring.models.Person;

import java.io.InputStream;
import java.util.Iterator;

public class FileParseUtil
{

		public static void parseFile(MultipartFile file, PersonDAO personDAO) throws Exception
		{
				DataFormatter df = new DataFormatter();
				InputStream inp = file.getInputStream();
				Workbook workbook = WorkbookFactory.create(inp);
				Sheet sheet = workbook.getSheetAt(0);
				Iterator<Row> rowIterator = sheet.iterator();
				rowIterator.next(); // skip header
				while (rowIterator.hasNext())
				{
						Row row = rowIterator.next();
						Cell idCell = row.getCell(0);
						Cell nameCell = row.getCell(1);
						Cell ageCell = row.getCell(2);
						Cell emailCell = row.getCell(3);
						// check cells values
						{
								int id = (int) idCell.getNumericCellValue();
								String name = df.formatCellValue(nameCell);
								int age = (int) ageCell.getNumericCellValue();
								String email = df.formatCellValue(emailCell);
								Person person = new Person(id,name,age,email);
								person.setId(id);
								personDAO.save(person);
								System.out.println("------------id = " + id);
								System.out.println("------------name = " + name);
								System.out.println("------------age = " + age);
								System.out.println("------------email = " + email);
						}
				}
		}
}
