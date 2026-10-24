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

import java.util.List;
import javax.swing.JLabel;
import org.tinyuml.draw.Connection;
import org.tinyuml.draw.DiagramElement;
import org.tinyuml.ui.diagram.DiagramEditor;
import org.tinyuml.ui.diagram.EditorMouseEvent;
import org.tinyuml.ui.diagram.EditorStateListener;
import org.tinyuml.ui.diagram.SelectionListener;
import org.tinyuml.umldraw.shared.NoteElement;
import org.tinyuml.umldraw.structure.ClassElement;
import org.tinyuml.umldraw.structure.ComponentElement;
import org.tinyuml.umldraw.structure.PackageElement;

/**
 * A status bar label that shows the category and the name of the element
 * selected in an editor. It observes the editor's selection and also the
 * removal of elements, so a deleted element is no longer shown.
 * @version 1.0
 */
public class SelectedElementLabel extends JLabel
implements SelectionListener, EditorStateListener {

  private DiagramEditor editor;

  /**
   * Sets the editor whose selection is shown.
   * @param anEditor the editor
   */
  public void setEditor(DiagramEditor anEditor) {
    editor = anEditor;
    update();
  }

  /**
   * Shows the information of the current selection.
   */
  private void update() {
    List<DiagramElement> selected = editor.getSelectedElements();
    if (selected.size() > 1) {
      setText("Selecci\u00f3n m\u00faltiple");
    } else if (selected.isEmpty() || selected.get(0) == editor.getDiagram()) {
      setText("Sin selecci\u00f3n");
    } else {
      setText("Selecci\u00f3n: " + describe(selected.get(0)));
    }
  }

  /**
   * Returns the category and the name of the specified element.
   * @param element the element
   * @return the description
   */
  private String describe(DiagramElement element) {
    if (element instanceof PackageElement) {
      return "Package - " + ((PackageElement) element).getLabelText();
    } else if (element instanceof ClassElement) {
      return "Class - " + ((ClassElement) element).getLabelText();
    } else if (element instanceof ComponentElement) {
      return "Component - " + ((ComponentElement) element).getLabelText();
    } else if (element instanceof NoteElement) {
      // notes can have several lines, show them in one line
      return "Note - "
        + ((NoteElement) element).getLabelText().replace('\n', ' ');
    } else if (element instanceof Connection) {
      return "Relaci\u00f3n";
    }
    return "Sin selecci\u00f3n";
  }

  // ************************************************************************
  // **** SelectionListener
  // *****************************************

  /**
   * {@inheritDoc}
   */
  public void selectionStateChanged() {
    update();
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
  public void stateChanged(DiagramEditor anEditor) { }

  /**
   * {@inheritDoc}
   */
  public void elementAdded(DiagramEditor anEditor) { }

  /**
   * {@inheritDoc}
   */
  public void elementRemoved(DiagramEditor anEditor) {
    update();
  }
}
