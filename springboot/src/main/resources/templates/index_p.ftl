<#assign jslist=[]/>
<#assign csslist=[]/>
<#include "/layout_p.ftl"/>
<@layout>
				<!-- Container -->
	            <div class="container mt-xl-50 mt-sm-30 mt-15">
	                <!-- Title -->
	                <div class="hk-pg-header">
	                    <div>
							<h2 class="hk-pg-title font-weight-600 mb-10">Web Stats</h2>
							<p>Earnings from subscriptions that stared in the period 1 - 31 December 2018<i class="ion ion-md-help-circle-outline ml-5" data-toggle="tooltip" data-placement="top" title="Need help about earning stats"></i></p>
						</div>
						<div class="d-flex">
	                        <div class="btn-group btn-group-sm">
								<button type="button" class="btn btn-secondary">Split dropdown</button>
								<button type="button" class="btn btn-secondary dropdown-toggle dropdown-toggle-split" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
									<span class="sr-only">Toggle Dropdown</span>
								</button>
								<div class="dropdown-menu dropdown-menu-right">
									<a class="dropdown-item" href="#">Action</a>
									<a class="dropdown-item" href="#">Another action</a>
									<a class="dropdown-item" href="#">Something else here</a>
									<div class="dropdown-divider"></div>
									<a class="dropdown-item" href="#">Separated link</a>
								</div>
							</div>
	                    </div>
	                </div>
	                <!-- /Title -->
	
	                <!-- Row -->
	                <div class="row">
	                    <div class="col-xl-12">
							<div class="hk-row">
								<div class="col-lg-8">
									<div class="card-group hk-dash-type-2">
										<div class="card card-sm">
											<div class="card-body">
												<span class="d-block font-14 font-weight-500 text-dark">Bonus Rate</span>
												<div class="d-flex align-items-center justify-content-between">
													<div class="display-5 font-weight-400 text-dark">47.43%</div>
													<div class="font-13 font-weight-500">
														<span>-28.12%</span>
														<i class="ion ion-md-arrow-down text-danger ml-5"></i>
													</div>
												</div>
												<div class="mt-20">
													<div id="sparkline_1"></div>
												</div>
											</div>
										</div>
										<div class="card card-sm">
											<div class="card-body">
												<span class="d-block font-14 font-weight-500 text-dark">New Sessions</span>
												<div class="d-flex align-items-center justify-content-between">
													<div class="display-5 font-weight-400 text-dark">76.4%</div>
													<div class="font-13 font-weight-500">
														<span>2.12%</span>
														<i class="ion ion-md-arrow-up text-success ml-5"></i>
													</div>
												</div>
												<div class="mt-20">
													<div id="sparkline_2"></div>
												</div>
											</div>
										</div>
										<div class="card card-sm">
											<div class="card-body">
												<span class="d-block font-14 font-weight-500 text-dark">Time on Site</span>
												<div class="d-flex align-items-center justify-content-between">
													<div class="display-5 font-weight-400 text-dark">2m 15s</div>
													<div class="font-13 font-weight-500">
														<span>39.15%</span>
														<i class="ion ion-md-arrow-up text-success ml-5"></i>
													</div>
												</div>
												<div class="mt-20">
													<div id="sparkline_3"></div>
												</div>
											</div>
										</div>
									</div>
										
									<div class="hk-row">
										<div class="col-md-6">
											<div class="card">
												<div class="card-header card-header-action">
													<h6>Visits by device type</h6>
													<div class="d-flex align-items-center card-action-wrap">
														<div class="inline-block dropdown">
															<a class="dropdown-toggle no-caret" data-toggle="dropdown" href="#" aria-expanded="false" role="button"><i class="ion ion-ios-more"></i></a>
															<div class="dropdown-menu dropdown-menu-right">
																<a class="dropdown-item" href="#">Action</a>
																<a class="dropdown-item" href="#">Another action</a>
																<a class="dropdown-item" href="#">Something else here</a>
																<div class="dropdown-divider"></div>
																<a class="dropdown-item" href="#">Separated link</a>
															</div>
														</div>
													</div>
												</div>
												<div class="card-body">
													<div id="e_chart_6" class="echart" style="height:197px;"></div>
													<div class="hk-legend-wrap mt-10">
														<div class="hk-legend">
															<span class="d-10 bg-primary rounded-circle d-inline-block"></span><span>Desktop</span>
														</div>
														<div class="hk-legend">
															<span class="d-10 bg-brown-light-3 rounded-circle d-inline-block"></span><span>Mobile</span>
														</div>
														<div class="hk-legend">
															<span class="d-10 bg-brown-light-2 rounded-circle d-inline-block"></span><span>Tablet</span>
														</div>
													</div>
												</div>
											</div>
										</div>
										<div class="col-md-6">
											<div class="card">
												<div class="card-header card-header-action">
													<h6>Total Sales</h6>
													<div class="d-flex align-items-center card-action-wrap">
														<div class="inline-block dropdown">
															<a class="dropdown-toggle no-caret" data-toggle="dropdown" href="#" aria-expanded="false" role="button"><i class="ion ion-ios-more"></i></a>
															<div class="dropdown-menu dropdown-menu-right">
																<a class="dropdown-item" href="#">Action</a>
																<a class="dropdown-item" href="#">Another action</a>
																<a class="dropdown-item" href="#">Something else here</a>
																<div class="dropdown-divider"></div>
																<a class="dropdown-item" href="#">Separated link</a>
															</div>
														</div>
													</div>
												</div>
												<div class="card-body">
													<div class="d-flex align-items-start justify-content-between mb-5">
														<div class="display-5 text-dark">$40,630.59</div>
														<div class="font-16 text-green font-weight-500">
															<i class="ion ion-md-arrow-up mr-5"></i>
															<span>5.12%</span>
														</div>
													</div>
													<div id="m_chart_3" class="" style="height:150px;"></div>
													<div class="hk-legend-wrap mt-10">
														<div class="hk-legend">
															<span class="d-10 bg-primary rounded-circle d-inline-block"></span><span>Today</span>
														</div>
														<div class="hk-legend">
															<span class="d-10 bg-brown-light-2 rounded-circle d-inline-block"></span><span>Yesterday</span>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="col-lg-4">
									<div class="card">
										<div class="card-header card-header-action">
											<h6>Audience Area Pole</h6>
											<div class="d-flex align-items-center card-action-wrap">
												<div class="inline-block dropdown">
													<a class="dropdown-toggle no-caret" data-toggle="dropdown" href="#" aria-expanded="false" role="button"><i class="ion ion-ios-more"></i></a>
													<div class="dropdown-menu dropdown-menu-right">
														<a class="dropdown-item" href="#">Action</a>
														<a class="dropdown-item" href="#">Another action</a>
														<a class="dropdown-item" href="#">Something else here</a>
														<div class="dropdown-divider"></div>
														<a class="dropdown-item" href="#">Separated link</a>
													</div>
												</div>
											</div>
										</div>
										<div class="card-body">
											<div id="e_chart_1" class="echart" style="height:190px;"></div>
											<div class="hk-legend-wrap mt-10">
												<div class="hk-legend">
													<span class="d-10 bg-primary rounded-circle d-inline-block"></span><span>A-1</span>
												</div>
												<div class="hk-legend">
													<span class="d-10 bg-brown-light-3 rounded-circle d-inline-block"></span><span>B-2</span>
												</div>
												<div class="hk-legend">
													<span class="d-10 bg-brown-light-4 rounded-circle d-inline-block"></span><span>C-3</span>
												</div>
												<div class="hk-legend">
													<span class="d-10 bg-brown-light-2 rounded-circle d-inline-block"></span><span>D-4</span>
												</div>
											</div>
										</div>
									</div>
									<div class="card card-sm border-bottom-0">
										<div class="card-header card-header-action">
											<h6>Device Stats</h6>
											<div class="d-flex align-items-center card-action-wrap">
												<div class="inline-block dropdown">
													<a class="dropdown-toggle no-caret" data-toggle="dropdown" href="#" aria-expanded="false" role="button"><i class="ion ion-ios-more"></i></a>
													<div class="dropdown-menu dropdown-menu-right">
														<a class="dropdown-item" href="#">Action</a>
														<a class="dropdown-item" href="#">Another action</a>
														<a class="dropdown-item" href="#">Something else here</a>
														<div class="dropdown-divider"></div>
														<a class="dropdown-item" href="#">Separated link</a>
													</div>
												</div>
											</div>
										</div>
										<div class="card-body pa-0">
											<div class="pa-15">
												<div class="row">
													<div class="col-4">
														<span class="d-block text-capitalize">desktop</span>
														<span class="d-block text-dark font-weight-500 font-20">15%</span>
														<span class="d-block font-weight-600 font-13">201,434</span>
													</div>
													<div class="col-4">
														<span class="d-block text-capitalize">mobile</span>
														<span class="d-block text-dark font-weight-500 font-20">34.5%</span>
														<span class="d-block font-weight-600 font-13">101,434</span>
													</div>
													<div class="col-4">
														<span class="d-block text-capitalize">tablet</span>
														<span class="d-block text-dark font-weight-500 font-20">60.8%</span>
														<span class="d-block font-weight-600 font-13">101,434</span>
													</div>
												</div>
											</div>
											 <div class="progress-wrap">
												<div class="progress rounded-bottom-left rounded-bottom-right">
													<div class="progress-bar bg-primary w-15" role="progressbar" aria-valuenow="15" aria-valuemin="0" aria-valuemax="100"></div>
													<div class="progress-bar bg-brown-light-2 w-35" role="progressbar" aria-valuenow="35" aria-valuemin="0" aria-valuemax="100"></div>
													<div class="progress-bar bg-brown-light-3 w-50" role="progressbar" aria-valuenow="50" aria-valuemin="0" aria-valuemax="100"></div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="card">
								<div class="card-body pa-0">
									<div class="table-wrap">
										<div class="table-responsive">
											<table class="table table-hover mb-0">
												<thead>
													<tr>
														<th></th>
														<th>Name</th>
														<th>Chart</th>
														<th>last Year</th>
														<th>6 months</th>
														<th>1 month</th>
														<th>Day</th>
														<th>Sale</th>
														<th>Buy</th>
													</tr>
												</thead>
												<tbody>
													<tr>
														<td><img class="circle" src="<@property key="prefix.resource"/>/themeforest/img/logo5.jpg" alt="icon"></td>
														<td>Fakebook</td>
														<td><span class="peity-line" data-width="90" data-peity='{ "fill": ["rgba(102,64,178,.05)"], "stroke":["#6640b2"]}' data-height="40">1,6,6,9,7,4,8,5,2,1</span> </td>
														<td><span class="text-success"><i class="ion ion-md-arrow-up" aria-hidden="true"></i> $ 1,234</span> </td>
														<td><span class="text-danger"><i class="ion ion-md-arrow-down" aria-hidden="true"></i> $ 5,678</span> </td>
														<td><span class="text-success"><i class="ion ion-md-arrow-up" aria-hidden="true"></i> $ 9,101</span> </td>
														<td><span class="text-danger"><i class="ion ion-md-arrow-down" aria-hidden="true"></i> $ 1,121</span> </td>
														<td>$3,141</td>
														<td>$5,161</td>
													</tr>
													<tr>
														<td><img class="circle" src="<@property key="prefix.resource"/>/themeforest/img/logo2.jpg" alt="icon"></td>
														<td>Microhard</td>
														<td><span class="peity-line" data-width="90" data-peity='{ "fill": ["rgba(102,64,178,.05)"], "stroke":["#6640b2"]}' data-height="40">1,6,6,9,9,9,8,5,2,1</span> </td>
														<td><span class="text-danger"><i class="ion ion-md-arrow-down" aria-hidden="true"></i> $ 7,181</span> </td>
														<td><span class="text-danger"><i class="ion ion-md-arrow-down" aria-hidden="true"></i> $ 9,202</span> </td>
														<td><span class="text-success"><i class="ion ion-md-arrow-up" aria-hidden="true"></i> $ 1,222</span> </td>
														<td><span class="text-danger"><i class="ion ion-md-arrow-down" aria-hidden="true"></i> $ 3,242</span> </td>
														<td>$5,262</td>
														<td>$7,282</td>
													</tr>
													<tr>
														<td><img class="circle" src="<@property key="prefix.resource"/>/themeforest/img/logo3.jpg" alt="icon"></td>
														<td>Oesla Motors</td>
														<td><span class="peity-line" data-width="90" data-peity='{ "fill": ["rgba(102,64,178,.05"], "stroke":["#6640b2"]}' data-height="40">5,6,5,5,7,4,6,5,2,1</span> </td>
														<td><span class="text-success"><i class="ion ion-md-arrow-up" aria-hidden="true"></i> $ 9,303</span> </td>
														<td><span class="text-danger"><i class="ion ion-md-arrow-down" aria-hidden="true"></i> $ 1,323</span> </td>
														<td><span class="text-success"><i class="ion ion-md-arrow-up" aria-hidden="true"></i> $ 3,343</span> </td>
														<td><span class="text-danger"><i class="ion ion-md-arrow-down" aria-hidden="true"></i> $ 5,363</span> </td>
														<td>$7,383</td>
														<td>$9,404</td>
													</tr>
													<tr>
														<td><img class="circle" src="<@property key="prefix.resource"/>/themeforest/img/logo4.jpg" alt="icon"></td>
														<td>NVISION</td>
														<td><span class="peity-line" data-width="90" data-peity='{ "fill": ["rgba(50,65,72,.05"], "stroke":["#6640b2"]}' data-height="40">3,4,4,6,4,4,7,5,2,1</span> </td>
														<td><span class="text-success"><i class="ion ion-md-arrow-up" aria-hidden="true"></i> $ 1,424</span> </td>
														<td><span class="text-danger"><i class="ion ion-md-arrow-down" aria-hidden="true"></i> $ 3,444</span> </td>
														<td><span class="text-success"><i class="ion ion-md-arrow-up" aria-hidden="true"></i> $ 5,464</span> </td>
														<td><span class="text-danger"><i class="ion ion-md-arrow-down" aria-hidden="true"></i> $ 7,484</span> </td>
														<td>$9,505</td>
														<td>$1,525</td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>
								</div>
							</div>		
						</div>
	                </div>
	                <!-- /Row -->
				</div>
	            <!-- /Container -->
				<!-- Footer -->
	            <div class="hk-footer-wrap container">
	                <footer class="footer">
	                    <div class="row">
	                        <div class="col-md-6 col-sm-12">
	                            <p>Pampered by<a href="https://hencework.com/" class="text-dark" target="_blank">Hencework</a> © 2019</p>
	                        </div>
	                        <div class="col-md-6 col-sm-12">
	                            <p class="d-inline-block">Follow us</p>
	                            <a href="#" class="d-inline-block btn btn-icon btn-icon-only btn-indigo btn-icon-style-4"><span class="btn-icon-wrap"><i class="fa fa-facebook"></i></span></a>
	                            <a href="#" class="d-inline-block btn btn-icon btn-icon-only btn-indigo btn-icon-style-4"><span class="btn-icon-wrap"><i class="fa fa-twitter"></i></span></a>
	                            <a href="#" class="d-inline-block btn btn-icon btn-icon-only btn-indigo btn-icon-style-4"><span class="btn-icon-wrap"><i class="fa fa-google-plus"></i></span></a>
	                        </div>
	                    </div>
	                </footer>
	            </div>
	            <!-- /Footer -->
</@layout>