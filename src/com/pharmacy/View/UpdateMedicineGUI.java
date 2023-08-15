package com.pharmacy.View;

import com.pharmacy.Helper.Config;
import com.pharmacy.Helper.Helper;
import com.pharmacy.Model.Medicine;

import javax.swing.*;

public class UpdateMedicineGUI extends JFrame{
    private JPanel wrapper;
    private JTextField fld_medicine_name;
    private JButton btn_update;
    private JTextField fld_medicine_stock_count;
    private JTextField fld_medicine_active_ingredient;
    private JTextField fld_medicine_dose;
    private JTextField fld_medicine_price;
    private JTextField fld_medicine_expire_date;
    private Medicine medicine;

    public UpdateMedicineGUI(Medicine medicine) {
        this.medicine = medicine;
        add(wrapper);
        setSize(300,450);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        fld_medicine_name.setText(medicine.getName());
        fld_medicine_stock_count.setText(String.valueOf(medicine.getStockCount()));
        fld_medicine_active_ingredient.setText(medicine.getActiveIngredient());
        fld_medicine_dose.setText(String.valueOf(medicine.getDose()));
        fld_medicine_price.setText(String.valueOf(medicine.getPrice()));
        fld_medicine_expire_date.setText(medicine.getExpireDate());

        btn_update.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_medicine_name) || Helper.isFieldEmpty(fld_medicine_stock_count) || Helper.isFieldEmpty(fld_medicine_active_ingredient) || Helper.isFieldEmpty(fld_medicine_dose) || Helper.isFieldEmpty(fld_medicine_price) || Helper.isFieldEmpty(fld_medicine_expire_date)) {
                Helper.showMessage("fill");
            }
            else {
                if (Medicine.update(medicine.getId(), fld_medicine_name.getText(), Integer.parseInt(fld_medicine_stock_count.getText()), fld_medicine_active_ingredient.getText(), Integer.parseInt(fld_medicine_dose.getText()), Integer.parseInt(fld_medicine_price.getText()), fld_medicine_expire_date.getText())){
                    Helper.showMessage("done");
                }
                dispose();
            }
        });
    }


}
