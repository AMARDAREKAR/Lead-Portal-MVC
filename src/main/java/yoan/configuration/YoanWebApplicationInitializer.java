// this configuration registers the dispatcher servlet in the web.xml file

package yoan.configuration;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class YoanWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {
				YoanHibernateConfig.class,YoanSecurityConfig.class
	        };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {
	            YoanMvcConfig.class
	        };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {
	            "/"
	        };
	}

}