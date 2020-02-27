package com.hce.ducati.freemarker;

import java.io.IOException;
import java.util.Map;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class PaginationTemplateDirectiveModelBean implements TemplateDirectiveModel {
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		int from = Integer.parseInt(params.get("from").toString());
		int to = Integer.parseInt(params.get("to").toString());
		int count = Integer.parseInt(params.get("count").toString());
		int page = Integer.parseInt(params.get("page").toString());
		int pages = Integer.parseInt(params.get("pages").toString());
		StringBuilder html = new StringBuilder(1200)
				.append("<input type=\"hidden\" id=\"paginationPage\" value=\"").append(page).append("\" />\r\n")
				.append("<input type=\"hidden\" id=\"paginationPages\" value=\"").append(pages).append("\" />\r\n")
				.append("<div class=\"row\">\r\n")
				.append("\t<div class=\"col-sm-12 col-md-5\">\r\n")
				.append("\t\t<div class=\"dataTables_info\" id=\"datable_1_info\" role=\"status\" aria-live=\"polite\">Showing ")
				.append(from).append(" to ").append(to).append(" of ").append(count)
				.append(" entries</div>\r\n")
				.append("\t</div>\r\n")
				.append("\t<div class=\"col-sm-12 col-md-7\">\r\n")
				.append("\t\t<div class=\"dataTables_paginate paging_simple_numbers\" id=\"datable_1_paginate\">\r\n")
				.append("\t\t\t<ul class=\"pagination\">\r\n")
				.append("\t\t\t\t<li class=\"paginate_button page-item previous");
		if(page==1||pages==0)
			html.append(" disabled");
		html.append("\" id=\"paginationPrevious\"><a href=\"javascript:void(0);\" aria-controls=\"datable_1\" data-dt-idx=\"0\" tabindex=\"0\" class=\"page-link\">Previous</a></li>\r\n");
		for(int i=1;i<=pages;i++) {
			html.append("\t\t\t\t<li class=\"paginationPage paginate_button page-item");
			if(page==i)
				html.append(" active");
			html.append("\" value=\"")
			.append(i)
			.append("\"><a href=\"javascript:void(0);\" aria-controls=\"datable_1\" data-dt-idx=\"")
			.append(i)
			.append("\" tabindex=\"0\" class=\"page-link\">")
			.append(i)
			.append("</a></li>\r\n");
		}
		html.append("\t\t\t\t<li class=\"paginate_button page-item next");
		if(page>=pages)
			html.append(" disabled");
		html.append("\" id=\"paginationNext\"><a href=\"javascript:void(0);\" aria-controls=\"datable_1\" data-dt-idx=\"")
		.append(pages+1)
		.append("\" tabindex=\"0\" class=\"page-link\">Next</a></li>\r\n")
		.append("\t\t\t</ul>\r\n")
		.append("\t\t</div>\r\n")
		.append("\t</div>\r\n")
		.append("</div>\r\n");
		env.getOut().write(html.toString());
	}
}
