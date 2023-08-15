package com.pharmacy.View;

import com.pharmacy.Helper.Config;
import com.pharmacy.Helper.Helper;

import javax.swing.*;

public class WorkerGUI extends JFrame{
    private JPanel wrapper;

    public WorkerGUI() {
        add(wrapper);
        setSize(580, 520);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);
    }
}
