package price_hook.price_hook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PriceHookApplication {

	public static void main(String[] args) {
		SpringApplication.run(PriceHookApplication.class, args);
	}

}
