/**
 * This file is part of TinyUML.
 *
 * TinyUML is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * TinyUML is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with TinyUML; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package org.tinyuml.ui;

import javax.swing.JLabel;
import org.tinyuml.draw.AbstractCompositeNode;
import org.tinyuml.draw.DiagramElement;
import org.tinyuml.ui.diagram.DiagramEditor;
import org.tinyuml.ui.diagram.EditorMouseEvent;
import org.tinyuml.ui.diagram.EditorStateListener;
import org.tinyuml.umldraw.shared.NoteElement;
import org.tinyuml.umldraw.structure.ClassElement;
import org.tinyuml.umldraw.structure.ComponentElement;
import org.tinyuml.umldraw.structure.PackageElement;

/**
 * A status bar label that shows the number of elements per category of the
 * diagram in an editor. It observes the editor and updates itself whenever
 * an element is added or removed. Connections are not counted.
 * @version 1.0
 */
public class ElementCountLabel extends JLabel implements EditorStateListener {

  private int packages, classes, components, notes;

  /**
   * Recounts the elements of the editor's diagram and shows the result.
   * @param editor the editor
   */
  public void update(DiagramEditor editor) {
    packages = classes = components = notes = 0;
    countElements(editor.getDiagram());
    int total = packages + classes + components + notes;
    setText(String.format(
      "Total Items: %02d | Package: %02d | Class: %02d | Component: %02d"
      + " | Note: %02d", total, packages, classes, components, notes));
  }

  /**
   * Recursively counts the elements contained in the specified node, so
   * elements nested in packages are included.
   * @param node the node
   */
  private void countElements(AbstractCompositeNode node) {
    for (DiagramElement child : node.getChildren()) {
      if (child instanceof PackageElement) {
        packages++;
      } else if (child instanceof ClassElement) {
        classes++;
      } else if (child instanceof ComponentElement) {
        components++;
      } else if (child instanceof NoteElement) {
        notes++;
      }
      if (child instanceof AbstractCompositeNode) {
        countElements((AbstractCompositeNode) child);
      }
    }
  }

  // ************************************************************************
  // **** EditorStateListener
  // *****************************************

  /**
   * {@inheritDoc}
   */
  public void mouseMoved(EditorMouseEvent event) { }

  /**
   * {@inheritDoc}
   */
  public void stateChanged(DiagramEditor editor) { }

  /**
   * {@inheritDoc}
   */
  public void elementAdded(DiagramEditor editor) {
    update(editor);
  }

  /**
   * {@inheritDoc}
   */
  public void elementRemoved(DiagramEditor editor) {
    update(editor);
  }
}
