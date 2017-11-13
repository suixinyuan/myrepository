<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700&subset=all" rel="stylesheet" type="text/css" />
<link href="assets/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
<link href="assets/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css" />
<link href="assets/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="assets/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css" />
<link href="assets/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet" type="text/css" />
<!-- END GLOBAL MANDATORY STYLES -->
<!-- BEGIN PAGE LEVEL PLUGINS -->
<link href="assets/plugins/jquery-notific8/jquery.notific8.min.css" rel="stylesheet" type="text/css" />
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN THEME GLOBAL STYLES -->
<link href="assets/css/components.min.css" rel="stylesheet" id="style_components" type="text/css" />
<link href="assets/css/plugins.min.css" rel="stylesheet" type="text/css" />
<style type="text/css">
	#result {  
    width: 100%; /*自动适应父布局宽度*/  
    overflow: auto;  
    word-break: break-all;  
    /*在ie中解决断行问题(防止自动变为在一行显示，主要解决ie兼容问题，ie8中当设宽度为100%时，文本域类容超过一行时，当我们双击文本内容就会自动变为一行显示，所以只能用ie的专有断行属性“word-break或word-wrap”控制其断行)*/  
}
</style>
<!-- END THEME GLOBAL STYLES -->
<title>尺寸计算</title>
</head>
<body>
<div class="content">
	<div class="row">
        <div class="col-md-12">
            <!-- BEGIN VALIDATION STATES-->
            <div class="portlet light portlet-fit portlet-form bordered">
                <div class="portlet-title">
                    <div class="caption">
                        <i class="icon-settings font-red"></i>
                        <span class="caption-subject font-red sbold uppercase">条件</span>
                    </div>
                </div>
                <div class="portlet-body">
					<form class="login-form" action="compute" method="post">
						<div class="alert alert-danger display-hide">
							<button class="close" data-close="alert"></button>
							<span>
								 输入型号尺寸和数量
							</span>
						</div>
						<div class="form-group">
							<!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
							<label class="control-label visible-ie8 visible-ie9">型号尺寸</label>
							<div class="input-icon">
								<i class="fa fa-tv"></i>
								<input name="typeVal" id="typeVal" value="${typeVal}" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="请输入型号尺寸，以逗号间隔。如387,620,873" />
							</div>
						</div>
						<div class="form-group">
							<label class="control-label visible-ie8 visible-ie9">型号数量</label>
							<div class="input-icon">
								<i class="fa fa-sort-numeric-asc"></i>
								<input name="typeNumVal" id="typeNumVal" value="${typeNumVal}" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="请输入型号数量，以逗号间隔，且与上述型号一一对应。如25,38,120" />
							</div>
						</div>
						<div style="width:49%;float:left">
							<div class="form-group">
									<label class="control-label visible-ie8 visible-ie9">初始误差</label>
									<div class="input-icon">
										<i class="fa fa-minus-circle"></i>
										<input name="startError" id="startError" value="${startError}" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="最小误差，与5980相比。此参数控制可选方案的数量。" />
									</div>
							</div>
						</div>
						<div style="width:49%;float:right">
							<div class="form-group">
								<label class="control-label visible-ie8 visible-ie9">最大误差</label>
								<div class="input-icon">
									<i class="fa fa-maxcdn"></i>
									<input name="maxError" id="maxError" value="${maxError}" class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="最大误差，与5980相比。此参数控制剩余原材料的数量。" />
								</div>
							</div>
						</div>
						<div class="form-actions" align="center">
							<button id="loginBut" type="button" onclick="reset()" class="btn blue" style="margin:auto;">
							 <i class="fa fa-paint-brush"></i>清空
							</button>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<button id="loginBut" type="submit" class="btn blue" style="margin:auto;">
							<i class="fa fa-paper-plane-o"></i>计算 
							</button>
						</div>
					</form>
				</div>
				
				<div class="portlet-title">
                    <div class="caption">
                        <i class="icon-settings font-red"></i>
                        <span class="caption-subject font-red sbold uppercase">计算结果</span>
                    </div>
                </div>
                <div class="portlet-body">
                	<textarea cols="80" rows="10" id="result" readonly>${result}</textarea>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	function reset() {
		$("#typeVal").val("");
		$("#typeNumVal").val("");
		$("#startError").val("");
		$("#maxError").val("");
		$(".result").text("");
	}
</script>
</body>
</html>