package com.inspur.luke.product.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author hgl11
 *
 * version:1.0 
 *
 * date:2017年7月11日,下午11:12:16
 *
 */
public class GaUtils {
	
	private static int ChrNum = 2000;//初始化20个个体
	private int bestfitness = Integer.MAX_VALUE;  //函数最优解
	private int maxGeneration = 1500;  //函数最优解
	private int[] beststr;   		//最优解的染色体的二进制码
	private int besthis = 0;
	private int[][] maxNum;
	private int[] type;//误差数组
	private int[] typeNum;//各型号数量
	private int[][] b;//每种方法的用量
	private int[][] x;//ChrNum个个体

	public int[] getResult(int[][] b,int[] typeNum,int[] type) {
		this.type = type;
		this.typeNum = typeNum;
		this.b = b;
		int num = b[0].length;
		if(num == 0)
			return null;
		ChrNum = getMaxChrNum();
		maxNum = new int[ChrNum][num];
		beststr = new int[num];
		//首先根据B和typeNum初始化族群
		x = new int[ChrNum][num];
		int index = 0;
		Map<String,int[]> map = new HashMap<String,int[]>();
		while(index < ChrNum) {
			map = init();
			x[index] = map.get("data");
			maxNum[index] = map.get("dataMax");
			index++;
		}
		//复制
		int time = 0;
		int mit =10;
		while(time < maxGeneration) {
			select();
			cross();
			double a = 1 - Math.pow(time/maxGeneration,mit);
			mutation(1 - Math.pow(Math.random(), a));
			time++;
		}
//		System.out.println("最优解为："+bestfitness);
//		System.out.println("历史最优解为："+besthis);
		return beststr;
		
		
	}

	/**
	 * @param b
	 * @param typeNum
	 */
	private Map<String,int[]> init() {
		Map<String,int[]> map = new HashMap<String,int[]>();
		int length = b[0].length;
		int[] singlex = new int[length];
		int[] singlexMax = new int[length];
		List<Integer> tempNum = new ArrayList<Integer>();
		for(int i=0;i<typeNum.length;i++) {
			tempNum.add(typeNum[i]);
		}
		boolean flag = true;
		//计算xi-xn的允许最大值
		while(flag) {
			for(int i=0;i<b[0].length;i++) {
				int temp = 10000;
				for(int j=0;j<b.length;j++) {
					if(b[j][i] != 0) {
						int val = tempNum.get(j)/b[j][i];
						if(val < temp) {
							temp = val;
						}
					}
				}
				singlexMax[i] = temp;
				singlex[i] = (int) Math.round(Math.random() * temp);
				for(int j=0;j<b.length;j++) {
					if(b[j][i] != 0) {
						tempNum.set(j, tempNum.get(j) - singlex[i] * b[j][i]);
					}
				}
			}
			
			flag = !getXn(singlex);
		}
		map.put("data", singlex);
		map.put("dataMax", singlexMax);
		return map;
	}
	
	/**
	 * 轮盘选择
	 * 计算群体上每个个体的适应度值; 
	 * 按由个体适应度值所决定的某个规则选择将进入下一代的个体;
	 */
	private void select() {
		int evals[] = new int[ChrNum]; // 所有染色体适应值
		double p[] = new double[ChrNum]; // 各染色体选择概率
		double q[] = new double[ChrNum]; // 累计概率
		double F = 0; // 累计适应值总和
		for (int i = 0; i < ChrNum; i++) {
			evals[i] = calculatefitnessvalue(x[i]);
			if (evals[i] < bestfitness) {  // 记录下种群中的最小值，即最优解
				bestfitness = evals[i];
				if(besthis > bestfitness) {
					besthis = bestfitness;
				}
				beststr = x[i];
			} else {
				evals[i] = 0;
			}

			F = F + evals[i]; // 所有染色体适应值总和
		}
		
		//对适应度进行排序，并对应好原来的序号
		int[] index = new int[ChrNum];
		for (int i = 0; i < ChrNum; i++) {
			p[i] = evals[i] / F;
			index[i] = i;
		}
		for(int i=0;i<ChrNum-1;i++) {
			for(int j=0;j<ChrNum-i-1;j++){
				if(p[j] > p[j+1]) {
					swap(p,j);
					swap(index,j);
				}
			}
		}
		//获取和
		for (int i = 0; i < ChrNum; i++) {
			if (i == 0)
				q[i] = p[i];
			else {
				q[i] = q[i - 1] + p[i];
			}
		}
		for (int i = 0; i < ChrNum; i++) {
			double r = Math.random();
			if (r <= q[0]) {
				x[i] = x[0];
			} else {
				for (int j = 1; j < ChrNum; j++) {
					if (r < q[j]) {
						x[i] = x[j];
						break;
					}
				}
			}
		}
	}
	
