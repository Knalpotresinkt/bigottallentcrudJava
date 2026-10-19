/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Form;

import Koneksi.Koneksi;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FormJuri extends javax.swing.JFrame {
    DefaultTableModel model;

    public FormJuri() {
        initComponents();
        setLocationRelativeTo(null); // Posisi form di tengah layar
        loadDataSiswa();
    }

    public void loadDataSiswa() {
        model = new DefaultTableModel();
        model.addColumn("ID Daftar");
        model.addColumn("Nama Siswa");
        model.addColumn("Mata Lomba");
        model.addColumn("Nilai");
        model.addColumn("Catatan");
        tblPenilaian.setModel(model);
        
        try {
            Connection c = Koneksi.configDB();
            Statement s = c.createStatement();
            // Ambil data gabungan dari 3 tabel
            String sql = "SELECT pendaftaran.id, users.username, lomba.nama_lomba, pendaftaran.nilai, pendaftaran.catatan " +
                         "FROM pendaftaran " +
                         "JOIN users ON pendaftaran.user_id = users.id " +
                         "JOIN lomba ON pendaftaran.lomba_id = lomba.id";
            ResultSet r = s.executeQuery(sql);
            while (r.next()) {
                model.addRow(new Object[]{ 
                    r.getString("id"), 
                    r.getString("username"), 
                    r.getString("nama_lomba"), 
                    r.getString("nilai"), 
                    r.getString("catatan") 
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data pendaftar: " + e.getMessage());
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblPenilaian = new javax.swing.JTable();
        cmbNilai = new javax.swing.JComboBox<>();
        txtCatatan = new javax.swing.JTextField();
        btnSimpanNilai = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblPenilaian.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblPenilaian);

        cmbNilai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Lolos", "Tidak Lolos" }));

        btnSimpanNilai.setText("Simpan Nilai");
        btnSimpanNilai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanNilaiActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("PANEL JURI");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cmbNilai, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtCatatan)
                    .addComponent(btnSimpanNilai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 57, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(274, 274, 274))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(cmbNilai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtCatatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSimpanNilai)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSimpanNilaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanNilaiActionPerformed
       int baris = tblPenilaian.getSelectedRow();
        if (baris == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data pendaftar di tabel terlebih dahulu!");
            return;
    }//GEN-LAST:event_btnSimpanNilaiActionPerformed

    String idPendaftaran = model.getValueAt(baris, 0).toString();
        String nilai = cmbNilai.getSelectedItem().toString();
        String catatan = txtCatatan.getText();
        
        try {
            Connection c = Koneksi.configDB();
            Statement s = c.createStatement();
            // Simpan penilaian juri ke database
            String sql = "UPDATE pendaftaran SET nilai='" + nilai + "', catatan='" + catatan + "' WHERE id='" + idPendaftaran + "'";
            s.executeUpdate(sql);
            
            JOptionPane.showMessageDialog(this, "Penilaian berhasil disimpan!");
            loadDataSiswa(); // Refresh tabel setelah dinilai
            txtCatatan.setText(""); // Kosongkan form catatan
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal update penilaian: " + e.getMessage());
        }
    }                                              
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormJuri.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormJuri.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormJuri.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormJuri.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormJuri().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSimpanNilai;
    private javax.swing.JComboBox<String> cmbNilai;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblPenilaian;
    private javax.swing.JTextField txtCatatan;
    // End of variables declaration//GEN-END:variables
}
