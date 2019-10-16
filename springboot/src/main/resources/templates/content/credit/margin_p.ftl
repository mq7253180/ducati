<#assign jslist=["/pagination.js", "/jquery-ui/jquery-ui.min.js", "/js/common.js", "/js/content/margin.js"]/>
<#assign csslist=["/jquery-ui/jquery-ui.css"]/>
<#include "/layout_p.ftl"/>
<@layout>
		<!-- 修改模态框（Modal） -->
		<div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content" style="width:100%">
					<div class="modal-header">
						<h4 class="modal-title" id="myModalLabel">更新保证金</h4>
						<button type="button" class="close click-close" data-dismiss="modal" aria-hidden="true">&times;</button>
					</div>
					<div class="modal-body">
						币种: 
						<select id="marginCurrencySel" class="currency-clear">
							<#list currencies as currency>
							<option value="${currency}">${currency}</option>
							</#list>
						</select>
						金额: <input type="text" id="marginAmmountTxt" class="txt-clear" />
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default click-close" data-dismiss="modal">关闭</button>
						<@button type="button" class="btn btn-primary" id="updateSubmitBtn" permission="creditMarginUpdate">提交</@button>
					</div>
				</div><!-- /.modal-content -->
			</div><!-- /.modal -->
		</div>
		<input type="hidden" class="txt-clear" id="creditMarginId" value="" />
		<div class="hk-pg-wrapper">
			<!-- Container -->
				<!-- Title -->
                <div class="hk-pg-header mb-10">
					<div>
						<h4 class="hk-pg-title"><span class="pg-title-icon"><span class="feather-icon"><i data-feather="book"></i></span></span>保证金</h4>
                    </div>
                    	未结融资余额: 剩余可用额度: 
					<div class="d-flex">
						<form method="post" id="searchForm">
							生效时间: <input type="text" id="validFrom" name="from" value="${criterionFrom?if_exists}" readonly />-<input type="text" id="validTo" name="to" value="${criterionTo?if_exists}" readonly />
							客户: 
							<select id="companySel" name="companyId" class="txt-clear">
								<option value="">全部</option>
							<#list companies as company>
								<option value="${company.id}">${company.cnName}</option>
							</#list>
							</select>
							保证金币种: 
							<select id="currencySel" name="currency">
								<option value="">全部</option>
							<#list currencies as currency>
								<option value="${currency}">${currency}</option>
							</#list>
							</select>
						</form>
						<input type="hidden" id="criterionCompanyId" value="${criterionCompanyId?if_exists}" />
						<input type="hidden" id="criterionCurrency" value="${criterionCurrency?if_exists}" />
						<@button id="searchBtn" class="btn btn-primary btn-sm" permission="creditMarginSeach">查询</@button>
                    </div>
                </div>
                <!-- /Title -->
                <div class="row">
                    <div class="col-xl-12">
                        <section class="hk-sec-wrapper">
                            <div class="row">
                                <div class="col-sm">
                                    <div class="table-wrap">
                                        <div class="table-responsive">
                                            <table id="edit_datable_1" class="table table-bordered table-striped mb-0">
                                                <thead>
                                                    <tr align="center">
                                                        <th>客户</th>
                                                        <th>合同币种</th>
                                                        <th>保证金币种</th>
                                                        <th>保证金量</th>
                                                        <th>汇率</th>
                                                        <th>比例</th>
                                                        <th>折算额度</th>
                                                        <th>经办</th>
                                                        <th>状态</th>
                                                        <th>生效时间</th>
                                                        <th>审核</th>
                                                        <th>操作项</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                <#if pagination.data?exists>
                                                	<#list pagination.data as margin>
                                                    <tr>
                                                        <td>${margin.companyCnName}</td>
                                                        <td>${margin.creditCurrency}</td>
                                                        <td>${margin.currency}</td>
                                                        <td align="right">${margin.amount}</td>
                                                        <td align="right">${margin.price}</td>
                                                        <td align="right">${margin.ratio}%</td>
                                                        <td align="right">${margin.marginAmount}</td>
                                                        <td>${margin.userName}</td>
                                                        <td>${margin.displayedStatus}</td>
                                                        <td>${margin.validFrom?if_exists}</td>
                                                        <td>${margin.approverUserName?if_exists}</td>
                                                        <td>
                                                        	<@a href="javascript:void(0);" class="click-audit" permission="creditMarginAudit" value="${margin.id}_${margin.currency}_${margin.amount}">审核</@a>|
                                                        	<@a href="javascript:void(0);" class="click-delete" permission="creditMarginDelete" value="${margin.id}">删除</@a>|
                                                        	<@a href="javascript:void(0);" class="click-update" permission="creditMarginUpdate" value="${margin.id}">修改</@a>
                                                        </td>
                                                    </tr>
													</#list>
												</#if>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <@p from="${pagination.from}" to="${pagination.to}" count="${pagination.count}" pages="${pagination.pages}" page="${pagination.page}" />
                        </section>
                    </div>
                </div>
            <!-- /Container -->
        </div>
</@layout>