package telran;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import telran.utils.JPQLQueryConsole;



@SpringBootApplication
public class DailyFarmApplication {

	public static void main(String[] args) {
		//SpringApplication.run(DailyFarmApplication.class, args);
	    ConfigurableApplicationContext ctx = SpringApplication.run(DailyFarmApplication.class, args);
	    JPQLQueryConsole console = ctx.getBean(JPQLQueryConsole.class);
	    console.run();
	}

}
