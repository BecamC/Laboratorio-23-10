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
import org.tinyuml.draw.DiagramElement;
import org.tinyuml.ui.diagram.DiagramEditor;
import org.tinyuml.ui.diagram.EditorMouseEvent;
import org.tinyuml.ui.diagram.EditorStateListener;
import org.tinyuml.ui.diagram.SelectionListener;

/**
 * A status bar label that shows how many elements are selected in an
 * editor. It observes the editor's selection and also the removal of
 * elements, so the count goes back to zero after a deletion.
 * @version 1.0
 */
public class SelectionCountLabel extends JLabel
implements SelectionListener, EditorStateListener {

  private DiagramEditor editor;

  /**
   * Sets the editor whose selection is counted.
   * @param anEditor the editor
   */
  public void setEditor(DiagramEditor anEditor) {
    editor = anEditor;
    update();
  }

  /**
   * Shows the number of selected elements.
   */
  private void update() {
    List<DiagramElement> selected = editor.getSelectedElements();
    int count = selected.size();
    // a click on an empty area selects the diagram itself, that is no
    // selection for the user
    if (count == 1 && selected.get(0) == editor.getDiagram()) {
      count = 0;
    }
    setText("Seleccionados: " + count);
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
