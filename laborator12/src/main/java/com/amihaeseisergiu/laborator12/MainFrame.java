package com.amihaeseisergiu.laborator12;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class MainFrame extends JFrame {
    
    private ControlPanel controlPanel;
    private DesignPanel designPanel;
    
    private DefaultTableModel tableModel = new DefaultTableModel(new String[][]{}, new String[]{"Key", "Value"});  
    private JTable table = new JTable(tableModel);   
    
    public MainFrame()
    {
        super("Swing Designer");
        this.setLayout(new BorderLayout());
        rootPane.setPreferredSize(new Dimension(1000, 800));
        init();
        addComponents();
        this.setVisible(true);
    }
    
    private void init()
    {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.designPanel = new DesignPanel(this);
        this.controlPanel = new ControlPanel(this);
        
        pack();
    }

    private void addComponents()
    {
        this.add(getControlPanel(), BorderLayout.NORTH);
        this.add(getDesignPanel(), BorderLayout.CENTER);
        this.add(getTable(), BorderLayout.WEST);
    }

    /**
     * @return the controlPanel
     */
    public ControlPanel getControlPanel() {
        return controlPanel;
    }

    /**
     * @param controlPanel the controlPanel to set
     */
    public void setControlPanel(ControlPanel controlPanel) {
        this.controlPanel = controlPanel;
    }

    /**
     * @return the designPanel
     */
    public DesignPanel getDesignPanel() {
        return designPanel;
    }

    /**
     * @param designPanel the designPanel to set
     */
    public void setDesignPanel(DesignPanel designPanel) {
        this.designPanel = designPanel;
        designPanel.init(this);
        getContentPane().removeAll();
        getContentPane().add(controlPanel, BorderLayout.NORTH);
        getContentPane().add(table, BorderLayout.WEST);
        getContentPane().add(designPanel, BorderLayout.CENTER);
        
        this.setVisible(true);
    }

    /**
     * @return the tableModel
     */
    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    /**
     * @param tableModel the tableModel to set
     */
    public void setTableModel(DefaultTableModel tableModel) {
        this.tableModel = tableModel;
    }

    /**
     * @return the table
     */
    public JTable getTable() {
        return table;
    }

    /**
     * @param table the table to set
     */
    public void setTable(JTable table) {
        this.table = table;
    }
    
}
