package janari.burgers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@SpringBootApplication
public class BurgerJointsApplication {

	@GetMapping("/")
	String index() {
		return "index";
	}

	public static void main(String[] args) {
		SpringApplication.run(BurgerJointsApplication.class, args);
	}
}
