package com.inspur.luke.product.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 功能描述: 
 * 创建人: zhangguilu
 * 创建时间:2017年11月2日 上午9:33:13
 */
@Controller
public class ComputeController {

	//跳转到首页
	@RequestMapping("/index")
	public ModelAndView index(){  
       //创建模型跟视图，用于渲染页面。并且指定要返回的页面为home页面  
       ModelAndView mav = new ModelAndView("demo");
       return mav;  
    }
	@RequestMapping(value = "/compute", method = RequestMethod.POST)
	public ModelAndView compute(String typeVal,String typeNumVal,String startError,String maxError,Model model, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("demo");
		if("".equals(typeVal) || "".equals(typeNumVal)) {
			mav.addObject("result","型号尺寸和型号数量为必填项！");
			return mav;
		}
		String[] types = typeVal.split(",");
		String[] typeNums = typeNumVal.split(",");
		mav.addObject("typeVal", typeVal);
		mav.addObject("typeNumVal", typeNumVal);
		mav.addObject("startError", startError);
		mav.addObject("maxError", maxError);
		int length = types.length;
		
		if(length != typeNums.length) {
			mav.addObject("result","型号和数量不对应，请检查输入参数！");
			return mav;
		}
		StringBuilder sb = new StringBuilder();
		int[] type = new int[length];
		int[] typeNum = new int[length];
		for(int i=0;i<length;i++) {
			type[i] = Integer.parseInt(types[i]);
			typeNum[i] = Integer.parseInt(typeNums[i]);
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		OptimiseUtils optimiseUtil = new OptimiseUtils();
		StringBuilder result = new StringBuilder();
		int initError = 1;
		if(startError != null && !"".equals(startError) && !"null".equals(startError)) {
			initError = Integer.parseInt(startError);
		}
		int finalError = 51;
		if(maxError != null && !"".equals(maxError) && !"null".equals(maxError)) {
			finalError = Integer.parseInt(maxError);
		}
		try {
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
						error+=5;
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
		} catch(Exception e) {
			mav.addObject("result","无合适方案，请增大初始误差值重新计算！");
			return mav;
		}
		result.append("各型号剩余数量：");
		for(int i=0;i<type.length;i++) {
			result.append("\n");
			result.append("长度"+type[i]+"剩余数量：" + typeNum[i]);
		}
		result.append("\n");
		result.append(sb.toString());
		mav.addObject("result",result.toString());
		return mav;
	}
}
