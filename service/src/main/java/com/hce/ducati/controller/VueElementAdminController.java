package com.hce.ducati.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.quincy.sdk.annotation.DoNotWrap;
import com.quincy.sdk.annotation.auth.LoginRequired;

@Controller
@RequestMapping("vue-element-admin")
public class VueElementAdminController {
	@DoNotWrap
	@LoginRequired
	@GetMapping("/transaction/list")
	@ResponseBody
	public Result transactionList() {
		Data data = new Data();
		data.setTotal(transactionList.length);
		data.setItems(transactionList);
		Result result = new Result();
		result.setCode(20000);
		result.setData(data);
		return result;
	}

	private final static Object[] transactionList = {
			new Transaction("C7cc94dC-183b-01c2-Dd59-C7CdfE9C24BC", 429725574702l, "Edward Clark", 9482.3f, "success"), 
			new Transaction("89313889-C4d5-dEE6-B912-0eB69c275929", 429725574702l, "Kenneth Smith", 2944.22f, "success"), 
			new Transaction("ddEFded1-8ccC-19aa-ec22-bfF26Bb4AeF0", 429725574702l, "Sandra Perez", 6928f, "pending"), 
			new Transaction("d78a2BA4-53ff-98AE-3Aec-2edCEefF1B4f", 429725574702l, "Sharon Perez", 14888.6f, "pending"), 
			new Transaction("7f995761-243d-fDF8-9b3C-E6EaAFc1E51f", 429725574702l, "Timothy Williams", 8666.9f, "success"), 
			new Transaction("4F4eDdE0-9EFD-1b5F-A28A-28E9EA4DDE06", 429725574702l, "Betty Walker", 11660.2f, "pending"), 
			new Transaction("ff51B983-AcFB-C32C-4CFA-10ADeC4EdbFB", 429725574702l, "Michael Jones", 9491.4f, "success"), 
			new Transaction("E0eDe8AE-9C99-Fcc2-Dfb4-8Cb1932ebCfC", 429725574702l, "Sarah Wilson", 3725f, "pending"), 
			new Transaction("AAd11496-A1B3-1703-7208-2bB2bd4fF25F", 429725574702l, "Charles Hall", 2146f, "pending"), 
			new Transaction("4EA78050-7a8b-B4Fe-29Ec-89FDdae3bCFb", 429725574702l, "Ruth Lee", 5548.2f, "pending"), 
			new Transaction("F5Cd3bde-E049-BF56-5e58-ca4f7E2FDB2e", 429725574702l, "Carol Jackson", 9888f, "pending"), 
			new Transaction("E478EA0B-1a69-3Cf2-6A9F-127bbeE71C78", 429725574702l, "Donna Perez", 5283.1f, "pending"), 
			new Transaction("30d54Cc6-EaF5-C92f-d2Ca-deD43EE96e89", 429725574702l, "Sarah White", 10877.3f, "success"), 
			new Transaction("dfbAfF9b-d7b6-3BBD-4bCB-7b895eaD6daf", 429725574702l, "Scott Lee", 1815f, "pending"), 
			new Transaction("4EC5d1Ca-8E1d-8EAC-9D4B-5fd5fbb4DAd5", 429725574702l, "Kevin Taylor", 14867.93f, "pending"), 
			new Transaction("c509AEb5-00Db-B6aD-1A7e-0bE3ED2B8EC1", 429725574702l, "Elizabeth Thompson", 5228.7f, "success"), 
			new Transaction("350E9eED-78F4-8aDa-D7d7-2Fb8bC3Fe9Ec", 429725574702l, "Jason Brown", 10254.23f, "success"), 
			new Transaction("EFb0C6eB-BCCB-dfD8-317b-9B40cbB9f9fC", 429725574702l, "Linda Lewis", 7904.92f, "success"), 
			new Transaction("c89c739A-3832-ccF8-Ea33-bef2Edf399A4", 429725574702l, "David Hernandez", 5237.81f, "success"), 
			new Transaction("b58cE3a9-FaBE-a42B-0C81-2f5b5C34bDBa", 429725574702l, "Michael Lee", 6179f, "success")
		};

