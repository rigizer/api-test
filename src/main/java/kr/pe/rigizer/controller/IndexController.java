package kr.pe.rigizer.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.pe.rigizer.service.BlogService;
import kr.pe.rigizer.service.YoutubeService;

@Controller
public class IndexController {
	@Autowired private BlogService blogService;
	@Autowired private YoutubeService youtubeService;
	
	@GetMapping({"/","/index"})
	public String index() {
		return "index";
	}
	
	@GetMapping("/blog")
	public String blog(Model model, @RequestParam(value="word", defaultValue="") String word) {
		Map<String, Object> data = null;
		
		if (!word.isBlank()) {
			data = blogService.getBlogSearchData(word);
		}
		
		model.addAttribute("word", word);
		model.addAttribute("data", data);
		
		return "blog";
	}
	
	@GetMapping("/youtube")
	public String youtube(Model model, @RequestParam(value="word", defaultValue="") String word) {
		Map<String, Object> data = null;
		
		if (!word.isBlank()) {
			data = youtubeService.getYoutubeSearchData(word);
		}
		
		model.addAttribute("word", word);
		model.addAttribute("data", data);
		
		return "youtube";
	}
}
