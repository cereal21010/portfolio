package com.example;

import com.example.layout.Layout;
import com.samskivert.mustache.Mustache;
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
