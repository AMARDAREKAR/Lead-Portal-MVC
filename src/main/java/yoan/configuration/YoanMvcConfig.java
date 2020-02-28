package yoan.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

@EnableWebMvc
@Configuration	
@ComponentScan("yoan.controller")
public class YoanMvcConfig implements WebMvcConfigurer {
	
    @Bean
    public TilesViewResolver viewResolver()
    {
    	TilesViewResolver viewResolver = new TilesViewResolver();
    	return viewResolver;
    }
    
    @Bean
    public TilesConfigurer tilesConfigurer()
    {
    	TilesConfigurer tilesConfigurer = new TilesConfigurer();
    	tilesConfigurer.setDefinitions("/WEB-INF/tiles.xml");
    	return tilesConfigurer;
    }
 
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
            .addResourceHandler("/resources/**").addResourceLocations("classpath:/statics/");
    }
}