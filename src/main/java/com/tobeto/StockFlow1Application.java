package com.tobeto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StockFlow1Application {

	public static void main(String[] args) {
		SpringApplication.run(StockFlow1Application.class, args);
	}

//	@Autowired
//	private RoleRepository roleRepository;
//
//	@Autowired
//	private UserRepository userRepository;
//
//	@Override
//	@Transactional
//	public void run(String... args) throws Exception {
//		Role role1 = new Role();
//		role1.setName("admin");
//		role1.setId(1);
//		roleRepository.save(role1);
//
//		Role role2 = new Role();
//		role2.setName("depo_sorumlusu");
//		role2.setId(2);
//		roleRepository.save(role2);
//
//		Role role3 = new Role();
//		role3.setName("rapor_kullanicisi");
//		role3.setId(3);
//		roleRepository.save(role3);
//
//		User adminUser = new User();
//		adminUser.setEmail("admin");
//		adminUser.setName("admin");
//		adminUser.setLastName("admin");
//		adminUser.setPassword("$2a$04$.o.xwULSE1butKafzocaGOUCAMsjH6XLYc/48Sj0CLILYat2Hun5a");
//		adminUser.setRole(role1);
//		userRepository.save(adminUser);
//	}

}
