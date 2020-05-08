package com.amihaeseisergiu.laborator12;

import java.awt.BorderLayout;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ControlPanel extends JPanel {
    
    private final MainFrame frame;
    private final JLabel classNameLabel = new JLabel("Class name");
    private final JTextField classNameField = new JTextField(30);
    private final JLabel textLabel = new JLabel("Default text");
    private final JTextField textField = new JTextField(10);
    private final JButton createButton = new JButton("Add component");
    private final JButton saveButton = new JButton("Save");
    private final JButton loadButton = new JButton("Load");
    
    public ControlPanel(MainFrame frame)
    {
        this.frame = frame;
        init();
    }
    
    private void init()
    {
        add(classNameLabel);
        add(classNameField);
        add(textLabel);
        add(textField);
        add(createButton);
        add(saveButton);
        add(loadButton);
        
        createButton.addActionListener(e -> {
            JComponent comp = createDynamicComponent(classNameField.getText());
            if (comp != null) {
                setComponentText(comp, textField.getText());
                frame.getDesignPanel().addAtRandomLocation(comp);
            }
        });

        saveButton.addActionListener(e -> {
            
            try {
                
                XMLEncoder encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("project.xml")));
                encoder.writeObject(frame.getDesignPanel());
                encoder.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ControlPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        loadButton.addActionListener(e -> {
            
            try {
                
                XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream("project.xml")));
                DesignPanel dp = (DesignPanel) decoder.readObject();
                decoder.close();

                frame.setDesignPanel(dp);
                frame.setVisible(true);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ControlPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    private JComponent createDynamicComponent(String className)
    {
        try {
            Class myclass = Class.forName(className);
            return (JComponent) myclass.newInstance();
        } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) { }
        return null;
    }

    private void setComponentText(JComponent comp, String text)
    {
        Class myclass = comp.getClass();
        Method a;
        try {
            a = myclass.getMethod("setText", String.class);
            a.invoke(comp, text);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) { }
    }
}
