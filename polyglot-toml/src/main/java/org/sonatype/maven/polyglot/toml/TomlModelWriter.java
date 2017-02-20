/**
 * Copyright (c) 2012 to original author or authors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.sonatype.maven.polyglot.toml;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.ModelWriter;
import org.codehaus.plexus.component.annotations.Component;
import org.sonatype.maven.polyglot.io.ModelWriterSupport;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

@Component(role = ModelWriter.class, hint = "toml")
public class TomlModelWriter extends ModelWriterSupport {
  public void write(Writer output, Map<String, Object> o, Model model) throws IOException {
//    DumperOptions dumperOptions = new DumperOptions();
//    dumperOptions.setIndent(2);
//    dumperOptions.setWidth(80);
//    Serializer serializer = new Serializer(new Emitter(output, dumperOptions), new ModelResolver(), dumperOptions, Tag.MAP);
//    Representer representer = new ModelRepresenter();
//    try {
//      serializer.open();
//      Node node = representer.represent(model);
//      serializer.serialize(node);
//      serializer.close();
//    } catch (IOException e) {
//      throw new YAMLException(e);
//    }
  }
}
