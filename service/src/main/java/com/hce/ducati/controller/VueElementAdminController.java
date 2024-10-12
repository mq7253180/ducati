package com.hce.ducati.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
		List<Transaction> items = new ArrayList<>(20);
		items.add(new Transaction("C7cc94dC-183b-01c2-Dd59-C7CdfE9C24BC", 429725574702l, "Edward Clark", 9482.3f, "success"));
		items.add(new Transaction("89313889-C4d5-dEE6-B912-0eB69c275929", 429725574702l, "Kenneth Smith", 2944.22f, "success"));
		items.add(new Transaction("ddEFded1-8ccC-19aa-ec22-bfF26Bb4AeF0", 429725574702l, "Sandra Perez", 6928f, "pending"));
		items.add(new Transaction("d78a2BA4-53ff-98AE-3Aec-2edCEefF1B4f", 429725574702l, "Sharon Perez", 14888.6f, "pending"));
		items.add(new Transaction("7f995761-243d-fDF8-9b3C-E6EaAFc1E51f", 429725574702l, "Timothy Williams", 8666.9f, "success"));
		items.add(new Transaction("4F4eDdE0-9EFD-1b5F-A28A-28E9EA4DDE06", 429725574702l, "Betty Walker", 11660.2f, "pending"));
		items.add(new Transaction("ff51B983-AcFB-C32C-4CFA-10ADeC4EdbFB", 429725574702l, "Michael Jones", 9491.4f, "success"));
		items.add(new Transaction("E0eDe8AE-9C99-Fcc2-Dfb4-8Cb1932ebCfC", 429725574702l, "Sarah Wilson", 3725f, "pending"));
		items.add(new Transaction("AAd11496-A1B3-1703-7208-2bB2bd4fF25F", 429725574702l, "Charles Hall", 2146f, "pending"));
		items.add(new Transaction("4EA78050-7a8b-B4Fe-29Ec-89FDdae3bCFb", 429725574702l, "Ruth Lee", 5548.2f, "pending"));
		items.add(new Transaction("F5Cd3bde-E049-BF56-5e58-ca4f7E2FDB2e", 429725574702l, "Carol Jackson", 9888f, "pending"));
		items.add(new Transaction("E478EA0B-1a69-3Cf2-6A9F-127bbeE71C78", 429725574702l, "Donna Perez", 5283.1f, "pending"));
		items.add(new Transaction("30d54Cc6-EaF5-C92f-d2Ca-deD43EE96e89", 429725574702l, "Sarah White", 10877.3f, "success"));
		items.add(new Transaction("dfbAfF9b-d7b6-3BBD-4bCB-7b895eaD6daf", 429725574702l, "Scott Lee", 1815f, "pending"));
		items.add(new Transaction("4EC5d1Ca-8E1d-8EAC-9D4B-5fd5fbb4DAd5", 429725574702l, "Kevin Taylor", 14867.93f, "pending"));
		items.add(new Transaction("c509AEb5-00Db-B6aD-1A7e-0bE3ED2B8EC1", 429725574702l, "Elizabeth Thompson", 5228.7f, "success"));
		items.add(new Transaction("350E9eED-78F4-8aDa-D7d7-2Fb8bC3Fe9Ec", 429725574702l, "Jason Brown", 10254.23f, "success"));
		items.add(new Transaction("EFb0C6eB-BCCB-dfD8-317b-9B40cbB9f9fC", 429725574702l, "Linda Lewis", 7904.92f, "success"));
		items.add(new Transaction("c89c739A-3832-ccF8-Ea33-bef2Edf399A4", 429725574702l, "David Hernandez", 5237.81f, "success"));
		items.add(new Transaction("b58cE3a9-FaBE-a42B-0C81-2f5b5C34bDBa", 429725574702l, "Michael Lee", 6179f, "success"));
		Data data = new Data();
		data.setTotal(items.size());
		data.setItems(items);
		Result result = new Result();
		result.setCode(20000);
		result.setData(data);
		return result;
	}

	public class Transaction {
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
		private List<Transaction> items;
		public int getTotal() {
			return total;
		}
		public void setTotal(int total) {
			this.total = total;
		}
		public List<Transaction> getItems() {
			return items;
		}
		public void setItems(List<Transaction> items) {
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
}
