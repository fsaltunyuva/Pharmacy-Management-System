package com.pharmacy.View;

import com.pharmacy.Helper.*;
import com.pharmacy.Model.Patient;
import com.pharmacy.Model.Operator;
import com.pharmacy.Model.Medicine;
import com.pharmacy.Model.User;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class OperatorGUI extends JFrame {
    private JPanel wrapper;
    private JTabbedPane tab_operator;
    private JLabel lbl_welcome;
    private JPanel pnl_top;
    private JButton btn_logout;
    private JPanel pnl_user_list;
    private JScrollPane scrl_user_list;
    private JTable tbl_user_list;
    private JPanel pnl_user_form;
    private JTextField fld_user_name;
    private JTextField fld_user_uname;
    private JTextField fld_user_pass;
    private JComboBox cmb_user_type;
    private JButton btn_user_add;
    private JTextField fld_user_id;
    private JButton btn_user_delete;
    private JTextField fld_user_id_delete;
    private JTextField fld_sh_user_name;
    private JTextField fld_sh_user_uname;
    private JComboBox cmb_sh_user_type;
    private JButton btn_user_sh;
    private JPanel pnl_medicine_list;
    private JScrollPane scrl_medicine_list;
    private JTable tbl_medicine_list;
    private JPanel pnl_medicine_Add;
    private JTextField fld_medicine_name;
    private JButton btn_medicine_add;
    private JPanel pnl_user_top;
    private JPanel pnl_patient_list;
    private JScrollPane scrl_patient_list;
    private JTable tbl_patient_list;
    private JPanel pnl_patient_add;
    private JTextField fld_patient_name;
    private JTextField fld_patient_lang;
    private JComboBox cmb_patient_medicine;
    private JComboBox cmb_patient_associated_worker;
    private JButton btn_patient_add;
    private JTextField fld_medicine_stock_count;
    private JTextField fld_medicine_active_ingredient;
    private JTextField fld_medicine_dose;
    private JTextField fld_medicine_price;
    private JTextField fld_medicine_expire_date;
    private DefaultTableModel mdl_user_list;
    private Object[] row_user_list;
    private DefaultTableModel mdl_medicine_list;
    private Object[] row_medicine_list;
    private final Operator operator;
    private JPopupMenu medicineMenu;
    private DefaultTableModel mdl_patient_list;
    private Object[] row_patient_list;

    public OperatorGUI(Operator operator) {
        this.operator = operator;

        add(wrapper);
        setSize(1800, 700);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        lbl_welcome.setText("Welcome " + operator.getName());

        //ModelUserList
        mdl_user_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0) return false;
                return super.isCellEditable(row, column);
            }
        };
        
        Object[] col_user_list = {"SSN", "Name-Surname", "User Name", "Password", "User Type"}; //Column names for the users
        mdl_user_list.setColumnIdentifiers(col_user_list);
        row_user_list = new Object[col_user_list.length];

        loadUserModel();

        tbl_user_list.setModel(mdl_user_list);
        tbl_user_list.getTableHeader().setReorderingAllowed(false);


        tbl_user_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String select_user_id = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 0).toString();
                fld_user_id_delete.setText(select_user_id);
            } catch (Exception exception) {
                //System.out.println(exception.getMessage());
            }
        });

        tbl_user_list.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                int user_id = Integer.parseInt(tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 0).toString());
                String user_name = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 1).toString();
                String user_uname = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 2).toString();
                String user_pass = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 3).toString();
                String user_type = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 4).toString();

                if (User.update(user_id, user_name, user_uname, user_pass, user_type)) {
                    Helper.showMessage("done");
                }
                /*else {
                    Helper.showMessage("error");
                }
                 */
                loadUserModel();
                loadWorkerCombo();
                loadPatientModel();
            }
        });
        //##ModelUserList


        //Medicine List
        medicineMenu = new JPopupMenu();
        JMenuItem updateMenu = new JMenuItem("Update");
        JMenuItem deleteMenu = new JMenuItem("Delete");
        medicineMenu.add(updateMenu);
        medicineMenu.add(deleteMenu);

        updateMenu.addActionListener(e -> {
            int select_id = Integer.parseInt(tbl_medicine_list.getValueAt(tbl_medicine_list.getSelectedRow(), 0).toString());
            UpdateMedicineGUI updateGUI = new UpdateMedicineGUI(Medicine.getFetch(select_id));
            updateGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadMedicineModel();
                    loadMedicineCombo();
                    loadPatientModel();
                }
            });
        });

        deleteMenu.addActionListener(e -> {
            if (Helper.confirm("sure")) {
                int select_id = Integer.parseInt(tbl_medicine_list.getValueAt(tbl_medicine_list.getSelectedRow(), 0).toString());
                if (Medicine.delete(select_id)) {
                    Helper.showMessage("done");
                    loadMedicineModel();
                    loadMedicineCombo();
                    loadPatientModel();
                } else {
                    Helper.showMessage("error");
                }
            }
        });

        mdl_medicine_list = new DefaultTableModel();
        Object[] col_medicine_list = {"ID", "Name", "Stock Count", "Active Ingredient", "Dose (mg)", "Price (TL)", "Expire Date"};
        mdl_medicine_list.setColumnIdentifiers(col_medicine_list);
        row_medicine_list = new Object[col_medicine_list.length];
        loadMedicineModel();

        tbl_medicine_list.setModel(mdl_medicine_list);
        tbl_medicine_list.setComponentPopupMenu(medicineMenu);
        tbl_medicine_list.getTableHeader().setReorderingAllowed(false);
        tbl_medicine_list.getColumnModel().getColumn(0).setMaxWidth(75);

        tbl_medicine_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int selected_row = tbl_medicine_list.rowAtPoint(point);
                tbl_medicine_list.setRowSelectionInterval(selected_row, selected_row);
            }
        });
        //##Medicine List

        //Patient List
        mdl_patient_list = new DefaultTableModel();
        Object[] col_patient_list = {"ID", "Name-Surname", "Allergies", "Medicine", "Associated Worker"};
        mdl_patient_list.setColumnIdentifiers(col_patient_list);
        row_patient_list = new Object[col_patient_list.length];
        loadPatientModel();

        tbl_patient_list.setModel(mdl_patient_list);
        tbl_patient_list.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_patient_list.getTableHeader().setReorderingAllowed(false);
        loadMedicineCombo();
        loadWorkerCombo();

        //## Patient List


        btn_user_add.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_user_name) || Helper.isFieldEmpty(fld_user_pass) || Helper.isFieldEmpty(fld_user_uname)) {
                Helper.showMessage("fill");
            } else {
                int id = Integer.parseInt(fld_user_id.getText());
                String name = fld_user_name.getText();
                String uname = fld_user_uname.getText();
                String pass = fld_user_pass.getText();
                String type = cmb_user_type.getSelectedItem().toString();

                if (User.add(id, name, uname, pass, type)) {
                    Helper.showMessage("done");
                    loadUserModel();
                    loadWorkerCombo();
                    fld_user_id.setText(null);
                    fld_user_name.setText(null);
                    fld_user_uname.setText(null);
                    fld_user_pass.setText(null);
                }
                //else{
                //Helper.showMessage("error");
                //}
            }
        });


        btn_user_delete.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_user_id_delete)) {
                Helper.showMessage("fill");
            } else {
                if (Helper.confirm("sure")) {
                    int user_id = Integer.parseInt(fld_user_id_delete.getText());
                    if (User.delete(user_id)) {
                        Helper.showMessage("done");
                        loadUserModel();
                        loadWorkerCombo();
                        loadPatientModel();
                        fld_user_id_delete.setText(null);
                    } else {
                        Helper.showMessage("error");
                    }
                }
            }
        });


        btn_user_sh.addActionListener(e -> {
            String name = fld_sh_user_name.getText();
            String uname = fld_sh_user_uname.getText();
            String type = cmb_sh_user_type.getSelectedItem().toString();

            String query = User.searchQuery(name, uname, type);

            loadUserModel(User.searchUserList(query));
        });


        btn_logout.addActionListener(e -> {
            dispose();
            LoginGUI login = new LoginGUI();
        });


        btn_medicine_add.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_medicine_name)) {
                Helper.showMessage("fill");
            } else {
                if (Medicine.add(fld_medicine_name.getText(), Integer.parseInt(fld_medicine_stock_count.getText()), fld_medicine_active_ingredient.getText(), Integer.parseInt(fld_medicine_dose.getText()), Integer.parseInt(fld_medicine_price.getText()), fld_medicine_expire_date.getText())) {
                    Helper.showMessage("done");
                    loadMedicineModel();
                    loadMedicineCombo();
                    fld_medicine_name.setText(null);
                } else {
                    Helper.showMessage("error");
                }
            }
        });

        btn_patient_add.addActionListener(e -> {
            Item medicineItem = (Item) cmb_patient_medicine.getSelectedItem();
            Item userItem = (Item) cmb_patient_associated_worker.getSelectedItem();
            if (Helper.isFieldEmpty(fld_patient_name) || Helper.isFieldEmpty(fld_patient_lang)){
                Helper.showMessage("fill");
            }
            else{
                if(Patient.add(userItem.getKey(), medicineItem.getKey(), fld_patient_name.getText(), fld_patient_lang.getText())){
                    Helper.showMessage("done");
                    loadPatientModel();
                    fld_patient_lang.setText(null);
                    fld_patient_name.setText(null);
                }
                else{
                    Helper.showMessage("error");
                }

            }
        });
    }

    private void loadPatientModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_patient_list.getModel();
        clearModel.setRowCount(0);

        int i = 0;
        for(Patient obj : Patient.getList()){
            i = 0;
            row_patient_list[i++] = obj.getId();
            row_patient_list[i++] = obj.getName();
            row_patient_list[i++] = obj.getAllergies();
            row_patient_list[i++] = obj.getPatient().getName();
            row_patient_list[i++] = obj.getWorker().getName();
            mdl_patient_list.addRow(row_patient_list);
        }
    }

    private void loadMedicineModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_medicine_list.getModel();
        clearModel.setRowCount(0);
        int i = 0;
        for (Medicine obj : Medicine.getList()) {
            i = 0;
            row_medicine_list[i++] = obj.getId();
            row_medicine_list[i++] = obj.getName();
            row_medicine_list[i++] = obj.getStockCount();
            row_medicine_list[i++] = obj.getActiveIngredient();
            row_medicine_list[i++] = obj.getDose();
            row_medicine_list[i++] = obj.getPrice();
            row_medicine_list[i++] = obj.getExpireDate();
            mdl_medicine_list.addRow(row_medicine_list);
        }
    }


    public void loadUserModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_user_list.getModel();
        clearModel.setRowCount(0);

        for (User obj : User.getList()) {
            int i = 0;
            row_user_list[i++] = obj.getId();
            row_user_list[i++] = obj.getName();
            row_user_list[i++] = obj.getUname();
            row_user_list[i++] = obj.getPass();
            row_user_list[i++] = obj.getType();
            mdl_user_list.addRow(row_user_list);
        }
    }

    public void loadUserModel(ArrayList<User> list) {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_user_list.getModel();
        clearModel.setRowCount(0);

        for (User obj : list) {
            int i = 0;
            row_user_list[i++] = obj.getId();
            row_user_list[i++] = obj.getName();
            row_user_list[i++] = obj.getUname();
            row_user_list[i++] = obj.getPass();
            row_user_list[i++] = obj.getType();
            mdl_user_list.addRow(row_user_list);
        }
    }

    public void loadMedicineCombo(){
        cmb_patient_medicine.removeAllItems();
        for(Medicine obj : Medicine.getList()){
            cmb_patient_medicine.addItem(new Item(obj.getId(), obj.getName()));
        }
    }

    public void loadWorkerCombo(){
        cmb_patient_associated_worker.removeAllItems();
        for(User obj : User.getListOnlyWorker()){
            if(obj.getType().equals("worker")){
                cmb_patient_associated_worker.addItem(new Item(obj.getId(), obj.getName()));
            }
        }
    }

    public static void main(String[] args) {
        Helper.setLayout();
        Operator op = new Operator();

        op.setId(1);
        op.setName("Furkan Safa");
        op.setPass("1234");
        op.setPass("1234");
        op.setType("operator");
        op.setUname("abc");

        OperatorGUI opGUI = new OperatorGUI(op);
    }


}

