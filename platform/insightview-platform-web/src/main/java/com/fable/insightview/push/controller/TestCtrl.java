package com.fable.insightview.push.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fable.insightview.platform.core.push.Publish;
import com.fable.insightview.platform.core.push.PushMessage;

@Controller
public class TestCtrl {
	
	@Autowired
	private Publish publish;

	@RequestMapping("/publish/{msg}/{account}")
	@ResponseBody
	public void test(@PathVariable String msg,@PathVariable String account) {
		PushMessage message = new PushMessage();
		Msg info = new Msg();
		info.setContent(msg);
		info.setTitle("拿钱");
		message.setMessage(info);
		List<String> UserAccountList = new ArrayList<String>();
		UserAccountList.add(account);
		message.setNotifyUserAccounts(UserAccountList);
		publish.publish(message, "ann");
	}
	
	public class Msg {
		private String content;

		private String title;

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}
	}
}
