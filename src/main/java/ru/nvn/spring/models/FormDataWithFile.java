package ru.nvn.spring.models;

import org.springframework.web.multipart.MultipartFile;

public class FormDataWithFile {


		public static final String TXT_TYPE = "text/plain";
		public static final String EXCEL_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

		private String name;
		private String email;
		private MultipartFile file;

		public String getName() {
				return name;
		}

		public void setName(String name) {
				this.name = name;
		}

		public String getEmail() {
				return email;
		}

		public void setEmail(String email) {
				this.email = email;
		}

		public MultipartFile getFile() {
				return file;
		}

		public void setFile(MultipartFile file) {
				this.file = file;
		}

}