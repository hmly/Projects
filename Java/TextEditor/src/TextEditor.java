import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hmly on 6/27/16.
 * Prompt:
 * Text Editor – Notepad style application that can open, edit, and save text
 * documents. Add syntax highlighting and other features.
 *
 * Reference:
 * http://www.leepoint.net/examples/components/editor/nutpad.html
 * http://java-sl.com/tip_row_column.html
 * http://andreinc.net/2013/07/15/how-to-customize-the-font-inside-a-jtextpane
 * -component-java-swing-highlight-java-keywords-inside-a-jtextpane/
 */
public class TextEditor extends JFrame {

    private static final Color DEFAULT_KEYWORD_COLOR = Color.blue;
    private static final String[] JAVA_KEYWORDS = { "abstract",
            "assert", "boolean", "break", "byte", "case", "catch", "char",
            "class", "const", "continue", "default", "do", "double", "else",
            "enum", "extends", "final", "finally", "float", "for", "goto",
            "if", "implements", "import", "instanceof", "int", "long",
            "native", "new", "package", "private", "protected", "public",
            "return", "short", "static", "strictfp", "super", "switch",
            "synchronized", "this", "throw", "throws", "transient", "try",
            "void", "volatile", "while", "false", "null", "true" };
    private static String JAVA_KEYWORDS_REGEX;

    static { // Configure regex from keywords
        StringBuilder buf = new StringBuilder();
        buf.append("(");
        for (String keyword : JAVA_KEYWORDS) {
            buf.append("\\b").append(keyword).append("\\b").append("|");
        }
        buf.deleteCharAt(buf.length()-1);
        buf.append(")");
        JAVA_KEYWORDS_REGEX = buf.toString();
    }

    private JPanel contentPane;
    private JMenuBar menuBar;
    private JToolBar toolBar;
    private JTextPane textEditor;
    private JScrollPane textEditorScrollPane;
    private StyledDocument textEditorDoc;

