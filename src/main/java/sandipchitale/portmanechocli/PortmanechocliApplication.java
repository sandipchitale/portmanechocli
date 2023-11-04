package sandipchitale.portmanechocli;

import org.jline.utils.AttributedString;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.CommandScan;
import org.springframework.shell.command.annotation.EnableCommand;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@SpringBootApplication
public class PortmanechocliApplication {

	@Configuration
	static class PostmanEchoPromptConfiguration implements PromptProvider {
		@Override
		public final AttributedString getPrompt() {
			return new AttributedString("postman-echo > ");
		}
	}

	@Command()
//	@ShellComponent
	@EnableCommand(PostmanEchoCommands.class)
	static class PostmanEchoCommands {
		private final RestClient restClient;

		PostmanEchoCommands(RestClient.Builder restClientBuilder) {
			this.restClient = restClientBuilder.baseUrl("https://postman-echo.com").build();
		}

		@Command(command = "get", description = "Access Postman Echo GET")
//		@ShellMethod(key = "get", value = "Access Postman Echo /get")
		public String get() {
			return restClient
					.get()
					.uri(UriComponentsBuilder.fromUriString("/get").queryParam("method", "get").build().toUriString())
					.retrieve()
					.body(String.class);
		}

		@Command(command = "post", description = "Access Postman Echo POST")
//		@ShellMethod(key = "post", value = "Access Postman Echo /post")
		public String post() {
			return restClient
					.post()
					.uri("/post")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
					.body("method=post")
					.retrieve()
					.body(String.class);
		}

		@Command(command = "put", description = "Access Postman Echo PUT")
//		@ShellMethod(key = "put", value = "Access Postman Echo /put")
		public String put() {
			return restClient
					.put()
					.uri("/put")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.body("method=put")
					.retrieve()
					.body(String.class);
		}

		@Command(command = "delete", description = "Access Postman Echo DELETE")
//		@ShellMethod(key = "delete", value = "Access Postman Echo /delete")
		public String delete() {
			return restClient
					.delete()
					.uri("/delete")
					.retrieve()
					.body(String.class);
		}

	}

	public static void main(String[] args) {
		SpringApplication.run(PortmanechocliApplication.class, args);
	}

}
