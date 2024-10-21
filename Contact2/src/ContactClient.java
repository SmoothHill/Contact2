import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactClient {
    private final ContactService contactService = new ContactService();
    private JTextArea textArea;
    private JTextField nameField, addressField, phoneField;

    public ContactClient() {
        JFrame frame = new JFrame("通讯录系统");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 300);

        // 创建文本区域
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        // 创建输入面板
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout()); // 使用流式布局

        nameField = new JTextField(10); // 设置文本框宽度
        addressField = new JTextField(10);
        phoneField = new JTextField(10);

        inputPanel.add(new JLabel("姓名:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("地址:"));
        inputPanel.add(addressField);
        inputPanel.add(new JLabel("电话:"));
        inputPanel.add(phoneField);

        // 创建按钮面板
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton addButton = new JButton("增加");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = contactService.addContact(nameField.getText(), addressField.getText(), phoneField.getText());
                JOptionPane.showMessageDialog(frame, message); // 弹出消息框
                displayContacts();
            }
        });

        JButton modifyButton = new JButton("更改");
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = contactService.modifyContact(nameField.getText(), addressField.getText(), phoneField.getText());
                JOptionPane.showMessageDialog(frame, message);
                displayContacts();
            }
        });

        JButton deleteButton = new JButton("删除");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = contactService.deleteContact(nameField.getText());
                JOptionPane.showMessageDialog(frame, message);
                displayContacts();
            }
        });

        JButton viewButton = new JButton("查看");
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayContacts();
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewButton);

        // 设置布局
        frame.setLayout(new BorderLayout());
        frame.getContentPane().add(inputPanel, BorderLayout.NORTH); // 输入面板放在上方
        frame.getContentPane().add(buttonPanel, BorderLayout.CENTER); // 按钮面板放在中间
        frame.getContentPane().add(scrollPane, BorderLayout.SOUTH); // 文本区域放在底部

        frame.setVisible(true);
    }

    private void displayContacts() {
        textArea.setText("");  // 清空文本区域
        try {
            ResultSet rs = contactService.viewContacts();
            while (rs != null && rs.next()) {
                String name = rs.getString("name");
                String address = rs.getString("address");
                String phone = rs.getString("phone");
                textArea.append(name + " " + address + " " + phone + "\n");
            }
            rs.getStatement().getConnection().close(); // 关闭连接
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "查看联系人失败: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ContactClient::new);
    }
}
