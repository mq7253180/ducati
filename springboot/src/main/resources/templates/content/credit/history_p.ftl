<#assign jslist=["/pagination.js", "/jquery-ui/jquery-ui.min.js", "/js/content/credit_history.js"]/>
<#assign csslist=["/jquery-ui/jquery-ui.css"]/>
<#include "/layout_p.ftl"/>
<@layout>
		<div class="hk-pg-wrapper">
			<!-- Container -->
				<!-- Title -->
                <div class="hk-pg-header mb-10">
					<div>
						<h4 class="hk-pg-title"><span class="pg-title-icon"><span class="feather-icon"><i data-feather="book"></i></span></span>合同授信额度修改历史</h4>
                    </div>
                    	客户: ${credit.companyCnName}; 币种: ${credit.currency}; 保证金比例: ${credit.ratio}%
                    <input type="hidden" id="creditId" value="${credit.id}" />
					<div class="d-flex">
						<form method="post" id="searchForm">
							生效时间: <input type="text" id="validFrom" name="from" value="${criterionFrom?if_exists}" readonly />-<input type="text" id="validTo" name="to" value="${criterionTo?if_exists}" readonly />
						</form>
						<@button id="searchBtn" class="btn btn-primary btn-sm" permission="creditSeach">查询</@button>
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
                                                        <th>授信总额度</th>
                                                        <th>生效时间</th>
                                                        <th>经办</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                <#if pagination.data?exists>
                                                	<#list pagination.data as history>
                                                    <tr>
                                                        <td align="right">${history.amount}</td>
                                                        <td>${history.validFrom}</td>
                                                        <td>${history.userName}</td>
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