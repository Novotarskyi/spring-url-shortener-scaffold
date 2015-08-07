

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;

@ComponentScan
@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);


        String[] profiles = ctx.getEnvironment().getActiveProfiles();
        Arrays.sort(profiles);


        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
    }
}

