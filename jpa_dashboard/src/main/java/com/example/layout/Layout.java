package com.example.layout;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;

import java.io.IOException;
import java.io.Writer;

public class Layout implements Mustache.Lambda {
	private Mustache.Compiler compiler;

	String body;

	public Layout(Mustache.Compiler compiler) {
		this.compiler = compiler;
	}

	@Override
	public void execute(Template.Fragment frag, Writer out) throws IOException {
		body = frag.execute();
		compiler.compile("{{>layout}}").execute(frag.context(), out);
	}
}