    private JFileChooser fileChooser = new JFileChooser();
    private Keywords highlight = new Keywords();
    private JTextField status = new JTextField();

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception x) {
            x.printStackTrace();
        }
        TextEditor editor = new TextEditor();
        editor.setVisible(true);
    }

    private TextEditor() {
        setTitle("Java Text Editor");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(700, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        createMenuBar();
        contentPane.add(menuBar, BorderLayout.NORTH);

        textEditor = new JTextPane();
        textEditor.addKeyListener(highlight);
        textEditor.setFont(new Font("monospaced", Font.PLAIN, 15));
        textEditorDoc = textEditor.getStyledDocument();
        textEditor.getDocument().putProperty(
                DefaultEditorKit.EndOfLineStringProperty, "\n");
        textEditorScrollPane = new JScrollPane(textEditor);
        contentPane.add(textEditorScrollPane, BorderLayout.CENTER);

        enableCaretListener();
        contentPane.add(status, BorderLayout.SOUTH);
    }

    private void createMenuBar() {
        menuBar = new JMenuBar();

        JMenu file = menuBar.add(new JMenu("File"));
        file.setMnemonic('F');
        file.add(new TextEditor.OpenAction());
        file.add(new TextEditor.SaveAction());
        file.addSeparator();
        file.add(new TextEditor.ExitAction());

        JMenu edit = menuBar.add(new JMenu("Edit"));
        JMenuItem cut = new JMenuItem(new DefaultEditorKit.CutAction());
        JMenuItem copy = new JMenuItem(new DefaultEditorKit.CopyAction());
        JMenuItem paste = new JMenuItem(new DefaultEditorKit.PasteAction());
        cut.setText("Cut");
        cut.setMnemonic(KeyEvent.VK_CUT);
        copy.setText("Copy");
        copy.setMnemonic(KeyEvent.VK_COPY);
        paste.setText("Paste");
        paste.setMnemonic(KeyEvent.VK_PASTE);
        edit.setMnemonic('E');
        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(new TextEditor.SelectAll());

        JMenu format = menuBar.add(new JMenu("Format"));
        format.setMnemonic('O');
        format.add(new TextEditor.WordWrapAction());

        JMenu view = menuBar.add(new JMenu("View"));
        view.setMnemonic('V');
        view.add(new TextEditor.StatusBar());

        JMenu help = menuBar.add(new JMenu("Help"));
        help.setMnemonic('H');
        help.add(new TextEditor.About());
    }

    // Update the current line and column number
    private void enableCaretListener() {
        textEditor.addCaretListener(e -> {
            int pos = e.getDot();
            int columnNum = pos;
            int lineNum = pos == 0 ? 1 : 0;
            try {
                columnNum = pos - Utilities.getRowStart(textEditor, pos) + 1;
                for (; pos > 0; lineNum++) {
                    pos = Utilities.getRowStart(textEditor, pos) - 1;
                }
            } catch (BadLocationException x) {
                JOptionPane.showMessageDialog(TextEditor.this, x);
            }
            status.setText("Line: " + lineNum + " Column: " + columnNum);
        });
    }

    // Check if each word entered matches a Java keyword
    private class Keywords extends JPanel implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
            int id = e.getID();
            if (id == KeyEvent.KEY_TYPED) {
                clearTextColors();
                Pattern pattern = Pattern.compile(JAVA_KEYWORDS_REGEX);
                Matcher match = pattern.matcher(textEditor.getText());
                while (match.find()) {
                    updateTextColor(match.start(), match.end() - match.start());
                }
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {}

        @Override
        public void keyReleased(KeyEvent e) {}
    }

    private void updateTextColor(int offset, int length, Color c) {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY,
                StyleConstants.Foreground, c);
        textEditorDoc.setCharacterAttributes(offset, length, aset, true);
    }

    private void clearTextColors() {
        updateTextColor(0, textEditor.getText().length(), Color.black);
    }

    private void updateTextColor(int offset, int length) {
        updateTextColor(offset, length, DEFAULT_KEYWORD_COLOR);
    }

    private class SelectAll extends AbstractAction {
        SelectAll() {
            super("Select All");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            textEditor.selectAll();
        }
    }

    private class StatusBar extends AbstractAction {
        StatusBar() {
            super("Status Bar");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(TextEditor.this, "Status Bar already enabled");
        }
    }

    private class About extends AbstractAction {
        About() {
            super("About");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(TextEditor.this,
                    "A simple text editor based on Notepad design and functionality");
        }
    }

    private class WordWrapAction extends AbstractAction {
        WordWrapAction() {
            super("Word Wrap");
        }

        @Override
        public void actionPerformed(ActionEvent e) {}
    }

    private class ExitAction extends AbstractAction {
        ExitAction() {
            super("Exit");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    private class OpenAction extends AbstractAction {
        OpenAction() {
            super("Open File...");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int option = fileChooser.showOpenDialog(TextEditor.this);
            if (option == JFileChooser.APPROVE_OPTION) {
                File f = fileChooser.getSelectedFile();
                try {
                    FileReader reader = new FileReader(f);
                    textEditor.read(reader, "");
                } catch (IOException x) {
                    JOptionPane.showMessageDialog(TextEditor.this,
                            "File Not Found", "ERROR", JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                }
            }
        }
    }

    private class SaveAction extends AbstractAction {
        SaveAction() {
            super("Save");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int option = fileChooser.showSaveDialog(TextEditor.this);
            if (option == JFileChooser.APPROVE_OPTION) {
                File f = fileChooser.getSelectedFile();
                try {
                    FileWriter writer = new FileWriter(f);
                    textEditor.write(writer);
                } catch (IOException x) {
                    JOptionPane.showMessageDialog(TextEditor.this,
                            "File Not Saved", "ERROR", JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                }
            }
        }
    }
}

