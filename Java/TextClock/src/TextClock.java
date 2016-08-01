import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hmly on 7/5/16.
 *
 * Simple text clock
 */
public class TextClock extends JFrame {

    private JPanel contentPane;
    private JTextField timeField;
    private JTextField dateField;

    private static final Font DEFAULT_FONT = new Font("consolas", Font.PLAIN, 48);

    private static final int WIDTH = 12;

    private TextClock() {
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());

        timeField = new JTextField(WIDTH);
        timeField.setEditable(false);
        timeField.setFont(DEFAULT_FONT);

        dateField = new JTextField(WIDTH);
        dateField.setEditable(false);
        dateField.setFont(DEFAULT_FONT);

        contentPane.add(timeField, BorderLayout.NORTH);
        contentPane.add(dateField, BorderLayout.CENTER);
        setContentPane(contentPane);
        setTitle("Text Clock");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();

        Timer t = new Timer(1000, new ClockListener()); // Every sec
        t.start();
        addDate();
    }

    private void addDate() {
        DateFormat date = new SimpleDateFormat("MM-dd-y");
        dateField.setText(date.format(new Date()));
    }

    private class ClockListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            DateFormat time = new SimpleDateFormat("hh:mm:ss a");
            timeField.setText(time.format(new Date()));
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception x) {
            x.printStackTrace();
        }
        new TextClock().setVisible(true);
    }
}
