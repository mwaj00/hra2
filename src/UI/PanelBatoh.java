/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import logika.HerniPlan;
import logika.Prostor;
import logika.Vec;
import utils.Observer;

/**
 *
 * @author Julietta
 */
/*public class PanelBatoh {
    
    private ObservableList<Vec> seznamVeciData = FXCollections.observableArrayList();
    private ListView seznamVeci = new ListView(seznamVeciData);
    
    private void init(){
        seznamVeci.setItems(seznamVeciData);
    }
    
    PanelBatoh(HerniPlan plan) {
        super();
        this.plan = plan;
        init();
        nastaveniHernihoPlanu(plan);
    }
} */



public class PanelBatoh extends JPanel implements Observer {

    private JPanel batohMujPanel, inventarMistnostPanel;
    private HerniPlan plan;
    private JTextArea popisVeciTextArea;
    //private Gui gui;
    private TitledBorder inventarMistnostBorder;

    /**
     * konstruktur
     *
     * @param plan
     */
    PanelBatoh(HerniPlan plan) {
        super();
        this.plan = plan;
        init();
        nastaveniHernihoPlanu(plan);
    }

    /**
     * listener zajišťující user-friendly funkce jako vybírání myší nebo popis
     * věci v poli dole
     */
    public class VyberVec implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            PolozkaBatohLabel label = (PolozkaBatohLabel) e.getSource();
            Vec vec = label.getVec();
            if (e.getButton() == MouseEvent.BUTTON1) {
                if ((vec.jePrenositelna() == true) && (!plan.getBatoh().getVeci().contains(vec))) {
                    gui.zpracujPrikaz("seber " + label.getVec().getNazev());
                } else {
                    if ((vec.pouzijNaCo() == null) || (!plan.getAktualniProstor().getVeci().contains(vec.pouzijNaCo()))) {
                        gui.zpracujPrikaz("jez " + label.getVec().getNazev());
                    } else {
                        gui.zpracujPrikaz("jez " + label.getVec().getNazev() + " " + vec.pouzijNaCo().getNazev());
                    }
                }
            } else if (e.getButton() == MouseEvent.BUTTON3) {
                if (plan.getInventar().getVeci().contains(vec)) {
                    gui.zpracujPrikaz("zahod " + label.getVec().getNazev());
                } else {
                    if (plan.getAktualniProstor().getVeci().contains(vec)) {
                        gui.zpracujPrikaz("jez " + label.getVec().getNazev());
                    }
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            PolozkaBatohLabel label = (PolozkaBatohLabel) e.getSource();
            popisVeciTextArea.setText(label.getName());
        }

        @Override
        public void mouseExited(MouseEvent e) {
            popisVeciTextArea.setText("");
        }
    }

    /**
     * jedna položka v inventáři - jedna věc - potřeboval jsem, aby komponenta
     * rovnou odkazovala na Věc, proto jsem radši zvolil samostatnou vnořenou
     * třídu rozšiřující JLabel
     */
    public class PolozkaBatohLabel extends JLabel {

        private Vec vec;

        public PolozkaBatohLabel(Vec vec) {
            super();
            this.setBorder(new TitledBorder(vec.getNazev()));
            this.vec = vec;
            this.setIcon(vec.getIcon());
            this.setName(vec.getCojeto());
            this.addMouseListener(new VyberVec());
        }

        public Vec getVec() {
            return vec;
        }
    }

    /**
     * inicializační metoda
     */
    private void init() {
        this.setName("batoh");
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(200, 640));
        batohMujPanel = new JPanel();
        batohMujPanel.setLayout(new BoxLayout(batohMujPanel, WIDTH));
        inventarMistnostPanel = new JPanel();
        inventarMistnostPanel.setLayout(new BoxLayout(inventarMistnostPanel, WIDTH));

        batohMujPanel.setBorder(new TitledBorder("BATOH"));
        batohMujPanel.setPreferredSize(new Dimension(100, 300));
        inventarMistnostBorder = new TitledBorder("PROSTOR");
        inventarMistnostPanel.setBorder(inventarMistnostBorder);
        inventarMistnostPanel.setPreferredSize(new Dimension(100, 300));

        this.add(batohMujPanel, BorderLayout.WEST);
        this.add(inventarMistnostPanel, BorderLayout.EAST);

        popisVeciTextArea = new JTextArea();
        popisVeciTextArea.setEditable(false);
        popisVeciTextArea.setPreferredSize(new Dimension(300, 80));
        popisVeciTextArea.setLineWrap(true);
        popisVeciTextArea.setWrapStyleWord(true);
        this.add(popisVeciTextArea, BorderLayout.SOUTH);

        this.setVisible(true);
    }

    /**
     * metoda nastaví herní plán
     *
     * @param plan - tadydleten Možná by to šlo udělat i lépe, ale původně nebyl
     * PanelInventare, ale OknoInventare, tenhle system vytváření je takový
     * přežitek
     */
    public void nastaveniHernihoPlanu(HerniPlan plan) {
        this.plan = plan;
        plan.zaregistrujPozorovatele(this);
        this.aktualizuj(plan.getAktualniProstor());
    }

    /**
     * A zde se inventář aktualizuje, překrytá metoda Observeru
     *
     * @param mistnost - aktuální prostor
     */
    @Override
    public void aktualizuj(Prostor mistnost) {
        this.repaint();
        batohMujPanel.removeAll();
        this.repaint();
        inventarMistnostPanel.removeAll();
        for (Vec i : plan.getBatoh().getVeci()) {
            batohMujPanel.add(new PolozkaBatohLabel(i));
        }
        for (Vec i : mistnost.getVeci()) {
            inventarMistnostPanel.add(new PolozkaBatohLabel(i));
        }

        batohMujPanel.doLayout();
        inventarMistnostPanel.doLayout();

        inventarMistnostBorder.setTitle(plan.getAktualniProstor().getNazev().toUpperCase());
    }
    
   }
    