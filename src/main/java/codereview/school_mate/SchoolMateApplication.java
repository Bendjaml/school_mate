package codereview.school_mate;

import codereview.school_mate.enums.RoleEnum;
import codereview.school_mate.model.*;
import codereview.school_mate.repository.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication
public class SchoolMateApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(SchoolMateApplication.class, args);
		fillDb(context);
	}

	public static void fillDb(ConfigurableApplicationContext context) {
		RoleRepository roleRepository = context.getBean(RoleRepository.class);

		Role roleAdmin = new Role();
		roleAdmin.setName(RoleEnum.ADMIN.getName());
		Role roleStudent = new Role();
		roleStudent.setName(RoleEnum.STUDENT.getName());
		Role roleTeacher = new Role();
		roleTeacher.setName(RoleEnum.TEACHER.getName());
		Role roleParent = new Role();
		roleParent.setName(RoleEnum.PARENT.getName());

		roleRepository.saveAll(List.of(roleAdmin, roleTeacher, roleStudent, roleParent));

		ParentRepository parentRepository = context.getBean(ParentRepository.class);

//		Parent parent = new Parent();
//		parent.setName("parentName");
//		parent.setSurname("parentSurname");
//		parent.setPatronymic("parentPantron");
//		parent.setContacts("parentContacts");

//		Parent savedParent = parentRepository.save(parent);

		StudentRepository studentRepository = context.getBean(StudentRepository.class);
		SchoolClassRepository schoolClassRepository = context.getBean(SchoolClassRepository.class);

		SchoolClass schoolClass = new SchoolClass();
		schoolClass.setName("1a");
		SchoolClass schoolClass1 = schoolClassRepository.save(schoolClass);

//		Student student = new Student();
//		student.setName("studentName");
//		student.setSurname("studentSurname");
//		student.setPatronymic("studentPatron");
//		student.setSchoolClass(schoolClass1);
//		student.setParent(parent);

//		Student savedStudent = studentRepository.save(student);

		UserRepository userRepository = context.getBean(UserRepository.class);

		User userStudent = new User();
		userStudent.setUsername("student");
		userStudent.setPassword("$2a$10$bVLL.9qlF2BxXMnlITmxgerRiiOza412PYXp/qCIaCHwaWFhrtWpi"); //student
		userStudent.setRole(roleRepository.findByName(RoleEnum.STUDENT.getName()).get());
//		userStudent.setStudent(student);

		User userParent = new User();
		userParent.setUsername("parent");
		userParent.setPassword("$2a$10$05oIMH7jtVXtYV4T8/EsdefxNx/JDHxpPLEjfCuHmRKlt2VcvASMi"); //parent
		userParent.setRole(roleRepository.findByName(RoleEnum.PARENT.getName()).get());
//		userParent.setParent(parent);

		userRepository.save(userParent);
		userRepository.save(userStudent);

		Parent parent = new Parent();
		parent.setName("parentName");
		parent.setSurname("parentSurname");
		parent.setPatronymic("parentPantron");
		parent.setContacts("parentContacts");
		parent.setUser(userParent);

		Parent savedParent = parentRepository.save(parent);

		Student student = new Student();
		student.setName("studentName");
		student.setSurname("studentSurname");
		student.setPatronymic("studentPatron");
		student.setSchoolClass(schoolClass1);
		student.setParent(parent);
		student.setUser(userStudent);

		Student savedStudent = studentRepository.save(student);
	}

}
