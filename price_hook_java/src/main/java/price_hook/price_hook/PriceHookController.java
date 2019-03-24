package price_hook.price_hook;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.util.HashMap;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

import price_hook.price_hook.model.ProductHistory;
import price_hook.price_hook.model.UserData;
import price_hook.price_hook.repository.ProductHistoryRepository;
import price_hook.price_hook.repository.UserDataRepository;

@RestController
public class PriceHookController {

	PageCrawler pageCrawler = new PageCrawler();
	@Value("${spring.datasource.platform}")
	public String dbname;

	@Autowired
	private UserDataRepository userRepo;

	@Autowired
	private ProductHistoryRepository prodRepo;

	@RequestMapping(value = "/getprice", method = RequestMethod.POST)
	public @ResponseBody HashMap<String, String> requestHandler(@Valid @RequestBody UserData userData) {

		boolean serviceExists = true;
		System.out.println("Databse name is " + dbname);

		String searchUrl = userData.getProduct_url();

		System.out.println(userData);

		try {
			searchUrl = URLDecoder.decode(searchUrl, "utf-8");
		} catch (UnsupportedEncodingException e1) {
			System.out.println("Failed to decode url");
		}
		HashMap<String, String> response = new HashMap<>();
		try {
			HtmlPage page = pageCrawler.getPage(searchUrl);
			searchUrl = page.getUrl().toString(); // to handle shortened urls

			print("Search url is " + searchUrl);

			PriceApi api = null;

			if (searchUrl.contains("amazon")) {
				api = new Amazon();
			} else if (searchUrl.contains("shein")) {
				api = new SheIn();
			} else if (searchUrl.contains("ebay")) {
				api = new EBay();
			} else if (searchUrl.contains("bestbuy")) {
				api = new BestBuy();
			} else if (searchUrl.contains("alibaba")) {
				api = new Alibaba();
			} else if (searchUrl.contains("walmart")) {
				api = new Walmart();
			} else if (searchUrl.contains("target")) {
				api = new Target();
			} else if (searchUrl.contains("flipkart")) {
				api = new Flipkart();
			} else if (searchUrl.contains("newegg")) {
				api = new Newegg();
			} else {
				serviceExists = false;
			}

			if (serviceExists) {
				String price = api.getPrice(page, searchUrl);
				String productName = api.getProdName(page);

				if (price.equals("1") || productName.equals("1")) {

					response.put("header", price);

				} else if (price.equals("2") || productName.equals("2")) {

					response.put("header", price);

				} else {

					print("Product Name: " + productName + "\nPrice: " + price);

					response.put("header", "0");
					response.put("product", productName);
					response.put("price", price);

					userRepo.save(userData);
					saveProductData(System.currentTimeMillis(), searchUrl, productName, price);
				}
			} else {
				response.put("header", "-1");
			}

			return response;

		} catch (MalformedURLException malex) {
			response.put("header","2");
			System.out.println("Malformed URL Exception");
			return response;
		}catch (Exception e) {

			e.printStackTrace();
			response.put("header", "4");
			response.put("product", "error-message");
			response.put("price", "error-message");
			return response;
		}

	}

	public static void print(String string) {
		System.out.println(string);
	}

	private void saveProductData(long time, String url, String productName, String price) {

		ProductHistory prodHist = new ProductHistory();
		System.out.println("Current time " + time);
		prodHist.setPrice_date(time);
		prodHist.setProduct_name(productName);
		prodHist.setProduct_price(price);
		prodHist.setProduct_url(url);
		prodRepo.save(prodHist);
		System.out.println("Product history saved!");
	}

}
