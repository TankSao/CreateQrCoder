package cn.gd.pl.lan.main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import com.swetake.util.Qrcode;

public class QRGenerator {
	
	private static JFrame mainf;
	private static JPanel pannel;
	private static JTextArea input;
	private static ImagePannel output;
	private static JButton generat;
	private static GridBagLayout gbl;
	private static GridBagConstraints gbc;
	private static Graphics2D gs;
	private static BufferedImage bi;

	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
	    JFrame.setDefaultLookAndFeelDecorated( true );
	    
		initUI();
	}

	public static void initUI(){
		gbl = new GridBagLayout();
		gbc = new GridBagConstraints();
		mainf = new JFrame("二维码生成器");
		mainf.setIconImage(Toolkit.getDefaultToolkit().getImage(QRGenerator.class.getResource("/resources/fac.png")));
		pannel = new JPanel(gbl);
		input = new JTextArea();
		output = new ImagePannel();
		generat = new JButton();
		
		generat.setText("生成二维码");
		generat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!"".equals(input.getText().trim()) && !(input.getText() == null)){
				bi = new BufferedImage(275, 275, BufferedImage.TYPE_INT_RGB);
				for(int i = 0 ; i < 275 ; i++)
					for(int j = 0 ; j < 275 ; j++)
						bi.setRGB(j, i, Color.WHITE.getRGB());
				gs = bi.createGraphics();
				gs.setColor(Color.BLACK);
				Qrcode qrcode = new Qrcode();
				qrcode.setQrcodeEncodeMode('B');//N A ...
				qrcode.setQrcodeErrorCorrect('M');//L M Q H
				qrcode.setQrcodeVersion(7);
				if((input.getText().trim().getBytes()).length > 0 && (input.getText().trim().getBytes()).length < 123){
				boolean[][] rest = qrcode.calQrcode(input.getText().trim().getBytes()); 
				for(int i = 0 ; i< rest.length ; i++){
					for(int j = 0 ; j < rest.length ; j++){
						if(rest[j][i])
							gs.fillRect(j * 6, i * 6, 6, 6);
					}
				}
				output.setBufferedImage(bi);
				output.repaint();
				File f = new File("c:\\qrcode.png");
				try {
					ImageIO.write(bi, "png", f);
					JOptionPane.showMessageDialog(mainf, new String("恭喜您，2维码生成成功咯！"), "结果提示", JOptionPane.INFORMATION_MESSAGE);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(mainf, new String("很遗憾，2维码生成失败了唉！"), "结果提示", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
				}else{
					JOptionPane.showMessageDialog(mainf, new String("编码内容长度不能超过123个字符哦，亲！"), "输入提示", JOptionPane.WARNING_MESSAGE);
				}
			}else
				JOptionPane.showMessageDialog(mainf, new String("编码内容不能为空哦！"), "输入提示", JOptionPane.WARNING_MESSAGE);
			}
		});
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.BOTH;
		input.setLineWrap(true);
		input.setToolTipText("在此输入内容 :)");
		gbl.setConstraints(input, gbc);
		pannel.add(input);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.NONE;
		generat.setToolTipText("点击我生成二维码 :D");
		gbc.gridwidth = GridBagConstraints.RELATIVE;
		gbl.setConstraints(generat, gbc);
		pannel.add(generat);
		
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.weightx = 265;//分配额外的水平空间
		gbc.weighty = 1;
		gbc.ipady = 100;
		output.setBackground(Color.darkGray);
		output.setToolTipText("二维码生成区域");
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbl.setConstraints(output, gbc);
		pannel.add(output);
		
		mainf.setResizable(false);
		mainf.setBounds(350, 300, 550, 305);
		mainf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainf.add(pannel);
		mainf.setVisible(true);
	}
	
}
