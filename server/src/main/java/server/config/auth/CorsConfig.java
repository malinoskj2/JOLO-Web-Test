package server.config.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class CorsConfig extends WebMvcConfigurerAdapter {

    private boolean corsEnabled;

    public CorsConfig(@Value("${cors}") String cors) {
        this.corsEnabled = cors.toLowerCase().equals("yes");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        if (this.corsEnabled) {
            registry.addMapping("/**");
        }
    }
}
