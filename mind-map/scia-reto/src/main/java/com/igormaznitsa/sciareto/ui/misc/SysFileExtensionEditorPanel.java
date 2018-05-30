/*
 * Copyright (C) 2018 Igor Maznitsa.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package com.igormaznitsa.sciareto.ui.misc;

import com.igormaznitsa.meta.annotation.MustNotContainNull;
import com.igormaznitsa.sciareto.ui.MainFrame;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class SysFileExtensionEditorPanel extends javax.swing.JPanel {

  private final List<String> strings = new ArrayList<>();
  
  public SysFileExtensionEditorPanel(@Nonnull final String initValue) {
    initComponents();
    this.tableExtensions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    makeModelAndSetToTable(initValue);
    this.tableExtensions.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(@Nonnull final ListSelectionEvent e) {
        updateButtons();
      }
    });
    updateButtons();
  }

  private void updateButtons() {
    this.buttonDeleteLine.setEnabled(this.tableExtensions.getSelectedRow() >= 0);
    this.buttonEditLine.setEnabled(this.tableExtensions.getSelectedRow() >= 0);
  }
  
  private void makeModelAndSetToTable(@Nonnull final String text){
    final String[] parsed = parseExtensionsAndSortForFirstChar(text);

    final String[][] data = new String[parsed.length][1];
    for (int i = 0; i < parsed.length; i++) {
      data[i] = new String[]{parsed[i]};
    }

    this.tableExtensions.setModel(new DefaultTableModel(data, new Object[]{"File extensions"}));
    this.tableExtensions.revalidate();
  }
  
  @Nullable
  public String getValuerNullIfDefault() {
    final String [] lines = new String[this.tableExtensions.getModel().getRowCount()];
    for(int i=0; i<this.tableExtensions.getModel().getRowCount(); i++){
      lines [i] = this.tableExtensions.getModel().getValueAt(i, 0).toString();
    }
    final String result = prepareStringFromLines(lines);
    return result.equals(prepareStringFromLines(parseExtensionsAndSortForFirstChar(MainFrame.DEFAULT_OPEN_IN_SYSTEM_EXTENSIONS))) ? null : result;
  }

  private static String prepareStringFromLines(@Nonnull @MustNotContainNull final String [] lines) {
    final StringBuilder builder = new StringBuilder();
    for (final String s : lines) {
      for (final String ext : s.split("\\,")) {
        final String trimmed = ext.toLowerCase(Locale.ENGLISH).trim();
        if (trimmed.isEmpty()) {
          continue;
        }
        if (builder.length() > 0) {
          builder.append(',');
        }
        builder.append(trimmed);
      }
    }
    return builder.toString();
  }
  
  @Nonnull
  @MustNotContainNull
  private static String [] parseExtensionsAndSortForFirstChar(@Nonnull final String text) {
   final String [] parsed = text.split("\\,");
   Arrays.sort(parsed);
   final List<String> result = new ArrayList<>();
  
   final StringBuilder buffer = new StringBuilder();
   Character curChar = null;
   for(final String s : parsed) {
     final String trimmed = s.trim();
     if (trimmed.isEmpty()) continue;
     if (curChar == null || curChar != trimmed.charAt(0)) {
       if (buffer.length()>0) result.add(buffer.toString());
       curChar = trimmed.charAt(0);
       buffer.setLength(0);
       buffer.append(s);
     } else {
       if (buffer.length()>0) buffer.append(',');
       buffer.append(s);
     }
   }
   if (buffer.length()>0) {
     result.add(buffer.toString());
   }
   return result.toArray(new String[result.size()]);
  }
  
  /**
   * This method is called from within the constructor to initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is always
   * regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {
    java.awt.GridBagConstraints gridBagConstraints;

    jPanel1 = new javax.swing.JPanel();
    buttonAddLine = new javax.swing.JButton();
    buttonEditLine = new javax.swing.JButton();
    buttonDeleteLine = new javax.swing.JButton();
    filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
    buttonReset = new javax.swing.JButton();
    filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 16), new java.awt.Dimension(0, 16), new java.awt.Dimension(32767, 16));
    jScrollPane1 = new javax.swing.JScrollPane();
    tableExtensions = new javax.swing.JTable();

    setLayout(new java.awt.BorderLayout());

    jPanel1.setLayout(new java.awt.GridBagLayout());

    buttonAddLine.setText("Add");
    buttonAddLine.setToolTipText("Add new empty line into table");
    buttonAddLine.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        buttonAddLineActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    jPanel1.add(buttonAddLine, gridBagConstraints);

    buttonEditLine.setText("Edit");
    buttonEditLine.setToolTipText("Start edit focused line");
    buttonEditLine.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        buttonEditLineActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    jPanel1.add(buttonEditLine, gridBagConstraints);

    buttonDeleteLine.setText("Delete");
    buttonDeleteLine.setToolTipText("Delete focused line");
    buttonDeleteLine.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        buttonDeleteLineActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    jPanel1.add(buttonDeleteLine, gridBagConstraints);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 5;
    gridBagConstraints.weighty = 1000.0;
    jPanel1.add(filler1, gridBagConstraints);

    buttonReset.setText("Reset");
    buttonReset.setToolTipText("Reset format list to default state");
    buttonReset.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        buttonResetActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 4;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    jPanel1.add(buttonReset, gridBagConstraints);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 3;
    jPanel1.add(filler2, gridBagConstraints);

    add(jPanel1, java.awt.BorderLayout.LINE_END);

    tableExtensions.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][] {
        {null, null, null, null},
        {null, null, null, null},
        {null, null, null, null},
        {null, null, null, null}
      },
      new String [] {
        "Title 1", "Title 2", "Title 3", "Title 4"
      }
    ));
    jScrollPane1.setViewportView(tableExtensions);

    add(jScrollPane1, java.awt.BorderLayout.CENTER);
  }// </editor-fold>//GEN-END:initComponents

  private void buttonResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonResetActionPerformed
    makeModelAndSetToTable(MainFrame.DEFAULT_OPEN_IN_SYSTEM_EXTENSIONS);
  }//GEN-LAST:event_buttonResetActionPerformed

  private void buttonAddLineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAddLineActionPerformed
    ((DefaultTableModel)this.tableExtensions.getModel()).addRow(new String[]{""});
  }//GEN-LAST:event_buttonAddLineActionPerformed

  private void buttonEditLineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonEditLineActionPerformed
    this.tableExtensions.editCellAt(this.tableExtensions.getSelectedRow(), 0);
  }//GEN-LAST:event_buttonEditLineActionPerformed

  private void buttonDeleteLineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDeleteLineActionPerformed
    ((DefaultTableModel)this.tableExtensions.getModel()).removeRow(this.tableExtensions.getSelectedRow());
  }//GEN-LAST:event_buttonDeleteLineActionPerformed


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton buttonAddLine;
  private javax.swing.JButton buttonDeleteLine;
  private javax.swing.JButton buttonEditLine;
  private javax.swing.JButton buttonReset;
  private javax.swing.Box.Filler filler1;
  private javax.swing.Box.Filler filler2;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JTable tableExtensions;
  // End of variables declaration//GEN-END:variables

}
