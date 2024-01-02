import javax.swing.*;


import java.awt.*;


import java.awt.event.*;


class Test extends JFrame
{
    JButton b;

	public Test()
	{
		createAndShowGUI();
	}


	private void createAndShowGUI()
	{
		setTitle("Block input");
		setLayout(new FlowLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);


		// Create JButton
		b=new JButton("Block input");
		add(b);

		b.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae)
			{
				// Create a JPanel with semi-transparent
				// black background

				// This will be glass pane
				JPanel p=new JPanel(){
					public void paintComponent(Graphics g)


					{
						g.setColor(new Color(0,0,0,140));

						g.fillRect(0,0,getWidth(),getHeight());
					}


				};		


				// Set it non-opaque
				p.setOpaque(false);

				// Set layout to JPanel
				p.setLayout(new GridBagLayout());				


				// Add the jlabel with the image icon
				// p.add(new JLabel(new ImageIcon("loading.gif")));


				// Take glass pane
				setGlassPane(p);


				


				// Add MouseListener
				p.addMouseListener(new MouseAdapter(){
					public void mousePressed(MouseEvent me)
					{
						// Consume the event, now the input is blocked
						me.consume();

						// Create beep sound, when mouse is pressed
						Toolkit.getDefaultToolkit().beep();
					}
				});

				


				// Make it visible, it isn't by default because


				// it is set as glass pane
				p.setVisible(true);
			}
		});

		setSize(400,400);
		setVisible(true);
		setLocationRelativeTo(null);
	}


	public static void main(String args[])
	{
		new Test();
	}


}
