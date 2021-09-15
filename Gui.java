package Project;


import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;

public class Gui 
{

	wiki obj = new wiki();
	Trie trie;
	void init() 
	{
		try 
		{
			List<String> words = obj.getTerms();
			trie = new Trie(words);
		} 
		
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	private JFrame frame;
	private JTextField textField;
    List<String> suggestions;
    private JScrollPane scrollPane;
    private JTextArea textArea;
    private JScrollPane scrollPane_2;
    private JTextPane textPane;
    private JButton goButton;
    private JButton deleteButton;
    private JButton insertButton;
    private JButton infoButton;
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					Gui window = new Gui();
					window.frame.setVisible(true);
				} 
				
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Gui() 
	{
		initialize();
		init();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		frame = new JFrame("Auto Complete Feature");
		frame.setBounds(100, 100, 762, 630);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setFont(new Font("Open Sans", Font.PLAIN, 14));
		textField.setHorizontalAlignment(SwingConstants.LEFT);
		textField.addKeyListener(new KeyAdapter() 
		{
			public void keyReleased(KeyEvent e) 
			{
				String text = textField.getText().toLowerCase();
				textArea.setText("");
				if (!text.equals("")) 
				{
					suggestions = trie.search(text);
					for (String s : suggestions)
						textArea.setText(textArea.getText()+s+"\n");
				}
			}
		});
		textField.setBounds(108, 40, 414, 36);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(108, 94, 527, 121);
		frame.getContentPane().add(scrollPane);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setFont(new Font("Open Sans Semibold", Font.PLAIN, 14));
		textArea.setMargin(new Insets(10,10,10,10));
		scrollPane.setViewportView(textArea);
		
		goButton = new JButton("Go");
		goButton.setFont(new Font("Open Sans", Font.BOLD, 14));
		goButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String text = textField.getText();
				if (text.isBlank())
					return;
				try 
				{
					textPane.setText(obj.getInfo(text));
				} 
				
				catch (IOException e1) 
				{
					textPane.setText("Page not found (404)");
				}
			}
		});
		goButton.setBounds(561, 40, 74, 36);
		frame.getContentPane().add(goButton);
		
		scrollPane_2 = new JScrollPane();
		scrollPane_2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane_2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane_2.setBounds(23, 265, 691, 297);
		frame.getContentPane().add(scrollPane_2);
		
		textPane = new JTextPane();
		textPane.setMargin(new Insets(10,10,10,10));
		scrollPane_2.setViewportView(textPane);
		
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String word = textField.getText();
				if (word.isBlank())
					return;
				if (trie.delete(word.toLowerCase()))
					textPane.setText(word+" deleted successfully");
				else
					textPane.setText("Not deleted or not found");
			}
		});
		deleteButton.setBounds(312, 226, 105, 28);
		frame.getContentPane().add(deleteButton);
		
		insertButton = new JButton("Insert");
		insertButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String word = textField.getText();
				trie.insert(word.toLowerCase());
				if (!word.isBlank())
					textPane.setText(word+" added successfully");
			}
		});
		insertButton.setBounds(171, 226, 96, 28);
		frame.getContentPane().add(insertButton);
		
		infoButton = new JButton("More info");
		infoButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String text = textField.getText();
				if (text.isBlank())
					return;
				try 
				{
					textPane.setText(obj.moreInfo(text));
				} 
				
				catch (IOException e1) 
				{
					textPane.setText("Page not found (404)");
				}
			}
		});
		infoButton.setBounds(465, 226, 96, 28);
		frame.getContentPane().add(infoButton);
	}
}
