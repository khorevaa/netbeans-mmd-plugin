/*
 * Copyright 2015 Igor Maznitsa.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.igormaznitsa.mindmap.swing.panel.utils;

import com.igormaznitsa.mindmap.model.Topic;
import com.igormaznitsa.mindmap.swing.panel.ui.AbstractCollapsableElement;
import java.awt.Color;
import java.io.File;
import java.net.URI;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {

  ;
        
  private static final Logger logger = LoggerFactory.getLogger(Utils.class);
        
  public static Topic[] getLeftToRightOrderedChildrens(final Topic topic) {
    final List<Topic> result = new ArrayList<Topic>();
    if (topic.getTopicLevel() == 0) {
      for (final Topic t : topic.getChildren()) {
        if (AbstractCollapsableElement.isLeftSidedTopic(t)) {
          result.add(t);
        }
      }
      for (final Topic t : topic.getChildren()) {
        if (!AbstractCollapsableElement.isLeftSidedTopic(t)) {
          result.add(t);
        }
      }
    }
    else {
      result.addAll(topic.getChildren());
    }
    return result.toArray(new Topic[result.size()]);
  }

  public static String color2html(final Color color) {
    final StringBuilder buffer = new StringBuilder();

    buffer.append('#');
    for (final int c : new int[]{color.getRed(), color.getGreen(), color.getBlue()}) {
      final String str = Integer.toHexString(c & 0xFF).toUpperCase(Locale.ENGLISH);
      if (str.length() < 2) {
        buffer.append('0');
      }
      buffer.append(str);
    }

    return buffer.toString();
  }

  public static File toFile(final URI uri) throws IllegalArgumentException {
    try {
      return Paths.get(uri).toFile();
    }
    catch (Exception ex) {
      logger.warn(String.format("Can't convert %s to a file",uri), ex);
    }
    final String host = uri.getHost();
    if (host != null && !host.isEmpty() && "file".equals(uri.getScheme())) {
      return new File("\\\\" + host + uri.getPath().replace('/', '\\'));
    }
    return new File(uri);
  }
  
  
}