	public static class Transaction {
		private String order_no;
		private Long timestamp;
		private String username;
		private Float price;
		private String status;
		private Transaction(String order_no, Long timestamp, String username, Float price, String status) {
			this.order_no = order_no;
			this.timestamp = timestamp;
			this.username = username;
			this.price = price;
			this.status = status;
		}
		public String getOrder_no() {
			return order_no;
		}
		public Long getTimestamp() {
			return timestamp;
		}
		public String getUsername() {
			return username;
		}
		public Float getPrice() {
			return price;
		}
		public String getStatus() {
			return status;
		}
	}

	public class Data {
		private int total;
		private Object[] items;
		public int getTotal() {
			return total;
		}
		public void setTotal(int total) {
			this.total = total;
		}
		public Object[] getItems() {
			return items;
		}
		public void setItems(Object[] items) {
			this.items = items;
		}
	}

	public class Result {
		private int code;
		private Data data;
		public int getCode() {
			return code;
		}
		public void setCode(int code) {
			this.code = code;
		}
		public Data getData() {
			return data;
		}
		public void setData(Data data) {
			this.data = data;
		}
	}

	@DoNotWrap
	@LoginRequired
	@GetMapping("/article/list")
	@ResponseBody
	public Result articleList(@RequestParam(required = true, value = "page")int page) {
		Object[] items = articleListPage[page-1];
		Data data = new Data();
		data.setTotal(items.length);
		data.setItems(items);
		Result result = new Result();
		result.setCode(20000);
		result.setData(data);
		return result;
	}

	public static class Article {
		private Integer id;
		private Long timestamp;
		private String author;
		private String reviewer;
		private String title;
		private String content_short;
		private String content;
		private Float forecast;
		private Integer importance;
		private String type;
		private String status;
		private String display_time;
		private boolean comment_disabled;
		private int pageviews;
		private String image_uri;
		private String[] platforms;
		public Article() {}
		public Article(Integer id, Long timestamp, String author, String reviewer, String title, String content_short,
				String content, Float forecast, Integer importance, String type, String status, String display_time,
				boolean comment_disabled, int pageviews, String image_uri, String[] platforms) {
			super();
			this.id = id;
			this.timestamp = timestamp;
			this.author = author;
			this.reviewer = reviewer;
			this.title = title;
			this.content_short = content_short;
			this.content = content;
			this.forecast = forecast;
			this.importance = importance;
			this.type = type;
			this.status = status;
			this.display_time = display_time;
			this.comment_disabled = comment_disabled;
			this.pageviews = pageviews;
			this.image_uri = image_uri;
			this.platforms = platforms;
		}
		public Integer getId() {
			return id;
		}
		public Long getTimestamp() {
			return timestamp;
		}
		public String getAuthor() {
			return author;
		}
		public String getReviewer() {
			return reviewer;
		}
		public String getTitle() {
			return title;
		}
		public String getContent_short() {
			return content_short;
		}
		public String getContent() {
			return content;
		}
		public Float getForecast() {
			return forecast;
		}
		public Integer getImportance() {
			return importance;
		}
		public String getType() {
			return type;
		}
		public String getStatus() {
			return status;
		}
		public String getDisplay_time() {
			return display_time;
		}
		public boolean isComment_disabled() {
			return comment_disabled;
		}
		public int getPageviews() {
			return pageviews;
		}
		public String getImage_uri() {
			return image_uri;
		}
		public String[] getPlatforms() {
			return platforms;
		}
	}

