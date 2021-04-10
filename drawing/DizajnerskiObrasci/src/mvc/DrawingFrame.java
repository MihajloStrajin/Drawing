package mvc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import command.BringToFront;
import geometry.Shape;

import javax.swing.SwingConstants;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class DrawingFrame extends JFrame {
	
	DrawingView view = new DrawingView();
	DrawingController controller;
	
	private ButtonGroup group = new ButtonGroup();
	private ButtonGroup group2 = new ButtonGroup();
	
	private JButton innerColorBtn;
	private JButton outerColorBtn;
	private JButton bringToFrontBtn;
	private JButton bringToBackBtn;
	private JButton toFrontBtn;
	private JButton toBackBtn;
	private JButton loadBtn;

	private JToggleButton tglbtnSelect;
	private JToggleButton tglbtnPoint;
	private JToggleButton tglbtnLine;
	private JToggleButton tglbtnRectangle;
	private JToggleButton tglbtnCircle;
	private JToggleButton tglbtnDonut;
	private JButton tglbtnModify;
	private JButton tglbtnDelete;
	private JToggleButton tglbtnHexagon;
	private JButton undo;
	private JButton redo;
	
	JScrollPane logPane;
	JList list;
	DefaultListModel<String> dlm=new DefaultListModel<String>();
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem saveLog;
	private JMenuItem loadLog;
	private JMenuItem saveDrawing;
	private JMenuItem loadDrawing;
	
	/**
	 * 
	 */
	public DrawingFrame() {
		view.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				controller.mouseClicked(arg0);
			}
		});
		
		getContentPane().add(view, BorderLayout.CENTER);
		

		JToolBar toolBar = new JToolBar();
		getContentPane().add(toolBar, BorderLayout.NORTH);
		
		JToolBar toolBarColors = new JToolBar();
		toolBarColors.setOrientation(SwingConstants.VERTICAL);
		getContentPane().add(toolBarColors, BorderLayout.WEST);
		
		undo = new JButton("Undo");
		toolBar.add(undo);
		undo.setEnabled(false);
		undo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.undo();
			}
		});
		
		
		redo = new JButton("Redo");
		toolBar.add(redo);
		redo.setEnabled(false);
		redo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.redo();
			}
		});
		toolBar.add(Box.createHorizontalGlue());

		tglbtnSelect = new JToggleButton("Select");
		toolBar.add(tglbtnSelect);

		tglbtnPoint = new JToggleButton("Point");
		toolBar.add(tglbtnPoint);

		tglbtnLine = new JToggleButton("Line");
		toolBar.add(tglbtnLine);

		tglbtnRectangle = new JToggleButton("Rectangle");
		toolBar.add(tglbtnRectangle);

		tglbtnCircle = new JToggleButton("Circle");
		toolBar.add(tglbtnCircle);

		tglbtnDonut = new JToggleButton("Donut");
		toolBar.add(tglbtnDonut);
		
		tglbtnHexagon = new JToggleButton("Hexagon");
		toolBar.add(tglbtnHexagon);
		toolBar.add(Box.createHorizontalGlue());
		
		tglbtnModify = new JButton("Modify");
		tglbtnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.modify();
			}
		});
		toolBar.add(tglbtnModify);

		tglbtnDelete = new JButton("Delete");
		tglbtnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.delete();

			}
		});
		toolBar.add(tglbtnDelete);
		tglbtnModify.setEnabled(false);
		tglbtnDelete.setEnabled(false);

		group.add(tglbtnPoint);
		group.add(tglbtnCircle);
		group.add(tglbtnDelete);
		group.add(tglbtnDonut);
		group.add(tglbtnLine);
		group.add(tglbtnModify);
		group.add(tglbtnRectangle);
		group.add(tglbtnSelect);
		group.add(tglbtnHexagon);
		group.add(undo);
		group.add(redo);
		
		outerColorBtn = new JButton();
		outerColorBtn.setText("     ");
		outerColorBtn.setToolTipText("Outer color");
		outerColorBtn.setBackground(Color.black);
		toolBarColors.add(outerColorBtn);
		outerColorBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.changeOuterColor();

			}
		});
		
		innerColorBtn = new JButton();
		innerColorBtn.setText("     ");
		innerColorBtn.setToolTipText("Inner color");
		innerColorBtn.setBackground(Color.white);
		toolBarColors.add(innerColorBtn);
		innerColorBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.changeInnerColor();

			}
		});
		toolBarColors.add(Box.createVerticalGlue());
		
		toBackBtn = new JButton();
		toBackBtn.setText("One back");
		toBackBtn.setEnabled(false);
		toolBarColors.add(toBackBtn);
		toBackBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				controller.toBack();
			}
		});
		
		toFrontBtn = new JButton();
		toFrontBtn.setText("One front");
		toFrontBtn.setEnabled(false);
		toolBarColors.add(toFrontBtn);
		toFrontBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.toFront();
				
			}
		});
		
		bringToBackBtn = new JButton();
		bringToBackBtn.setText("Bring to back");
		bringToBackBtn.setEnabled(false);
		toolBarColors.add(bringToBackBtn);
		bringToBackBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.bringToBack();

			}
		});
		
		bringToFrontBtn = new JButton();
		bringToFrontBtn.setText("Bring to front");
		bringToFrontBtn.setEnabled(false);
		toolBarColors.add(bringToFrontBtn);
		bringToFrontBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.bringToFront();

			}
		});
		toolBarColors.add(Box.createVerticalGlue());
		
		loadBtn = new JButton();
		loadBtn.setText("LOAD");
		toolBarColors.add(loadBtn);
		loadBtn.setEnabled(false); 
		loadBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.loadOneByOne();

			}
		});
		
		
		group2.add(outerColorBtn);
		group2.add(innerColorBtn);
		group2.add(bringToFrontBtn);
		group2.add(bringToBackBtn);
		group2.add(toFrontBtn);
		group2.add(toBackBtn);
		group2.add(loadBtn);
		
		logPane = new JScrollPane();
		getContentPane().add(logPane,BorderLayout.SOUTH);
		
		list = new JList();
		logPane.setColumnHeaderView(list);
		list.setModel(dlm);
		logPane.setViewportView(list);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		saveLog = new JMenuItem("Save log");
		fileMenu.add(saveLog);
		saveLog.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.saveLog();
				
			}
		});
		
		loadLog = new JMenuItem("Load log");
		fileMenu.add(loadLog);
		loadLog.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.loadLog();
				
			}
		});
		
		saveDrawing = new JMenuItem("Save drawing");
		fileMenu.add(saveDrawing);
		saveDrawing.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					controller.saveDrawing(controller.getModel().getShapes());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		loadDrawing = new JMenuItem("Load drawing");
		fileMenu.add(loadDrawing);
		loadDrawing.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					controller.loadDrawing();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	

	public JButton getUndo() {
		return undo;
	}



	public JButton getRedo() {
		return redo;
	}



	public DrawingView getView() {
		return view;
	}

	public void setController(DrawingController controller) {
		this.controller = controller;
	}
	
	public JToggleButton getTglbtnSelect() {
		return tglbtnSelect;
	}

	public JToggleButton getTglbtnPoint() {
		return tglbtnPoint;
	}

	public JToggleButton getTglbtnLine() {
		return tglbtnLine;
	}

	public JToggleButton getTglbtnRectangle() {
		return tglbtnRectangle;
	}
 
	public JToggleButton getTglbtnCircle() {
		return tglbtnCircle;
	}

	public JToggleButton getTglbtnDonut() {
		return tglbtnDonut;
	}

	public JButton getTglbtnModify() {
		return tglbtnModify;
	}

	public JButton getTglbtnDelete() {
		return tglbtnDelete;
	}

	public JToggleButton getTglbtnHexagon() {
		return tglbtnHexagon;
	}



	public JButton getInnerColorBtn() {
		return innerColorBtn;
	}



	public void setInnerColorBtn(JButton innerColorBtn) {
		this.innerColorBtn = innerColorBtn;
	}



	public JButton getOuterColorBtn() {
		return outerColorBtn;
	}



	public void setOuterColorBtn(JButton outerColorBtn) {
		this.outerColorBtn = outerColorBtn;
	}



	public JButton getLoadBtn() {
		return loadBtn;
	}



	public JButton getBringToFrontBtn() {
		return bringToFrontBtn;
	}



	public JButton getBringToBackBtn() {
		return bringToBackBtn;
	}



	public JButton getToFrontBtn() {
		return toFrontBtn;
	}



	public JButton getToBackBtn() {
		return toBackBtn;
	}



	public DefaultListModel<String> getDlm() {
		return dlm;
	}

	
	
	
	
	
}