	/**
	 * 将染色体转换成x,y变量的值
	 */
	private int calculatefitnessvalue(int[] chr) {

		int fitness = 0;
		int[] temp = new int[typeNum.length];
		for(int t=0;t<typeNum.length;t++) {
			temp[t] = typeNum[t];
		}
		for(int i=0;i<chr.length;i++) {
			if(chr[i] != 0) {
				for(int j=0;j<b.length;j++) {
					if(b[j][i] != 0) {
						temp[j] = temp[j] - b[j][i]*chr[i];
					}
				}
			}
		}
//		for(int i=0;i<chr.length;i++) {
//			fitness += (-5980) * chr[i];
//			fitness += -chr[i];
//		}
//		for(int i=0;i<temp.length;i++) {
//			fitness += temp[i] * type[i];
//		}
		for(int i=0;i<temp.length;i++) {
			fitness += temp[i];
		}
		//需优化的函数
		return fitness;
	}
	/**
	 * 交换
	 */
	private void cross() {
		int length = x.length;
		int n = x[0].length;
		for(int i=0;i<length-1;i++) {
			boolean flag = false;
			int[] index = getIndex(x[i]);
			if(index != null) {
				for(int j=0;j<index.length;j++) {
					x[i][j]++;
					flag = isProper(x[i]);
					if(!flag) {
						x[i][j]--;
					}
				}
			} else {
				int position = generateRandomInt(n);
				for(int j=position;j<n;j++) {
					x[i][j]++;
					flag = isProper(x[i]);
					if(!flag) {
						x[i][j]--;
					}
				}
			}
		}
			
	}
	/**
	 * 突变
	 */
	private void mutation(double delta) {
		int n = x[0].length;
		for(int i=0;i<x.length;i++) {
			boolean flag = false;
			int ti = 0;
			int[] index = getIndex(x[i]);
			int maxInter = 1000;
			while(!flag) {
				int position = 0;
				if(n > 1) {
					if(index != null) {
						position = index[ti];
						maxInter = index.length;
					} else {
						position = generateRandomInt(n-1);
					}
				}
				int temp = x[i][position];
				x[i][position] = (int) (x[i][position] + maxNum[i][position] * delta);
				flag = isProper(x[i]);
				if(!flag) {
					x[i][position] = temp;
				}
				ti++;
				if(ti >= maxInter) {
					break;
				}
			}
		}
	}
	
	private void swap(int[] type,int j) {
		int temp = type[j];
		type[j] = type[j+1];
		type[j+1] = temp;
	}
	private void swap(double[] type,int j) {
		double temp = type[j];
		type[j] = type[j+1];
		type[j+1] = temp;
	}
	
	/**
	 * 生成1-n-1的均匀分布随机数
	 */
	private int generateRandomInt(int n){
		return new Random().nextInt(n)+1;
	}
	/**
	 * 根据其他方法的量获取xn的值
	 */
	private boolean getXn(int[] singlex) {
		boolean flag = true;
		int temp = 10000;
		//获取xn
		for(int i=0;i<b.length;i++) {
			int sum = 0;
			for(int j=0;j<b[0].length-1;j++) {
				if(b[i][j] != 0) {
					sum += singlex[j]*b[i][j];
				}
			}
			if(typeNum[i] < sum) {
				flag = false;
				break;
			}
			if(b[i][b[0].length-1] != 0) {
				singlex[b[0].length-1] = (typeNum[i] - sum)/b[i][b[0].length-1];
				if(singlex[b[0].length-1] < temp) {
					temp = singlex[b[0].length-1];
				}
			}
		}
		if(flag) {
			singlex[b[0].length-1] = temp;
		}
		
		return flag;
	}
	/**
	 * 判断方法是否合理
	 */
	private boolean isProper(int[] xi) {
		boolean flag = true;
		for(int i=0;i<b.length;i++) {
			int sum = 0;
			for(int j=0;j<b[0].length;j++) {
				if(b[i][j] != 0) {
					sum += xi[j]*b[i][j];
				}
			}
			if(sum > typeNum[i]) {
				flag = false;
				break;
			}
		}
		return flag;
	}
	/**
	 * 判断是否有那么多个体数
	 */
	private int getMaxChrNum() {
		int multi = 1;
		List<Integer> tempNum = new ArrayList<Integer>();
		for(int i=0;i<typeNum.length;i++) {
			tempNum.add(typeNum[i]);
		}
		for(int i=0;i<b[0].length;i++) {
			int temp = 10000;
			for(int j=0;j<b.length;j++) {
				if(b[j][i] != 0) {
					int val = tempNum.get(j)/b[j][i];
					if(val < temp) {
						temp = val;
					}
				}
			}
			multi = multi * temp;
			if(multi > ChrNum) {
				break;
			}
		}
		if(multi < ChrNum) {
			ChrNum = multi;
		}
		return ChrNum;
	}
	
	/**
	 * 获取剩余量较大的型号，进行cross时增加
	 */
	private int[] getIndex(int[] xi) {
		int remainMaxIndex = 0;
		int remainMax = 0;
		for(int i=0;i<b.length;i++) {
			int sum = 0;
			for(int j=0;j<b[0].length;j++) {
				if(b[i][j] != 0) {
					sum += xi[j]*b[i][j];
				}
			}
			if(typeNum[i]-sum > remainMax) {
				remainMax = typeNum[i]-sum;
				remainMaxIndex = i;
			}
		}
		int num = 0;//
		for(int i=0;i<xi.length;i++) {
			if(b[remainMaxIndex][i] != 0) {
				num++;
			}
		}
		
		if(num != 0) {
			int[] index = new int[num];
			int[] bi = new int[xi.length];
			for(int i=0;i<xi.length;i++) {
				bi[i] = b[remainMaxIndex][i];
			}
			int[] biindex = new int[bi.length];
			for(int i=0;i<bi.length;i++) {
				biindex[i] = i;
			}
			for(int i=0;i<bi.length-1;i++) {
				for(int j=0;j<bi.length-i-1;j++){
					if(bi[j] < bi[j+1]) {
						swap(bi,j);
						swap(biindex,j);
					}
				}
			}
			for(int i=0;i<num;i++) {
				index[i] = biindex[i];
			}
			return index;
		} else {
			return null;
		}
		
		
	}
}
