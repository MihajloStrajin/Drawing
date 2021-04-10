package drawing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import geometry.Point;
import geometry.Rectangle;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DlgRectangle extends JDialog {

	private final JPanel contentPanel = new JPanel();
	Color outLineColor;
	Color innerColor;
	Rectangle rect;
	public boolean isOk;
	private JTextField txtUpperLeftPointX;
	private JTextField txtUpperLeftPointY;
	private JTextField txtWidth;
	private JTextField txtHeight;
	private JButton btnOutlineColor;
	private JButton btnInnerColor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgRectangle dialog = new DlgRectangle();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgRectangle() {
		setTitle("DlgRectangle");
		setBounds(100, 100, 450, 402);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblUpperLeftPointX = new JLabel("Upper Left Point X");
			GridBagConstraints gbc_lblUpperLeftPointX = new GridBagConstraints();
			gbc_lblUpperLeftPointX.insets = new Insets(0, 0, 5, 5);
			gbc_lblUpperLeftPointX.anchor = GridBagConstraints.EAST;
			gbc_lblUpperLeftPointX.gridx = 0;
			gbc_lblUpperLeftPointX.gridy = 0;
			contentPanel.add(lblUpperLeftPointX, gbc_lblUpperLeftPointX);
		}
		{
			txtUpperLeftPointX = new JTextField();
			GridBagConstraints gbc_txtUpperLeftPointX = new GridBagConstraints();
			gbc_txtUpperLeftPointX.insets = new Insets(0, 0, 5, 0);
			gbc_txtUpperLeftPointX.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtUpperLeftPointX.gridx = 1;
			gbc_txtUpperLeftPointX.gridy = 0;
			contentPanel.add(txtUpperLeftPointX, gbc_txtUpperLeftPointX);
			txtUpperLeftPointX.setColumns(10);
		}
		{
			JLabel lblUpperLeftPoint = new JLabel("Upper Left Point Y");
			GridBagConstraints gbc_lblUpperLeftPoint = new GridBagConstraints();
			gbc_lblUpperLeftPoint.anchor = GridBagConstraints.EAST;
			gbc_lblUpperLeftPoint.insets = new Insets(0, 0, 5, 5);
			gbc_lblUpperLeftPoint.gridx = 0;
			gbc_lblUpperLeftPoint.gridy = 2;
			contentPanel.add(lblUpperLeftPoint, gbc_lblUpperLeftPoint);
		}
		{
			txtUpperLeftPointY = new JTextField();
			GridBagConstraints gbc_txtUpperLeftPointY = new GridBagConstraints();
			gbc_txtUpperLeftPointY.insets = new Insets(0, 0, 5, 0);
			gbc_txtUpperLeftPointY.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtUpperLeftPointY.gridx = 1;
			gbc_txtUpperLeftPointY.gridy = 2;
			contentPanel.add(txtUpperLeftPointY, gbc_txtUpperLeftPointY);
			txtUpperLeftPointY.setColumns(10);
		}
		{
			JLabel lblWidth = new JLabel("Width");
			GridBagConstraints gbc_lblWidth = new GridBagConstraints();
			gbc_lblWidth.insets = new Insets(0, 0, 5, 5);
			gbc_lblWidth.gridx = 0;
			gbc_lblWidth.gridy = 4;
			contentPanel.add(lblWidth, gbc_lblWidth);
		}
		{
			txtWidth = new JTextField();
			GridBagConstraints gbc_txtWidth = new GridBagConstraints();
			gbc_txtWidth.insets = new Insets(0, 0, 5, 0);
			gbc_txtWidth.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtWidth.gridx = 1;
			gbc_txtWidth.gridy = 4;
			contentPanel.add(txtWidth, gbc_txtWidth);
			txtWidth.setColumns(10);
		}
		{
			JLabel lblHeight = new JLabel("Height");
			GridBagConstraints gbc_lblHeight = new GridBagConstraints();
			gbc_lblHeight.insets = new Insets(0, 0, 5, 5);
			gbc_lblHeight.gridx = 0;
			gbc_lblHeight.gridy = 6;
			contentPanel.add(lblHeight, gbc_lblHeight);
		}
		{
			txtHeight = new JTextField();
			GridBagConstraints gbc_txtHeight = new GridBagConstraints();
			gbc_txtHeight.insets = new Insets(0, 0, 5, 0);
			gbc_txtHeight.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtHeight.gridx = 1;
			gbc_txtHeight.gridy = 6;
			contentPanel.add(txtHeight, gbc_txtHeight);
			txtHeight.setColumns(10);
		}
		{
			JLabel lblOutLineColor = new JLabel("OutLine Color");
			GridBagConstraints gbc_lblOutLineColor = new GridBagConstraints();
			gbc_lblOutLineColor.insets = new Insets(0, 0, 5, 5);
			gbc_lblOutLineColor.gridx = 0;
			gbc_lblOutLineColor.gridy = 8;
			contentPanel.add(lblOutLineColor, gbc_lblOutLineColor);
		}
		{
			btnOutlineColor = new JButton("OutLine Color");
			btnOutlineColor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					outLineColor=JColorChooser.showDialog(null, "Select color", btnOutlineColor.getBackground());
					if(outLineColor!=null)btnOutlineColor.setBackground(outLineColor);
				}
			});
			btnOutlineColor.setBackground(Color.black);
			GridBagConstraints gbc_btnOutlineColor = new GridBagConstraints();
			gbc_btnOutlineColor.fill = GridBagConstraints.HORIZONTAL;
			gbc_btnOutlineColor.insets = new Insets(0, 0, 5, 0);
			gbc_btnOutlineColor.gridx = 1;
			gbc_btnOutlineColor.gridy = 8;
			contentPanel.add(btnOutlineColor, gbc_btnOutlineColor);
		}
		{
			JLabel lblInnerColor = new JLabel("Inner Color");
			GridBagConstraints gbc_lblInnerColor = new GridBagConstraints();
			gbc_lblInnerColor.insets = new Insets(0, 0, 0, 5);
			gbc_lblInnerColor.gridx = 0;
			gbc_lblInnerColor.gridy = 10;
			contentPanel.add(lblInnerColor, gbc_lblInnerColor);
		}
		{
			btnInnerColor = new JButton("Inner Color");
			btnInnerColor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					innerColor=JColorChooser.showDialog(null, "Select color", btnInnerColor.getBackground());
					if(innerColor!=null)btnInnerColor.setBackground(innerColor);
				}
			});
			btnInnerColor.setBackground(Color.white);
			GridBagConstraints gbc_btnInnerColor = new GridBagConstraints();
			gbc_btnInnerColor.fill = GridBagConstraints.HORIZONTAL;
			gbc_btnInnerColor.gridx = 1;
			gbc_btnInnerColor.gridy = 10;
			contentPanel.add(btnInnerColor, gbc_btnInnerColor);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(txtUpperLeftPointX.getText().trim().isEmpty() || txtUpperLeftPointY.getText().trim().isEmpty()||
								txtWidth.getText().trim().isEmpty() || txtHeight.getText().trim().isEmpty()){
							JOptionPane.showMessageDialog(null, "Please fill all fields");
							isOk = false;
						}else{
							try {
								if (Integer.parseInt(txtWidth.getText()) < 0
										|| Integer.parseInt(txtHeight.getText()) < 0) {
									JOptionPane.showMessageDialog(null, "Height and Width must be greater then 0");
									isOk=false;
								} else {
									isOk = true;

									rect = new Rectangle(
											new Point(Integer.parseInt(txtUpperLeftPointX.getText().toString()),
													Integer.parseInt(txtUpperLeftPointY.getText().toString())),
											Integer.parseInt(txtHeight.getText().toString()),
											Integer.parseInt(txtWidth.getText().toString()),false,btnOutlineColor.getBackground(),btnInnerColor.getBackground());

									setVisible(false);
								}
							} catch (NumberFormatException e1) {
								JOptionPane.showMessageDialog(null, "Values must be numbers");

							}
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}

	public JTextField getTxtUpperLeftPointX() {
		return txtUpperLeftPointX;
	}

	public void setTxtUpperLeftPointX(JTextField txtUpperLeftPointX) {
		this.txtUpperLeftPointX = txtUpperLeftPointX;
	}

	public JTextField getTxtUpperLeftPointY() {
		return txtUpperLeftPointY;
	}

	public void setTxtUpperLeftPointY(JTextField txtUpperLeftPointY) {
		this.txtUpperLeftPointY = txtUpperLeftPointY;
	}

	public JTextField getTxtWidth() {
		return txtWidth;
	}

	public void setTxtWidth(JTextField txtWidth) {
		this.txtWidth = txtWidth;
	}

	public JTextField getTxtHeight() {
		return txtHeight;
	}

	public void setTxtHeight(JTextField txtHeight) {
		this.txtHeight = txtHeight;
	}

	public JButton getBtnOutlineColor() {
		return btnOutlineColor;
	}

	public JButton getBtnInnerColor() {
		return btnInnerColor;
	}

}
