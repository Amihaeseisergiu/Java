package com.amihaeseisergiu.laborator12;

import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class DesignPanel extends JPanel implements Serializable {
    
    public static final int W = 800, H = 600;
    private MainFrame frame;
    
    public DesignPanel()
    {
        
    }
    
    public DesignPanel(MainFrame frame)
    {
        this.frame = frame;
        setPreferredSize(new Dimension(W, H));
        setLayout(null);
    }
    
    public void init(MainFrame frame)
    {
        this.frame = frame;
    }
    
    public void addAtRandomLocation(JComponent comp)
    {
        Random random = new Random();
        int x = random.nextInt(W);
        int y = random.nextInt(H);
        int w = comp.getPreferredSize().width;
        int h = comp.getPreferredSize().height;
        comp.setBounds(x, y, w, h);
        comp.setToolTipText(comp.getClass().getName());
        
        comp.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                try {
                    while (frame.getTableModel().getRowCount() > 0){
                        for (int i = 0; i < frame.getTableModel().getRowCount(); ++i){
                            frame.getTableModel().removeRow(i);
                        }
                    }
                    for(PropertyDescriptor d : Introspector.getBeanInfo(comp.getClass()).getPropertyDescriptors())
                    {
                        String propertyName = d.getName();
                        Object value = null;
                        if(d.getReadMethod() != null)
                            value = d.getReadMethod().invoke(comp);
                        frame.getTableModel().addRow(new Object[] {propertyName, value});
                        frame.revalidate();
                    }
                } catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    Logger.getLogger(DesignPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                
            }
        });
        this.add(comp);
        frame.repaint();
    }
}
