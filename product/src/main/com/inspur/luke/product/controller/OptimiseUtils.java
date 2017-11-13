package com.inspur.luke.product.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hgl11
 *
 * version:1.0 
 *
 * date:2017年4月26日,下午2:56:05
 *
 */
public class OptimiseUtils {

	public Map<String,Object> getOptimiseMethod(int errorLimit,int[] typeNum,int[] type) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		List<int[]> methodList = new ArrayList<int[]>();
		List<Integer> errorList = new ArrayList<Integer>();
		
		int length = typeNum.length;
		//对原料的长度进行排序，同时原材料个数也跟着排序
		for(int i=0;i<length-1;i++) {
			for(int j=0;j<length-i-1;j++){
				if(type[j] > type[j+1]) {
					swap(type,j);
					swap(typeNum,j);
				}
			}
		}
		
		//找出第一个大于510毫米的原材料
		int indexOfLong510 = 0;
		for(int i=0;i<length;i++) {
			if(type[i] >= 510) {
				indexOfLong510 = i;
				break;
			}
		}
		
		//从大于510毫米的原材料开始遍历，只使用一种材料的情况
		if(type[indexOfLong510] > 510) {
			for(int i=indexOfLong510;i<length;i++) {
				int[] adapt = new int[length];
				for(int j=1;j<=typeNum[i];j++) {
					int error = 5980 - type[i] * j;
					if(error < 0 && errorLimit > 0) {
						error = 6000 - type[i] * j;
					}
					if(error >= 0 && error < errorLimit) {
						adapt[i] = j;
						methodList.add(adapt);
						errorList.add(error);
						break;
					}
				}
			}
		} else {
			return null;
		}
		
		
		//从大于510毫米的原材料开始遍历，使用两种和三种材料的情况
		for(int i=indexOfLong510;i<length;i++) {
			for(int numI=1;numI<=typeNum[i];numI++) {
				//如果510以上的原材料*根数的长度已经大于了5980，没必要查找组合了
				int lengthOfLong = numI*type[i];
				if(lengthOfLong > 6000) {
					break;
				} else {
					for(int j=0;j<length;j++) {
						if(j == i) {
							continue;
						}
						int[] adapt2 = new int[length];
						for(int numJ=1;numJ<=typeNum[j];numJ++) {
							int error = 5980 - type[j] * numJ - lengthOfLong;//误差
							if(error < 0 && errorLimit > 0) {
								error = 6000 - type[j] * numJ - lengthOfLong;//误差
							}
							if(error >= 0 && error < errorLimit) {
								//如果误差在允许范围内，则用第i种材料numI，第j种材料numJ
								adapt2[j] = numJ;
								adapt2[i] = numI;
								methodList.add(adapt2);
								errorList.add(error);
								break;
							} else if(error > errorLimit){ //如果前两个都不够5980，则继续考虑第三种，此种情况不单独写，减小计算量
								for(int k=0;k<length;k++) {
									if(k == i || k == j) {
										continue;
									}
									int[] adapt3 = new int[length];
									for(int numK=1;numK<=typeNum[k];numK++) {
										int errorLast = error - type[k] * numK;
										if(errorLast >= 0 && errorLast < errorLimit) {
											//如果误差在允许范围内，则用第i种材料numI，第j种材料numJ
											adapt3[j] = numJ;
											adapt3[i] = numI;
											adapt3[k] = numK;
											methodList.add(adapt3);
											errorList.add(errorLast);
											break;
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		//去除重复部分
		for(int i=0;i<methodList.size();i++) {
			int[] arr = methodList.get(i);
			for(int j=0;j<methodList.size();j++) {
				if(j == i)
					continue;
				int[] arr1 = methodList.get(j);
				if(Arrays.equals(arr,arr1)) {
					methodList.remove(j);
					errorList.remove(j);
				}
			}
		}
		
		//初始化系数矩阵
		int methodNum = methodList.size();
		int[][] B = new int[length][methodNum];
		for(int i=0;i<methodNum;i++) {
			int[] arr = methodList.get(i);
			for(int j=0;j<arr.length;j++) {
				B[j][i] = arr[j];
			}
		}
		
		int[] errorArray = new int[methodNum];
		for(int i=0;i<methodNum;i++) {
			errorArray[i] = errorList.get(i);
		}
		int[] x = new GaUtils().getResult(B, typeNum, type);
		if(x == null) {
			return null;
		}
		
		for(int i=0;i<x.length;i++) {
			if(x[i] != 0) {
				for(int j=0;j<length;j++) {
					if(B[j][i] != 0) {
						typeNum[j] = typeNum[j] - B[j][i]*x[i];
					}
				}
			}
		}
		StringBuilder sbFinal = new StringBuilder();

		for(int i=0;i<methodList.size();i++) {
			int[] arr = methodList.get(i);
			if(x[i] != 0) {
				int totalLen = 0;
				StringBuilder sb = new StringBuilder();
				for(int j=0;j<arr.length;j++) {
					if(arr[j] != 0) {
						sb.append(String.valueOf(arr[j])).append("*").append(type[j]).append("+");
						totalLen += arr[j]*type[j];
					}
				}
				String s = sb.toString();
				s = s.substring(0,s.lastIndexOf("+")) +" = " + totalLen + "........"+x[i];
				sbFinal.append(s+"\n");
			}
		}
		map.put("typeNum", typeNum);
		map.put("type", type);
		map.put("comMethod", sbFinal.toString());
		return map;
	}	
	
	
	
	public static void main(String[] args) {
		int[] typeNum = new int[]{454,60,12,48,454};
		int[] type = new int[]{1682,2082,632,802,502};
//		int[] typeNum = new int[]{112,32,128,96,176,62,62,48,44,48,48,24,176,368,32,188,96,24};
//		int[] type = new int[]{662,462,762,862,562,962,612,1012,812,1212,1112,1262,402,302,352,502,602,702};
//		int[] typeNum = new int[]{314,254,716,244,124,124,194,206,56};
//		int[] type = new int[]{380,860,300,200,1060,560,690,460,250};
		Map<String,Object> map = new HashMap<String,Object>();
		OptimiseUtils optimiseUtil = new OptimiseUtils();
		StringBuilder sb = new StringBuilder();
		int initError = 41;
		int finalError = 51;
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
				map = optimiseUtil.getOptimiseMethod(initError,typeNum,type);
				while(map == null) {
					error+=10;
					map = optimiseUtil.getOptimiseMethod(error,typeNum,type);
					if(error > finalError) {
						flag = false;
						break;
					}
				}
				if(flag) {
					typeNum = (int[])map.get("typeNum");
					type = (int[])map.get("type");
					sb.append((String)map.get("comMethod"));
				}
			} else {
				break;
			}
		}
		System.out.println("各型号剩余数量：");
		for(int i=0;i<type.length;i++) {
			System.out.println("长度"+type[i]+"剩余数量：" + typeNum[i]);
		}
		System.out.println(sb.toString());
	}
	
	private void swap(int[] type,int j) {
		int temp = type[j];
		type[j] = type[j+1];
		type[j+1] = temp;
	}
	

}

