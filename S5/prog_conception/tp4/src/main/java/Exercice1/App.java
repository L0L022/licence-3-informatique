package Exercice1;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class App {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> run());
	}

	public static void run() {
		JFrame frame = new JFrame("demo");
		Truck truck = new Truck();
		frame.getContentPane().add(truck);
		frame.pack();
		frame.setVisible(true);
	}

}
