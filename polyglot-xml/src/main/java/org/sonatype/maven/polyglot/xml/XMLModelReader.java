/**
 * Copyright (c) 2012 to original author or authors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.sonatype.maven.polyglot.xml;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.ModelParseException;
import org.apache.maven.model.io.ModelReader;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.sonatype.maven.polyglot.io.ModelReaderSupport;
import org.sonatype.maven.polyglot.xml.xpp3.MavenXpp3Reader;

/**
 * XML model reader.
 *
 */
@Component(role = ModelReader.class, hint = "xml41")
public class XMLModelReader extends ModelReaderSupport {
	
	MavenXpp3Reader reader;

	public XMLModelReader() {
		reader = new MavenXpp3Reader();
	}

	public Model read(Reader input, Map<String, ?> options) throws IOException, ModelParseException {
		if (input == null) {
			throw new IllegalArgumentException("XML Reader is null.");
		}
		
		Model model = null;

		try {
			model = reader.read(input);
		} catch (XmlPullParserException e) {
			throw new ModelParseException(e.getMessage(), -1, -1, e);
		}
		
		return model;
	}
}
