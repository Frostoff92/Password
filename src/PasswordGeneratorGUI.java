import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class PasswordGeneratorGUI extends JFrame implements ActionListener {

    private JLabel passwordLabel;
    private JTextArea passwordArea;
    private JButton generateButton;
    private JButton clearButton;
    private Clipboard clipboard;
    private static final String CHARACTERS = "abcdefghijkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789";
    private StringBuilder generatedPasswords = new StringBuilder();

    public PasswordGeneratorGUI() {
        setTitle("Генератор паролей");
        setSize(450, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        passwordLabel = new JLabel("Сгенерированные пароли:");
        passwordArea = new JTextArea(10,20);
        passwordArea.setFont(new Font("Arial", Font.BOLD,20));


        generateButton = new JButton("Сгенерировать");
        generateButton.addActionListener(this);

        clearButton = new JButton("Очистить");
        clearButton.addActionListener(this);

        JPanel panel = new JPanel();
        panel.add(passwordLabel);
        panel.add(passwordArea);
        panel.add(generateButton);
        panel.add(clearButton);

        clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        setContentPane(panel);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == generateButton) {
            generatedPasswords.setLength(0);
            passwordArea.setText("");
            for (int i = 0; i < 10; i++) {
                String password = generatePassword();
                generatedPasswords.append(password).append("\n");
                passwordArea.append(password + "\n");
            }
            passwordArea.selectAll();

            StringSelection selection = new StringSelection(generatedPasswords.toString());
            clipboard.setContents(selection, null);
        }
        if (e.getSource() == clearButton)   {
            generatedPasswords.setLength(0);
            passwordArea.setText("");
        }
    }

    private static String generatePassword() {
        StringBuilder password = new StringBuilder();
        while (password.length() < 8) {
            int index = (int)(Math.random() * CHARACTERS.length());
            password.append(CHARACTERS.charAt(index));
        }
        if (!password.toString().matches(".*\\d.*")) { // Make sure there is at least one digit
            password.setCharAt((int)(Math.random() * 8), (char)(Math.random() * 10 + '0'));
        }
        return password.toString();
    }

    public static void main(String[] args) {
        new PasswordGeneratorGUI();
    }
}
