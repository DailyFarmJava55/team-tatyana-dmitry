package telran.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

/**
 * This class represents simple console of multi-line SQL and JPQL queries.
 * 
 * Integration with your application: 1. To instantiate bean, do one of
 * following solutions:
 * 
 * Solution a) Move this class to be under root package of your Spring Boot
 * application
 * 
 * Solution b) Add this package to your Spring Boot application component scan
 * path as: @ComponentScan({"telran.utils"})
 * 
 * Solution c) Add bean creating method to your application configuration as:
 * 
 * @Bean JPQLQueryConsole createConsole() {return new JPQLQueryConsole();}
 * 
 *       2. To run the console in Spring Boot application add the following code
 *       in your main(): ... ConfigurableApplicationContext ctx =
 *       SpringApplication.run(...); JPQLQueryConsole console =
 *       ctx.getBean(JPQLQueryConsole.class); console.run();
 * 
 *       3. To have the readable output ensure that your entities have
 *       toString() implementation
 * 
 * @author Daniel Zinchin (idea by Yury Granovsky) Version 2.0
 */
@Service
@Transactional
public class JPQLQueryConsole {
	@PersistenceContext
	EntityManager em;

	public void run() {
		Scanner scanner = new Scanner(System.in);
		var builder = new StringBuilder();
		var isNative = false;
		while (true) {
			try {
				if (builder.length() == 0) {
					System.out.println(
							"\n" + (isNative ? "SQL " : "JPQL") + "===> Enter multi-line query ending with ';'."
									+ "Type 'mode' to switch the mode, 'exit' to stop the work.");
				}
				String line = scanner.nextLine();
				if (line.isEmpty()) {
					continue;
				}
				if (line.equalsIgnoreCase("exit")) {
					break;
				}
				if (line.equalsIgnoreCase("mode")) {
					builder.setLength(0);
					isNative = !isNative;
					continue;
				}
				builder.append(' ').append(line);
				if (line.endsWith(";")) {
					builder.setLength(builder.length() - 1); // drop ';'
					String query = builder.toString();
					builder.setLength(0);
					runAnyQuery(query, isNative).forEach(System.out::println);
				}
			} catch (Exception e) {
				System.out.println("Error: " + e.getMessage());
			}

		}
		scanner.close();
	}

	@SuppressWarnings("unchecked")
	public Iterable<String> runAnyQuery(String queryText, boolean isNative) {
		Query query = isNative ? em.createNativeQuery(queryText) : em.createQuery(queryText);

		if (queryText.toUpperCase().matches("\\s*SELECT\\s*.*")) { // it is read-only query
			List<?> listRes = query.getResultList();
			if (listRes.isEmpty()) {
				return Collections.EMPTY_LIST;
			}
			return listRes.get(0).getClass().isArray() ? getResultProjection((List<Object[]>) listRes)
					: getResult((List<Object>) listRes);
		} else {
			int count = query.executeUpdate();
			return Collections.singletonList("Updated: " + count);
		}
	}

	private Iterable<String> getResult(List<Object> listRes) {
		return listRes.stream().map(Object::toString).collect(Collectors.toList());
	}

	private Iterable<String> getResultProjection(List<Object[]> listRes) {

		return listRes.stream().map(Arrays::deepToString).collect(Collectors.toList());
	}
}
