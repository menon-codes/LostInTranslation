package examples;

import java.awt.GridLayout;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;

import translation.CanadaTranslator;
import translation.Translator;

public class JListDemo {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            JPanel languagePanel = new JPanel();
            languagePanel.setLayout(new GridLayout(0, 2));
            languagePanel.add(new JLabel("Language:"), 0);

            Translator translator = new CanadaTranslator();

            String[] items = new String[translator.getLanguageCodes().size()];

            int i = 0;
            for (String langaugeCode : translator.getLanguageCodes()) {
                items[i++] = langaugeCode;
            }

            // create the JList with the array of strings and set it to allow multiple
            // items to be selected at once.
            JList<String> list = new JList<>(items);
            list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            // place the JList in a scroll pane so that it is scrollable in the UI
            JScrollPane scrollPane = new JScrollPane(list);
            languagePanel.add(scrollPane, 1);

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(languagePanel);

            list.addListSelectionListener((ListSelectionEvent e) -> {
                int[] indices = list.getSelectedIndices();
                String[] items1 = new String[indices.length];
                for (int i1 = 0; i1 < indices.length; i1++) {
                    items1[i1] = list.getModel().getElementAt(indices[i1]);
                }
                JOptionPane.showMessageDialog(null, "User selected:" +
                        System.lineSeparator() + Arrays.toString(items1));
            } /**
             * Called whenever the value of the selection changes.
             *
             * @param e the event that characterizes the change.
             */ );

            JFrame frame = new JFrame("JList Demo");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null); // place in centre of screen
            frame.pack();
            frame.setVisible(true);

        });
    }
}
