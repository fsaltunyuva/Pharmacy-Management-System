package com.pharmacy.Helper;

import javax.swing.*;
import java.awt.*;

public class Helper {

    public static void setLayout(){ //Making the layout of the app more modern looking with "Nimbus" theme
        for (UIManager.LookAndFeelInfo info: UIManager.getInstalledLookAndFeels()){
            if("Nimbus".equals(info.getName())){
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException |
                         IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public static int screenCenterPoint(String axis, Dimension size){ //To calculate the center pixels of the current computer
        int point = 0;

        switch (axis){
            case "x":
                point = ((Toolkit.getDefaultToolkit().getScreenSize().width - size.width) / 2);
                break;
            case "y":
                point = ((Toolkit.getDefaultToolkit().getScreenSize().height - size.height) / 2);
                break;

            default:
                point = 0;
        }

        return point;

    }

    public static boolean isFieldEmpty(JTextField field){ //Checking that if the given field is empty or not
        return field.getText().trim().isEmpty();
    }

    public static void showMessage(String str){ //A helper method to show warning messages if necessary. (It gives warning according to the given parameter)
        String msg;
        String title;

        switch (str){
            case "fill":
                msg = "Please fill in all fields.";
                title = "Error";
                break;
            case "done":
                msg = "The operation is successful.";
                title = "Result";
                break;
            case "error":
                msg = "Something went wrong.";
                title = "Error";
                break;
            default:
                msg = str;
                title = "Message";
        }

        JOptionPane.showMessageDialog(null, msg,title,JOptionPane.INFORMATION_MESSAGE); //Creation of the warning window according to the parameter
    }

    public static boolean confirm(String str){
        String msg;

        switch (str) {
            case "sure":
                msg = "Are you sure you want to perform this action?";
                break;
            default:
                msg = str;
        }
        return JOptionPane.showConfirmDialog(null, msg, "Is it your final decision?", JOptionPane.YES_NO_OPTION) == 0;
    }

}
