package telran;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
	    
	    ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> listenForExit(ctx));
	    console.run();
	}
	private static void listenForExit(ConfigurableApplicationContext ctx) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("> ");
                String input = scanner.nextLine().trim();
                if ("exit".equalsIgnoreCase(input)) {
                    System.out.println("Shutting down...");
                    ctx.close(); 
                    System.exit(0); 
                }
            }
        }
    }
}

