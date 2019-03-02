import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class GUImenu extends JFrame {
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenu subMenu;
    private JMenuItem item1;
    private JMenuItem item2;
    private JMenuItem item3;
    private JMenuItem item4;
    private JMenuItem item5;
    private JMenuItem item6;

    public GUImenu() {
        super("Example Menu System");// Call the JFrame constructor.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // Specify an action for the close button.
        buildMenuBar();

        // Pack and display the window.
        pack();
        setSize(1000, 250); // set frame size
        setVisible(true);
    }

    private void buildMenuBar() {
        // Create the menu bar.
        menuBar = new JMenuBar();

        // Create the file and text menus.
        menu = new JMenu("Menu");
        menuBar.add(menu);
        subMenu = new JMenu("Create Customer");
        item1 = new JMenuItem("Ordinary Customer");
        subMenu.add(item1);
        item1.addActionListener(new showOrdinaryCust());
        item6 = new JMenuItem("Privileged Customer");
        subMenu.add(item6);

        menu.add(subMenu);
        item2 = new JMenuItem("View Customers Who Didn't Pay");
        menu.add(item2);
        item3 = new JMenuItem("Remove Client");
        menu.add(item3);
        item4 = new JMenuItem("Create Order");
        menu.add(item4);
        item5 = new JMenuItem("Search...");
        menu.add(item5);
        setJMenuBar(menuBar);

    }

    public static void main(String[] args) {
        new GUImenu();
    }

    private class showOrdinaryCust implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == item1)

                GUImenu.main(null);

        }
    }
}