	private final static String IMAGE_URL = "https://wpimg.wallstcn.com/e4558086-631c-425c-9430-56ffb46e70b3";
	private final static String CONTENT = "<p>I am testing data, I am testing data.</p><p><img src=\"https://wpimg.wallstcn.com/4c69009c-0fd4-4153-b112-6cb53d1cf943\"></p>";
	private static Object[][] articleListPage = new Object[5][];
	static {
		articleListPage[0] = new Object[] {
				new Article(1, 947579194518l, "Sharon", "Jessica", "Nwgc Inttpnt Nksoboer Upnvxxmm Dsntiv Gxcxbly Reedvja", "mock data", CONTENT, 66.07f, 2, "CN", "published", "1997-09-26 23:19:14", true, 3239, IMAGE_URL, new String[] {"a-platform"}), 
				new Article(2, 271210329007l, "Jeffrey", "Sharon", "Pixzb Ooiirv Ndflcd Nojiouhkj Scjbkew Ynie", "mock data", CONTENT, 24.36f, 1, "JP", "published", "001-03-09 07:13:23", true, 4136, IMAGE_URL, new String[] {"a-platform"}), 
				new Article(3, 470064696674l, "Shirley", "Timothy", "Phgxu Evvcjkfcm Trfd Rcmk Lwnfr Jiqvwzq", "mock data", CONTENT, 7.34f, 3, "JP", "published", "1971-09-15 17:07:50", true, 3648, IMAGE_URL, new String[] {"a-platform"}), 
				new Article(4, 189730665555l, "Robert", "Jessica", "Hxtsfq Xajsdoa Rpuiu Rnt Ppxjpngf Frjfytqmgt", "mock data", CONTENT, 54.15f, 3, "CN", "published", "1971-11-05 23:56:05", true, 844, IMAGE_URL, new String[] {"a-platform"}), 
				new Article(5, 472183356670l, "Thomas", "Shirley", "Pwipow Qggruwzug Aeteopr Pwfw Xmqjj Nffphel", "mock data", CONTENT, 35.33f, 3, "JP", "published", "1990-01-06 16:30:09", true, 3777, IMAGE_URL, new String[] {"a-platform"}), 
				new Article(6, 462573366124l, "Melissa", "Matthew", "Arpge Befkvvndb Gomeib Hjltnul Slyyxeyaso Biwgcdvxl Viv Fcddhrxh", "mock data", CONTENT, 58.21f, 3, "US", "published", "1998-06-13 02:29:03", true, 1044, IMAGE_URL, new String[] {"a-platform"}), 
				new Article(7, 175070108568l, "Christopher", "Lisa", "Xyvez Omgno Ftpwm Fxbhdzpwc Iimut Otsdqi Jbbmkfrr Afxlm Nolgzwjec Laybyh", "mock data", CONTENT, 46.24f, 2, "EU", "draft", "1991-10-02 06:43:39", true, 4687, IMAGE_URL, new String[] {"a-platform"}), 
				new Article(8, 1147673358316l, "Jason", "Frank", "Ebtrw Zcnxule Mwmrpe Dckjig Qkgcbjdtkm", "mock data", CONTENT, 94.07f, 2, "CN", "published", "2016-07-27 00:27:46", true, 4494, IMAGE_URL, new String[] {"a-platform"}), 
				new Article(9, 766239461219l, "Michael", "Edward", "Pdlfcggex Kkeffmjo Wprfb Tllsouvi Hndjv Krbttjo Emjynfxahs Gawctfz", "mock data", CONTENT, 14.76f, 1, "EU", "draft", "2010-10-28 10:44:42", true, 393, IMAGE_URL, new String[] {"a-platform"}), 
				new Article(10, 1270375222855l, "John", "Donald", "Wfgnqx Jkud Qtt Nsmbtlckn Pzvzmao", "mock data", CONTENT, 88.15f, 3, "US", "draft", "1971-12-03 07:12:59", true, 393, IMAGE_URL, new String[] {"a-platform"}), 
				new Article(11, 263234127290l, "Sharon", "Gary", "Kjmqhclz Eibgcn Meeuyut Qkmk Kbubygxei Riojs Zetyyd", "mock data", CONTENT, 32.28f, 2, "EU", "draft", "1973-04-05 01:47:46", true, 2392, IMAGE_URL, new String[] {"a-platform"}), 
				new Article(12, 661350751370l, "John", "Scott", "Ootuxibsm Dqjajp Etgiwmdep Qzucso Krep Ukugune Kuclnofa Wfonxmf Lhkidkfqx", "mock data", CONTENT, 62.34f, 3, "CN", "draft", "1991-05-09 21:06:33", true, 1123, IMAGE_URL, new String[] {"a-platform"}), 
				new Article(13, 1421968412114l, "Laura", "Frank", "Rhhinojj Nrjkc Ubvdbt Hpokukl Qvhhuwwm Euipkadvr Cgcwrcdxk Gvyelu", "mock data", CONTENT, 79.36f, 1, "CN", "published", "2005-04-16 04:03:14", true, 736, IMAGE_URL, new String[] {"a-platform"}), 
				new Article(14, 1176858860586l, "Kevin", "Kenneth", "Rfjrwwf Gilbv Indil Tcjrqldj Gwtetq Yurcsf Uwvd Xunqkwgwne Tdrvd", "mock data", CONTENT, 48.77f, 3, "US", "draft", "2015-08-14 08:08:54", true, 3272, IMAGE_URL, new String[] {"a-platform"}), 
				new Article(15, 1367470633961l, "Steven", "Lisa", "Wglrbx Mson Epial Gytbdttfn Ppsw", "mock data", CONTENT, 55.34f, 2, "EU", "draft", "1995-05-30 22:51:50", true, 957, IMAGE_URL, new String[] {"a-platform"}), 
				new Article(16, 317619666439l, "Susan", "Gary", "Cqsmmqcke Ejpwuzb Kijsbrsv Sedwy Flac Mjtc Dkkhe", "mock data", CONTENT, 51.82f, 2, "JP", "published", "2016-07-20 08:28:41", true, 4572, IMAGE_URL, new String[] {"a-platform"}), 
				new Article(17, 557270082224l, "Paul", "Elizabeth", "Ecnsoamv Flrbu Ygte Grrpc Zvrtdf Celzvmtbt Eguumhf Szsi Vcvfobqq", "mock data", CONTENT, 83.84f, 1, "EU", "draft", "2014-04-23 01:42:24", true, 303, IMAGE_URL, new String[] {"a-platform"}), 
				new Article(18, 262605853246l, "Nancy", "Jose", "Lizgrffu Tkuelrdgzr Zipqff Zsntxp Bwgy Zmqxf Dbxycdbcm", "mock data", CONTENT, 61.43f, 3, "CN", "draft", "1998-07-19 07:36:58", true, 973, IMAGE_URL, new String[] {"a-platform"}), 
				new Article(19, 1016071477940l, "Robert", "George", "Vbpcuvm Remlb Zygzwvp Lkyll Ihj Iycckg Cqjtzcy Yigri Dcpqycydey", "mock data", CONTENT, 61.95f, 2, "JP", "published", "1973-04-10 17:47:00", true, 4177, IMAGE_URL, new String[] {"a-platform"}), 
				new Article(20, 18973582243l, "Margaret", "Betty", "Ofcdqlb Jqoikwxsi Mhgahgltq Izqol Zsjbapddc Iefxgsyskn Bghnmupfx Nnuw", "mock data", CONTENT, 1.24f, 1, "US", "draft", "1978-10-18 22:36:10", true, 3969, IMAGE_URL, new String[] {"a-platform"})
		};
		/*articleListPage[1] = new Object[] {
				new Article(21, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(22, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(23, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(24, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(25, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(26, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(27, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(28, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(29, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(30, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(31, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(32, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(33, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(34, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(35, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(36, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(37, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(38, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(39, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(40, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"})
		};
		articleListPage[2] = new Object[] {
				new Article(41, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(42, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(43, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(44, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(45, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(46, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(47, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(48, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(49, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(50, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(51, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(52, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(53, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(54, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(55, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(56, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(57, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(58, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(59, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(60, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"})
		};
		articleListPage[3] = new Object[] {
				new Article(61, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(62, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(63, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(64, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(65, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(66, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(67, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(68, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(69, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(70, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(71, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(72, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(73, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(74, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(75, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(76, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(77, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(78, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(79, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(80, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"})
		};
		articleListPage[4] = new Object[] {
				new Article(81, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(82, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(83, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(84, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(85, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(86, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(87, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(88, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(89, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(90, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(91, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(92, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(93, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(94, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(95, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(96, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(97, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(98, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(99, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"}), 
				new Article(100, l, "", "", "", "mock data", CONTENT, f, , "", "", "", true, , IMAGE_URL, new String[] {"a-platform"})
		};*/
	}
}
