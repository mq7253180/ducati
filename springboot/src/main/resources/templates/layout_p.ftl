<#import "/spring.ftl" as spring>
<#macro layout>
<!DOCTYPE html>
<html>
	<head>
	    <meta charset="UTF-8" />
	    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
	    <title>循环授信再保理</title>
	    <meta name="description" content="A responsive bootstrap 4 admin dashboard template by hencework" />
	    <!-- Favicon -->
	    <link rel="shortcut icon" href="<@property key="prefix.resource"/>/themeforest/favicon.ico" />
	    <link rel="icon" type="image/x-icon" href="<@property key="prefix.resource"/>/themeforest/favicon.ico" />
		<!-- Morris Charts CSS -->
	    <link rel="stylesheet" type="text/css" href="<@property key="prefix.resource"/>/themeforest/vendors/morris/morris.css" />
	    <!-- Toggles CSS -->
	    <link rel="stylesheet" type="text/css" href="<@property key="prefix.resource"/>/themeforest/vendors/jquery-toggles/toggles.css" />
	    <link rel="stylesheet" type="text/css" href="<@property key="prefix.resource"/>/themeforest/vendors/jquery-toggles/toggles-light.css" />
	    <!-- Custom CSS -->
	    <link rel="stylesheet" type="text/css" href="<@property key="prefix.resource"/>/themeforest/dist/css/style.css" />
	<#list csslist as css>
		<link rel="stylesheet" type="text/css" href="<@property key="prefix.resource"/>${css}" />
	</#list>
	</head>
	<body>
	    <!-- Preloader -->
	    <div class="preloader-it">
	        <div class="loader-pendulums"></div>
	    </div>
	    <!-- /Preloader -->
		<!-- HK Wrapper -->
		<div class="hk-wrapper hk-vertical-nav hk-icon-nav">
	        <!-- Top Navbar -->
	        <nav class="navbar navbar-expand-xl navbar-light fixed-top hk-navbar">
	            <a id="navbar_toggle_btn" class="navbar-toggle-btn nav-link-hover" href="javascript:void(0);"><span class="feather-icon"><i data-feather="menu"></i></span></a>
	            <a class="navbar-brand" href="/index">
	                <img class="brand-img d-inline-block" src="<@property key="prefix.resource"/>/themeforest/img/logo-light.png" alt="brand" />去首页
	            </a>
	            <ul class="navbar-nav hk-navbar-content">
	                <li class="nav-item dropdown dropdown-authentication">
	                    <a class="nav-link dropdown-toggle no-caret" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
	                        <div class="media">
	                            <div class="media-body">
	                                <span>${xsession.user.name}<i class="zmdi zmdi-chevron-down"></i></span>
	                            </div>
	                        </div>
	                    </a>
	                    <div class="dropdown-menu dropdown-menu-right" data-dropdown-in="flipInX" data-dropdown-out="flipOutX">
	                        <a class="dropdown-item" href="javascript:void(0);" id="logoutA"><i class="dropdown-icon zmdi zmdi-power"></i><span>登出</span></a>
	                    </div>
	                </li>
	            </ul>
	        </nav>
	        <form role="search" class="navbar-search">
	            <div class="position-relative">
	                <a href="javascript:void(0);" class="navbar-search-icon"><span class="feather-icon"><i data-feather="search"></i></span></a>
	                <input type="text" name="example-input1-group2" class="form-control" placeholder="Type here to Search">
	                <a id="navbar_search_close" class="navbar-search-close" href="#"><span class="feather-icon"><i data-feather="x"></i></span></a>
	            </div>
	        </form>
	        <!-- /Top Navbar -->
	        <!-- Vertical Nav -->
	        <nav class="hk-nav hk-nav-dark">
	            <a href="javascript:void(0);" id="hk_nav_close" class="hk-nav-close"><span class="feather-icon"><i data-feather="x"></i></span></a>
	            <div class="nicescroll-bar">
	                <div class="navbar-nav-wrap">
	                    <ul class="navbar-nav flex-column">
	                    	<#list xsession.menus as menu>
	                        <li class="nav-item active">
	                            <a class="nav-link" href="javascript:void(0);" data-toggle="collapse" data-target="#menu_${menu.id}">
	                                <span class="feather-icon"><i data-feather="${menu.icon}"></i></span>
	                                <span class="nav-link-text">${menu.name}</span>
	                            </a>
	                            <ul id="menu_${menu.id}" class="nav flex-column collapse collapse-level-1">
	                                <li class="nav-item">
	                                    <ul class="nav flex-column">
	                                    <#if menu.children?exists>
	                                    	<#list menu.children as subMenu>
	                                        <li class="nav-item">
	                                            <a class="nav-link" href="${subMenu.uri}">${subMenu.name}</a>
	                                        </li>
	                                        </#list>
										</#if>
	                                    </ul>
	                                </li>
	                            </ul>
	                        </li>
	                        </#list>
	                    </ul>
	                </div>
	            </div>
	        </nav>
	        <div id="hk_nav_backdrop" class="hk-nav-backdrop"></div>
	        <!-- /Vertical Nav -->
	        <!-- Setting Panel -->
	        <div class="hk-settings-panel">
	            <div class="nicescroll-bar position-relative">
	                <div class="settings-panel-wrap">
	                    <div class="settings-panel-head">
	                        <img class="brand-img d-inline-block align-top" src="<@property key="prefix.resource"/>/themeforest/img/logo-light.png" alt="brand" />
	                        <a href="javascript:void(0);" id="settings_panel_close" class="settings-panel-close"><span class="feather-icon"><i data-feather="x"></i></span></a>
	                    </div>
	                    <hr>
	                    <h6 class="mb-5">Layout</h6>
	                    <p class="font-14">Choose your preferred layout</p>
	                    <div class="layout-img-wrap">
	                        <div class="row">
	                            <a href="dashboard1.html" class="col-6 mb-30">
	                                <img class="rounded opacity-70" src="<@property key="prefix.resource"/>/themeforest/img/layout1.png" alt="layout">
	                                <i class="zmdi zmdi-check"></i>
	                            </a>
	                            <a href="dashboard2.html" class="col-6 mb-30">
	                                <img class="rounded opacity-70" src="<@property key="prefix.resource"/>/themeforest/img/layout2.png" alt="layout">
	                                <i class="zmdi zmdi-check"></i>
	                            </a>
	                           <a href="dashboard3.html" class="col-6 mb-30">
	                                <img class="rounded opacity-70" src="<@property key="prefix.resource"/>/themeforest/img/layout3.png" alt="layout">
	                                <i class="zmdi zmdi-check"></i>
	                            </a>
								<a href="javascript:void(0);" class="col-6 mb-30">
	                                <img class="rounded opacity-70" src="<@property key="prefix.resource"/>/themeforest/img/layout4.png" alt="layout">
	                                <i class="zmdi zmdi-check"></i>
	                            </a>
								<a href="dashboard5.html" class="col-6 active">
	                                <img class="rounded opacity-70" src="<@property key="prefix.resource"/>/themeforest/img/layout5.png" alt="layout">
	                                <i class="zmdi zmdi-check"></i>
	                            </a>
	                        </div>
	                    </div>
	                    <hr>
	                    <h6 class="mb-5">Navigation</h6>
	                    <p class="font-14">Menu comes in two modes: dark & light</p>
	                    <div class="button-list hk-nav-select mb-10">
	                        <button type="button" id="nav_light_select" class="btn btn-outline-light btn-sm btn-wth-icon icon-wthot-bg"><span class="icon-label"><i class="fa fa-sun-o"></i> </span><span class="btn-text">Light Mode</span></button>
	                        <button type="button" id="nav_dark_select" class="btn btn-outline-primary btn-sm btn-wth-icon icon-wthot-bg"><span class="icon-label"><i class="fa fa-moon-o"></i> </span><span class="btn-text">Dark Mode</span></button>
	                    </div>
	                    <hr>
	                    <h6 class="mb-5">Top Nav</h6>
	                    <p class="font-14">Choose your liked color mode</p>
	                    <div class="button-list hk-navbar-select mb-10">
	                        <button type="button" id="navtop_light_select" class="btn btn-outline-primary btn-sm btn-wth-icon icon-wthot-bg"><span class="icon-label"><i class="fa fa-sun-o"></i> </span><span class="btn-text">Light Mode</span></button>
	                        <button type="button" id="navtop_dark_select" class="btn btn-outline-light btn-sm btn-wth-icon icon-wthot-bg"><span class="icon-label"><i class="fa fa-moon-o"></i> </span><span class="btn-text">Dark Mode</span></button>
	                    </div>
	                    <hr>
	                    <div class="d-flex justify-content-between align-items-center">
	                        <h6>Scrollable Header</h6>
	                        <div class="toggle toggle-sm toggle-simple toggle-light toggle-bg-primary scroll-nav-switch"></div>
	                    </div>
	                    <button id="reset_settings" class="btn btn-primary btn-block btn-reset mt-30">Reset</button>
	                </div>
	            </div>
	            <img class="d-none" src="<@property key="prefix.resource"/>/themeforest/img/logo-light.png" alt="brand" />
	            <img class="d-none" src="<@property key="prefix.resource"/>/themeforest/img/logo-dark.png" alt="brand" />
	        </div>
	        <!-- /Setting Panel -->
	        <!-- Main Content -->
	        <div class="hk-pg-wrapper">
	        	<#nested/>
	        </div>
	        <!-- /Main Content -->
	    </div>
	    <input type="hidden" id="locale" value="<@locale/>"/>
		<input type="hidden" id="uri" value="<@attr key="uri_without_first" />"/>
		<input type="hidden" id="resourcePrefix" value="<@property key="prefix.resource"/>"/>
	    <!-- /HK Wrapper -->
	    <script src="<@property key="prefix.resource"/>/jquery-3.4.1.min.js"></script>
		<!-- Bootstrap Core JavaScript -->
		<script src="<@property key="prefix.resource"/>/themeforest/vendors/popper.min.js"></script>
		<script src="<@property key="prefix.resource"/>/themeforest/vendors/bootstrap/bootstrap.min.js"></script>
		<!-- Slimscroll JavaScript -->
		<script src="<@property key="prefix.resource"/>/themeforest/dist/js/jquery.slimscroll.js"></script>
		<!-- Fancy Dropdown JS -->
		<script src="<@property key="prefix.resource"/>/themeforest/dist/js/dropdown-bootstrap-extended.js"></script>
	    <!-- FeatherIcons JavaScript -->
	    <script src="<@property key="prefix.resource"/>/themeforest/dist/js/feather.min.js"></script>
	    <!-- Toggles JavaScript -->
	    <script src="<@property key="prefix.resource"/>/themeforest/vendors/jquery-toggles/toggles.min.js"></script>
	    <script src="<@property key="prefix.resource"/>/themeforest/dist/js/toggle-data.js"></script>
		<!-- Counter Animation JavaScript -->
		<script src="<@property key="prefix.resource"/>/themeforest/vendors/jquery.waypoints.min.js"></script>
		<script src="<@property key="prefix.resource"/>/themeforest/vendors/jquery.counterup.min.js"></script>
		<!-- Easy pie chart JS -->
	    <script src="<@property key="prefix.resource"/>/themeforest/vendors/jquery.easypiechart.min.js"></script>
		<!-- Sparkline JavaScript -->
	    <script src="<@property key="prefix.resource"/>/themeforest/vendors/jquery.sparkline.min.js"></script>
		<!-- Morris Charts JavaScript -->
	    <script src="<@property key="prefix.resource"/>/themeforest/vendors/raphael.min.js"></script>
	    <script src="<@property key="prefix.resource"/>/themeforest/vendors/morris/morris.min.js"></script>
		<!-- EChartJS JavaScript -->
		<script src="<@property key="prefix.resource"/>/themeforest/vendors/echarts-en.min.js"></script>
		<!-- Peity JavaScript -->
	    <script src="<@property key="prefix.resource"/>/themeforest/vendors/jquery.peity.min.js"></script>
	    <!-- Init JavaScript -->
	    <script src="<@property key="prefix.resource"/>/themeforest/dist/js/init.js"></script>
		<script src="<@property key="prefix.resource"/>/themeforest/dist/js/dashboard5-data.js"></script>
		<script src="<@property key="prefix.resource"/>/jquery.i18n.properties.min.js"></script>
		<script src="<@property key="prefix.resource"/>/core.js"></script>
		<script src="<@property key="prefix.resource"/>/layout.js"></script>
	<#list jslist as js>
		<script src="<@property key="prefix.resource"/>${js}"></script>
	</#list>
	</body>
</html>
</#macro>