package lightsearch.server.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@ImportResource("ls_server_context.xml")

@ComponentScan("lightsearch.server.*")
@Configuration
public class LightSearchServerConfiguration {
}
