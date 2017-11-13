package com.inspur.luke.product.controller;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * 功能描述: 
 * 创建人: zhangguilu
 * 创建时间:2017年8月1日 下午2:34:29
 */
public class FrameOper {
	private JFrame jf;
	private JButton compute;
	private JTextField typeField;
	private JTextField numField;
	private JTextField errorLimit;
	private JTextField maxErrorLimit;
	private JTextArea textArea;
	private JButton clear;
	private JLabel type;
	private JLabel num;
	private JLabel error;
	private JLabel maxError;
	
//	private JDialog dialog;
//	private JButton okBut;
//	private JLabel label;
	
	FrameOper() {
		init();
	}
	
	private void init() {
		jf = new JFrame("下料计算");
		jf.setLayout(new GridBagLayout());
		
		compute = new JButton("计算");
		clear = new JButton("清空");
		
		typeField = new JTextField(36);
		numField = new JTextField(36);
		errorLimit = new JTextField(36);
		maxErrorLimit = new JTextField(36);
		
		type = new JLabel("尺寸:");
		num = new JLabel("数量:");
		error = new JLabel("起始误差:");
		maxError = new JLabel("最大误差:");
		
		GridBagConstraints gbc=new GridBagConstraints();    
        //设定button的属性值    
        gbc.gridx=0;  //横坐标  
        gbc.gridy=0;  //纵坐标  
        gbc.gridwidth=1;  //组件宽  
        gbc.gridheight=1;  //组件高  
        gbc.weightx=0;  //行的权重，通过这个属性来决定如何分配行的剩余空间  
        gbc.anchor=GridBagConstraints.CENTER;   
         //当组件在格内而不能撑满其格时，通过fill的值来设定填充方式，有四个值  
        gbc.fill=GridBagConstraints.NONE;    
        gbc.insets=new Insets(0,20,10,0);    
        gbc.ipadx=5;  //组件间的横向间距  
        gbc.ipady=20;  //组件间的纵向间距  
        jf.add(type,gbc);
        
        gbc.gridx=1;  //横坐标  
        gbc.gridy=0;  //纵坐标  
        gbc.gridwidth=10;  //组件宽  
        gbc.gridheight=1;  //组件高  
        gbc.weightx=100;  //行的权重，通过这个属性来决定如何分配行的剩余空间  
        gbc.anchor=GridBagConstraints.WEST;   
         //当组件在格内而不能撑满其格时，通过fill的值来设定填充方式，有四个值  
        gbc.fill=GridBagConstraints.HORIZONTAL;    
        gbc.insets=new Insets(0,0,10,20);    
//        gbc.ipadx=0;  //组件间的横向间距  
//        gbc.ipady=20;  //组件间的纵向间距  
        jf.add(typeField,gbc);
//        typeField.addKeyListener(new KeyAdapter(){
//        	public void keyPressed(KeyEvent e) {
//        		int code = e.getKeyCode();
//        		if(!(code >= KeyEvent.VK_0 && code <= KeyEvent.VK_9) && code != KeyEvent.VK_COMMA) {
//        			dialog = new JDialog(jf,"错误",true);
//        			dialog.setBounds(400, 200, 300, 160);
//        			dialog.setLayout(new FlowLayout());
//        			label = new JLabel("型号只可以输入数字，并以逗号隔开");
//        			okBut = new JButton("确定");
//        			dialog.add(label);
//        			dialog.add(okBut);
//        			dialog.setVisible(true);
//        			e.consume();
//        		}
//        	}
//        });
        
        
      //设定button的属性值    
        gbc.gridx=0;  //横坐标  
        gbc.gridy=1;  //纵坐标  
        gbc.gridwidth=1;  //组件宽  
        gbc.gridheight=1;  //组件高  
        gbc.weightx=10;  //行的权重，通过这个属性来决定如何分配行的剩余空间  
        gbc.weighty=0;    
        gbc.anchor=GridBagConstraints.CENTER;   
         //当组件在格内而不能撑满其格时，通过fill的值来设定填充方式，有四个值  
        gbc.fill=GridBagConstraints.NONE;    
        gbc.insets=new Insets(0,20,10,0);    
        jf.add(num,gbc);
        
        gbc.gridx=1;  //横坐标  
        gbc.gridy=1;  //纵坐标  
        gbc.gridwidth=10;  //组件宽  
        gbc.gridheight=1;  //组件高  
        gbc.weightx=90;  //行的权重，通过这个属性来决定如何分配行的剩余空间  
        gbc.weighty=0;    
        gbc.anchor=GridBagConstraints.CENTER;   
         //当组件在格内而不能撑满其格时，通过fill的值来设定填充方式，有四个值  
        gbc.fill=GridBagConstraints.HORIZONTAL;    
        gbc.insets=new Insets(0,0,10,20);    
        jf.add(numField,gbc);
        
      //设定误差起始值    
        gbc.gridx=0;  //横坐标  
        gbc.gridy=2;  //纵坐标  
        gbc.gridwidth=1;  //组件宽  
        gbc.gridheight=1;  //组件高  
        gbc.weightx=10;  //行的权重，通过这个属性来决定如何分配行的剩余空间  
        gbc.weighty=0;    
        gbc.anchor=GridBagConstraints.WEST;   
         //当组件在格内而不能撑满其格时，通过fill的值来设定填充方式，有四个值  
        gbc.fill=GridBagConstraints.NONE;    
        gbc.insets=new Insets(0,20,10,0);    
        jf.add(error,gbc);
        
        gbc.gridx=1;  //横坐标  
        gbc.gridy=2;  //纵坐标  
        gbc.gridwidth=1;  //组件宽  
        gbc.gridheight=1;  //组件高  
        gbc.weightx=90;  //行的权重，通过这个属性来决定如何分配行的剩余空间  
        gbc.weighty=0;    
        gbc.anchor=GridBagConstraints.CENTER;   
         //当组件在格内而不能撑满其格时，通过fill的值来设定填充方式，有四个值  
        gbc.fill=GridBagConstraints.HORIZONTAL;    
        gbc.insets=new Insets(0,0,10,20);    
        jf.add(errorLimit,gbc);
        
      //设定误差起始值    
        gbc.gridx=2;  //横坐标  
        gbc.gridy=2;  //纵坐标  
        gbc.gridwidth=1;  //组件宽  
        gbc.gridheight=1;  //组件高  
        gbc.weightx=10;  //行的权重，通过这个属性来决定如何分配行的剩余空间  
        gbc.weighty=0;    
        gbc.anchor=GridBagConstraints.EAST;   
         //当组件在格内而不能撑满其格时，通过fill的值来设定填充方式，有四个值  
        gbc.fill=GridBagConstraints.NONE;    
        gbc.insets=new Insets(0,20,10,0);    
        jf.add(maxError,gbc);
        
        gbc.gridx=3;  //横坐标  
        gbc.gridy=2;  //纵坐标  
        gbc.gridwidth=1;  //组件宽  
        gbc.gridheight=1;  //组件高  
        gbc.weightx=90;  //行的权重，通过这个属性来决定如何分配行的剩余空间  
        gbc.weighty=0;    
        gbc.anchor=GridBagConstraints.CENTER;   
         //当组件在格内而不能撑满其格时，通过fill的值来设定填充方式，有四个值  
        gbc.fill=GridBagConstraints.HORIZONTAL;    
        gbc.insets=new Insets(0,0,10,20);    
        jf.add(maxErrorLimit,gbc);
        
        
      //设定button的属性值    
        gbc.gridx=1;  //横坐标  
        gbc.gridy=3;  //纵坐标  
        gbc.gridwidth=1;  //组件宽  
        gbc.gridheight=1;  //组件高  
        gbc.anchor=GridBagConstraints.CENTER;   
         //当组件在格内而不能撑满其格时，通过fill的值来设定填充方式，有四个值  
        gbc.fill=GridBagConstraints.NONE;    
        gbc.insets=new Insets(0,0,0,0);    
        gbc.ipadx=5;  //组件间的横向间距  
        gbc.ipady=8;  //组件间的横向间距 
        jf.add(clear,gbc);
        clear.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				typeField.setText("");
				numField.setText("");
				textArea.setText("");
			}
		});
        
        gbc.gridx=3;  //横坐标  
        gbc.gridy=3;  //纵坐标  
        gbc.gridwidth=1;  //组件宽  
        gbc.gridheight=1;  //组件高  
        gbc.anchor=GridBagConstraints.CENTER;   
         //当组件在格内而不能撑满其格时，通过fill的值来设定填充方式，有四个值  
        gbc.fill=GridBagConstraints.NONE;    
        gbc.insets=new Insets(0,0,0,20);    
        jf.add(compute,gbc);
        
        textArea = new JTextArea();
        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setHorizontalScrollBarPolicy(
		JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(
		JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        gbc.gridx=0;  //横坐标  
        gbc.gridy=4;  //纵坐标  
        gbc.gridwidth=10;  //组件宽  
        gbc.weightx=10;  //行的权重，通过这个属性来决定如何分配行的剩余空间  
        gbc.weighty=1;    
        gbc.anchor=GridBagConstraints.CENTER;   
         //当组件在格内而不能撑满其格时，通过fill的值来设定填充方式，有四个值  
        gbc.fill=GridBagConstraints.BOTH;    
        gbc.insets=new Insets(20,20,20,20);    
        jf.add(scroll,gbc);
        
        compute.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
				if("".equals(typeField.getText())) {
					textArea.setText("型号不能为空，且型号只可以输入数字，并以英文逗号隔开！");
					return;
				} else if("".equals(numField.getText())) {
					textArea.setText("型号数量不能为空，且数量只可以输入数字，并以英文逗号隔开！");
					return;
				}
				
				String[] types = typeField.getText().split(",");
				String[] typeNums = numField.getText().split(",");
				String startError = errorLimit.getText();
				String maxError = maxErrorLimit.getText();
				
				int length = types.length;
				
				if(length != typeNums.length) {
					textArea.setText("型号和数量不对应，请检查输入参数！");
					return;
				}
				
				int[] type = new int[length];
				int[] typeNum = new int[length];
				for(int i=0;i<length;i++) {
					type[i] = Integer.parseInt(types[i]);
					typeNum[i] = Integer.parseInt(typeNums[i]);
				}
				
				Map<String,Object> map = new HashMap<String,Object>();
				OptimiseUtils optimiseUtil = new OptimiseUtils();
				StringBuilder sb = new StringBuilder();
				int initError = 1;
				if(!"".equals(startError)) {
					initError = Integer.parseInt(startError);
				}
				int finalError = 51;
				if(!"".equals(maxError)) {
					finalError = Integer.parseInt(maxError);
				}
				map = optimiseUtil.getOptimiseMethod(initError,typeNum,type);
				typeNum = (int[])map.get("typeNum");
				type = (int[])map.get("type");
				sb.append((String)map.get("comMethod"));
				
				int error = initError;
				boolean flag = true;
				while(flag) {
					int num = 0;
					for(int i=0;i<typeNum.length;i++) {
						if(typeNum[i] != 0) {
							num++;
						}
					}
					if(num != 0) {
						int[] typeNum1 = new int[num];
						int[] type1 = new int[num];
						int j = 0;
						for(int i=0;i<typeNum.length;i++) {
							if(typeNum[i] != 0) {
								typeNum1[j] = typeNum[i];
								type1[j] = type[i];
								j++;
							}
						}
						map = optimiseUtil.getOptimiseMethod(error,typeNum1,type1);
						if(map == null) {
							error+=10;
//							map = optimiseUtil.getOptimiseMethod(error,typeNum1,type1);
							if(error > finalError) {
								flag = false;
								break;
							}
						} else {
							typeNum = (int[])map.get("typeNum");
							type = (int[])map.get("type");
							sb.append((String)map.get("comMethod"));
						}
					} else {
						break;
					}
				}
				textArea.append("各型号剩余数量：");
				for(int i=0;i<type.length;i++) {
					textArea.append("\n");
					textArea.append("长度"+type[i]+"剩余数量：" + typeNum[i]);
				}
				textArea.append("\n");
				textArea.append(sb.toString());
			}
		});
        
        jf.pack();    
        jf.setBounds(380, 0, 600, 700);
        jf.setVisible(true);    
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        
		
	}
	
	public static void main(String[] args) {
		new FrameOper();
//		new OptimiseUtils().getTheBestMethods();
	}
}
