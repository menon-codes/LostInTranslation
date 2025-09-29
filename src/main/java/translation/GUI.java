package translation;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Translator translator = new JSONTranslator();
            CountryCodeConverter countryConverter = new CountryCodeConverter();
            LanguageCodeConverter languageConverter = new LanguageCodeConverter();

            JPanel countryPanel = new JPanel();
            // Create country dropdown with country names
            List<String> countryCodes = translator.getCountryCodes();
            JComboBox<String> countryComboBox = new JComboBox<>();
            for (String countryCode : countryCodes) {
                String countryName = countryConverter.fromCountryCode(countryCode);
                countryComboBox.addItem(countryName);
            }
            countryComboBox.setSelectedIndex(0); // Select first country by default
            countryPanel.add(new JLabel("Country:"));
            countryPanel.add(countryComboBox);

            JPanel languagePanel = new JPanel();
            // Create language dropdown with language names
            List<String> languageCodes = translator.getLanguageCodes();
            JComboBox<String> languageComboBox = new JComboBox<>();
            for (String languageCode : languageCodes) {
                String languageName = languageConverter.fromLanguageCode(languageCode);
                languageComboBox.addItem(languageName);
            }
            languageComboBox.setSelectedIndex(0); // Select first language by default
            languagePanel.add(new JLabel("Language:"));
            languagePanel.add(languageComboBox);

            JPanel buttonPanel = new JPanel();
            JButton submit = new JButton("Submit");
            buttonPanel.add(submit);

            JLabel resultLabelText = new JLabel("Translation:");
            buttonPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            buttonPanel.add(resultLabel);

            submit.addActionListener((ActionEvent e) -> {
                // Get selected items from dropdowns
                String selectedLanguageName = (String) languageComboBox.getSelectedItem();
                String selectedCountryName = (String) countryComboBox.getSelectedItem();
                
                // Convert names back to codes for translation
                String languageCode = null;
                String countryCode = null;
                
                // Find the language code for the selected language name
                for (String langCode : translator.getLanguageCodes()) {
                    if (selectedLanguageName.equals(languageConverter.fromLanguageCode(langCode))) {
                        languageCode = langCode;
                        break;
                    }
                }
                
                // Find the country code for the selected country name
                for (String cCode : translator.getCountryCodes()) {
                    if (selectedCountryName.equals(countryConverter.fromCountryCode(cCode))) {
                        countryCode = cCode;
                        break;
                    }
                }
                
                String result = translator.translate(countryCode, languageCode);
                if (result == null) {
                    result = "no translation found!";
                }
                resultLabel.setText(result);
            });

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(countryPanel);
            mainPanel.add(languagePanel);
            mainPanel.add(buttonPanel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);

        });
    }
}
