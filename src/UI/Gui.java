/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

/**
 *
 * @author Julietta
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import static java.awt.image.ImageObserver.WIDTH;
import java.util.Collection;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import logika.Hra;

public class Gui {

    private Hra hra;
    private JFrame hlavniOknoFrame;
    private JTextArea vystupTextArea;
    private JLabel zadejPrikazLabel;
    private JTextField vstupPrikazuTextField;
    private JPanel dolniPanel;
    private Icon logoIcon;
    private JLabel obrazekLabel;
    private JPanel horniPanel;
    private JPanel panelVychodu;
    private JPanel pravyPanel;

    private JMenuBar listaMenuBar;
    private JMenu souborMenu, napovedaMenu;

    private JMenuItem novaHraMenuItem, konecMenuItem;
    private JMenuItem napovedaMenuItem, oProgramuMenuItem;

    private OknoNapovedy oknoNapovedy;

    private String radek, text;

    private PanelVychodu seznamVychodu;
    private OknoProstoru oknoProstoru;

    private PanelBatoh oknoBatoh;

    private ZpracovaniPrikazu zpracujPrikazListener;

    /**
     * listener zpracování příkazu
     */
    private class ZpracovaniPrikazu implements ActionListener {

        /**
         * v actionPerformed toho moc nastaveného nemám, co? No,
         * zpracovaniPrikazu využívá i panelInventare a panel Vychodu, proto
         * jsem je oddelit musel.
         *
         * @param arg0
         */
        @Override
        public void actionPerformed(ActionEvent arg0) {
            zpracovaniPrikazu(vstupPrikazuTextField.getText());
        }

        /**
         * A toto je ten dělník, co v tváře potu datluje příkazy Hře.
         *
         * @param vstup - vstupní příkaz
         */
        public void zpracovaniPrikazu(String vstup) {
            Collection vychody = hra.getHerniPlan().getAktualniProstor().getVychody();
            radek = vstup;
            text = hra.zpracujPrikaz(radek);

            if ((!vychody.equals(hra.getHerniPlan().getAktualniProstor().getVychody()))) {
                vystupTextArea.setText("\n\n" + radek + "\n");
                vystupTextArea.append("\n" + text + "\n");
            } else {
                vystupTextArea.append("\n\n" + radek + "\n");
                vystupTextArea.append("\n" + text + "\n");
                vystupTextArea.setCaretPosition(vystupTextArea.getDocument().getLength());
            }

            if (hra.konecHry()) {
                vstupPrikazuTextField.setEditable(false);
                vystupTextArea.append("\n\n" + hra.vratEpilog());
            }
            vstupPrikazuTextField.setText("");

            vystupTextArea.setCaretPosition(vystupTextArea.getDocument().getLength());
        }
    }

    /**
     * Vnitřní třída implementující ActionListener, zobrazí informace o programu
     */
    private class OProgramu implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent evt) {
            JOptionPane.showMessageDialog(hlavniOknoFrame,
                    "<html><body><h2>Čekání na Godota<h2>"
                    + "<p>Verze 2.1 - ve fázi sestavování GUI</p>"
                    + "<p><i>Autor: já</i></p>"
                    + "</body></html>", "O programu", 1);
        }
    }

    /**
     * Vnitřní třída implementující ActionListener, zobrazí html s nápovědou
     */
    private class Napoveda implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            oknoNapovedy = new OknoNapovedy("/zdroje/napoveda.html");
            oknoNapovedy.setVisible(true);
        }
    }

    /**
     * Vnitřní třída implementující ActionListener, nezobrazí nic, zato všechno
     * vypne
     */
    private class KonecHry implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    /**
     * Vnitřní třída implementující ActionListener, spustí hru nanovo
     */
    private class NovaHra implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            hra = new Hra();
            oknoProstoru.nastaveniHernihoPlanu(hra.getHerniPlan());
            seznamVychodu.nastaveniHernihoPlanu(hra.getHerniPlan());
            oknoBatoh.nastaveniHernihoPlanu(hra.getHerniPlan());
            vystupTextArea.setText(hra.vratUvitani());
            vstupPrikazuTextField.requestFocus();
        }
    }

    /**
     * Konstruktor, který propojí grafiku a logiku, inicializuje grafické
     * komponenty
     *
     * @param hra logika
     */
    public Gui(Hra hra) {
        this.hra = hra;
        initMenu();
        init();
        vstupPrikazuTextField.requestFocus();
    }

    /**
     * Metoda inicializuje a propojuje jednotlivé komponenty GUI
     */
    private void init() {

//VYTVOŘENÍ OKNA
        hlavniOknoFrame = new JFrame();
        hlavniOknoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        hlavniOknoFrame.setTitle("ADVENTURA");
        hlavniOknoFrame.setLocation(640, 0);
        hlavniOknoFrame.setPreferredSize(new Dimension(720, 700));

        hlavniOknoFrame.setJMenuBar(listaMenuBar);

        dolniPanel = new JPanel();
        vystupTextArea = new JTextArea(hra.vratUvitani());
        vystupTextArea.setEditable(false);
        vystupTextArea.setLineWrap(true);
        vystupTextArea.setWrapStyleWord(true);
        zadejPrikazLabel = new JLabel("Zadej prikaz");
        vstupPrikazuTextField = new JTextField(10);

        seznamVychodu = new PanelVychodu(hra.getHerniPlan());
        seznamVychodu.setGui(this);
        panelVychodu = new JPanel(new BorderLayout());

        panelVychodu.add(seznamVychodu);

        oknoProstoru = new OknoProstoru(hra.getHerniPlan());
        oknoBatoh = new PanelBatoh(hra.getHerniPlan());
        oknoBatoh.setGui(this);

        hlavniOknoFrame.add(new JScrollPane(vystupTextArea));

        hlavniOknoFrame.add(oknoBatoh, BorderLayout.WEST);

        dolniPanel.add(zadejPrikazLabel);
        dolniPanel.add(vstupPrikazuTextField);
        zpracujPrikazListener = new ZpracovaniPrikazu();
        vstupPrikazuTextField.addActionListener(zpracujPrikazListener);

        pravyPanel = new JPanel();
        pravyPanel.setLayout(new BoxLayout(pravyPanel, WIDTH));
        pravyPanel.add(seznamVychodu);
        hlavniOknoFrame.add(pravyPanel, BorderLayout.EAST);

        hlavniOknoFrame.add(dolniPanel, BorderLayout.SOUTH);

        hlavniOknoFrame.pack();
    }

    /**
     * a poslední inicializační metoda, ta vytvoří menu
     */
    private void initMenu() {
        listaMenuBar = new JMenuBar();
        souborMenu = new JMenu("Soubor");
        souborMenu.setMnemonic(KeyEvent.VK_S);
        listaMenuBar.add(souborMenu);

        novaHraMenuItem = new JMenuItem("Nová hra");
        novaHraMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl N"));
        novaHraMenuItem.addActionListener(new NovaHra());

        souborMenu.addSeparator();

        konecMenuItem = new JMenuItem("Konec");
        konecMenuItem.setAccelerator(KeyStroke.getKeyStroke("F12"));
        konecMenuItem.addActionListener(new KonecHry());

        souborMenu.add(novaHraMenuItem);
        souborMenu.add(konecMenuItem);

        napovedaMenu = new JMenu("Nápověda");
        napovedaMenu.setMnemonic(KeyEvent.VK_N);
        listaMenuBar.add(napovedaMenu);

        napovedaMenuItem = new JMenuItem("Nápověda");
        napovedaMenuItem.setAccelerator(KeyStroke.getKeyStroke("F1"));
        napovedaMenuItem.addActionListener(new Napoveda());

        oProgramuMenuItem = new JMenuItem("O programu");
        oProgramuMenuItem.addActionListener(new OProgramu());

        napovedaMenu.addSeparator();

        napovedaMenu.add(napovedaMenuItem);
        napovedaMenu.add(oProgramuMenuItem);
    }

    /**
     * metoda slouží paneluVychodu a inventáři k přístupu na metodu
     * zpracovaniPrikazu příslušného zdejšího listeneru
     *
     * @param prikaz - string s příkazem
     */
    public void zpracujPrikaz(String prikaz) {
        zpracujPrikazListener.zpracovaniPrikazu(prikaz);
    }

    /**
     * Metoda, která zviditelňuje GUI
     *
     * @param viditelnost GUI
     */
    public void setVisible(boolean viditelnost) {
        hlavniOknoFrame.setVisible(viditelnost);
    }
}

