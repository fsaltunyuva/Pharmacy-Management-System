package com.pharmacy.View;

import com.pharmacy.Helper.Config;
import com.pharmacy.Helper.Helper;
import com.pharmacy.Model.Operator;

import javax.swing.*;

public class LoginGUI extends JFrame{
    private JPanel wrapper;
    private JPanel wtop;
    private JPanel wbottom;
    private JTextField fld_user_uname;
    private JPasswordField fld_user_pass;
    private JButton btn_login;

    public LoginGUI(){
        add(wrapper);
        setSize(580, 520);
        setLocation(Helper.screenCenterPoint("x", getSize()),Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);

        btn_login.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_user_uname) ||Helper.isFieldEmpty(fld_user_pass)){
                Helper.showMessage("fill");
            }
            else{
                Operator u = (Operator) Operator.getFetch(fld_user_uname.getText(), fld_user_pass.getText());
                if(u == null){
                    Helper.showMessage("User not found.");
                }
                else{
                    switch (u.getType()){
                        case "operator":
                            OperatorGUI opGUI = new OperatorGUI((Operator) u);
                            break;
                        case "worker":
                            WorkerGUI edGUI = new WorkerGUI();
                            break;
                        case "patient":
                            PatientGUI patientGUI = new PatientGUI();
                            break;
                    }
                    dispose(); //To close the window that leads to another window
                }
            }
        });
    }

    public static void main(String[] args) {
        Helper.setLayout();
        LoginGUI login = new LoginGUI();
    }
}
