package com.jpa.dashboard;

import com.samskivert.mustache.Mustache;
import com.jpa.dashboard.layout.Layout;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.ServletContext;
import java.util.Map;

@AllArgsConstructor
@ControllerAdvice
public class LayoutAdvice {
	private final Mustache.Compiler compiler;
	private ServletContext servletContext;

	@ModelAttribute("layout")
	public Mustache.Lambda layout(Map<String, Object> model) {
		model.put("context", servletContext.getContextPath());
		return new Layout(compiler);
	}
}
