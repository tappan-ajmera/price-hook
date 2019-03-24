package price_hook.price_hook;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlHeading1;
import com.gargoylesoftware.htmlunit.html.HtmlMeta;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;

public interface PriceApi {

	public String getPrice(HtmlPage page, String url);

	public String getProdName(HtmlPage page);
}

class Amazon implements PriceApi {

	public String getPrice(HtmlPage page, String url) {
		try {
			HtmlSpan priceBlock = (HtmlSpan) page.getFirstByXPath("//span[@id='priceblock_ourprice']");
			String price = priceBlock.getTextContent().trim();
			price = price.replaceAll("[^0-9.-]", "");

			return price;
		} catch (NullPointerException e) {
			return "1";
		} catch (Exception ex) {
			return "3";
		}
	}

	public String getProdName(HtmlPage page) {
		// TODO Auto-generated method stub
		try {
			HtmlSpan prodName = (HtmlSpan) page.getFirstByXPath("//span[@id='productTitle']");
			String name = prodName.getTextContent();
			name = name.trim().replace("\n", "");
			return name;
		} catch (NullPointerException e) {
			return "1";
		} catch (Exception ex) {
			return "3";
		}
	}

}

class SheIn implements PriceApi {

	public String getPrice(HtmlPage page, String url) {
		try {
			DomElement price = (DomElement) page.getFirstByXPath("//meta[@itemprop='price']");
			return price.getAttribute("content").trim();
		} catch (NullPointerException e) {
			return "1";
		} catch (Exception ex) {
			return "3";
		}
	}

	public String getProdName(HtmlPage page) {
		try {
			DomElement productName = (DomElement) page.getFirstByXPath("//meta[@itemprop='name']");
			return productName.getAttribute("content").trim();
		} catch (NullPointerException e) {
			return "1";
		} catch (Exception ex) {
			return "3";
		}
	}

}

class EBay implements PriceApi {

	public String getPrice(HtmlPage page, String url) {

		try {
			HtmlSpan priceBlock = (HtmlSpan) page.getFirstByXPath("//span[@itemprop='price']");
			String price = priceBlock.getTextContent().trim();
			price = price.replaceAll("[^0-9.-]", "");

			return price;
		} catch (NullPointerException e) {
			return "1";
		} catch (Exception ex) {
			return "3";
		}
	}

	public String getProdName(HtmlPage page) {
		try {
			HtmlHeading1 prodName = (HtmlHeading1) page.getFirstByXPath("//h1[@itemprop='name']");
			DomNodeList<DomNode> childNodes = prodName.getChildNodes();
			String name = childNodes.get(childNodes.size() - 1).toString(); // to remove "details about"
			name = name.trim().replace("\n", "");
			return name;
		} catch (NullPointerException e) {
			return "1";
		} catch (Exception ex) {
			return "3";
		}

	}

}

class BestBuy implements PriceApi {

	@Override
	public String getPrice(HtmlPage page, String url) {

		try {
			String xPath = "//div[contains(@class,'priceView-hero-price priceView-customer-price')]";
			HtmlDivision priceView = (HtmlDivision) page.getFirstByXPath(xPath);
			DomNodeList<DomNode> priceNodes = priceView.getChildNodes();
			String price = priceNodes.get(priceNodes.getLength() - 1).getTextContent();
			price = price.replaceAll("[^0-9.-]", "");
			return price;
		} catch (NullPointerException e) {
			return "1";
		} catch (Exception ex) {
			return "3";
		}
	}

	@Override
	public String getProdName(HtmlPage page) {
		try {
			HtmlHeading1 prodName = (HtmlHeading1) page.getFirstByXPath("//h1[@class='heading-5 v-fw-regular']");
			String name = prodName.getTextContent();
			return name;
		} catch (NullPointerException e) {
			return "1";
		} catch (Exception ex) {
			return "3";
		}
	}

}

class Alibaba implements PriceApi {

	@Override
	public String getPrice(HtmlPage page, String url) {
		try {
			String xpathExpr = "//span[@class='ma-ref-price']/span[2]";
			HtmlSpan prodPrice = (HtmlSpan) page.getFirstByXPath(xpathExpr);
			String price = prodPrice.getTextContent().trim();
			return price;
		} catch (NullPointerException e) {
			return "1";
		} catch (Exception ex) {
			return "3";
		}
	}

	@Override
	public String getProdName(HtmlPage page) {
		try {
			String xpathExpr = "//h1[@class='ma-title']";
			HtmlHeading1 prodName = (HtmlHeading1) page.getFirstByXPath(xpathExpr);
			String name = prodName.getTextContent().trim();
			return name;
		} catch (NullPointerException e) {
			return "1";
		} catch (Exception ex) {
			return "3";
		}
	}

}

class Walmart implements PriceApi {

	@Override
	public String getPrice(HtmlPage page, String url) {
		try {
			String xpathExpr = "//span[@itemprop='price']";
			HtmlSpan prodPrice = (HtmlSpan) page.getFirstByXPath(xpathExpr);
			String price = prodPrice.getAttribute("content").trim();
			return price;
		} catch (NullPointerException e) {
			return "1";
		} catch (Exception ex) {
			return "3";
		}
	}

	@Override
	public String getProdName(HtmlPage page) {
		try {
			String xpathExpr = "//h1[@itemprop='name']";
			HtmlHeading1 prodName = (HtmlHeading1) page.getFirstByXPath(xpathExpr);
			String name = prodName.getAttribute("content").trim();
			return name;
		} catch (NullPointerException e) {
			return "1";
		} catch (Exception ex) {
			return "3";
		}
	}

}

class Target implements PriceApi {

	@Override
	public String getPrice(HtmlPage page, String url) {
		try {
			String xpathExpr = "//span[@data-test='product-price']";
			HtmlSpan prodPrice = (HtmlSpan) page.getFirstByXPath(xpathExpr);
			String price = prodPrice.getTextContent().replaceAll("[^0-9.-]", "").trim();
			return price;
		} catch (NullPointerException e) {
			return "1";
		} catch (Exception ex) {
			return "3";
		}
	}

	@Override
	public String getProdName(HtmlPage page) {
		try {
			String xpathExpr = "//span[@data-test='product-title']";
			HtmlSpan prodName = (HtmlSpan) page.getFirstByXPath(xpathExpr);
			String name = prodName.getTextContent().trim();
			return name;
		} catch (NullPointerException e) {
			return "1";
		} catch (Exception ex) {
			return "3";
		}
	}

}

class Flipkart implements PriceApi {

	@Override
	public String getPrice(HtmlPage page, String url) {
		try {
			String xpathExpr = "//div[@class='_1vC4OE _3qQ9m1']";
			HtmlDivision prodPrice = (HtmlDivision) page.getFirstByXPath(xpathExpr);
			String price = prodPrice.getTextContent().replaceAll("[^0-9.-]", "").trim();
			System.out.println("Length of name is " + price.length());
			return price;
		} catch (NullPointerException e) {
			return "1";
		} catch (Exception ex) {
			return "3";
		}
	}

	@Override
	public String getProdName(HtmlPage page) {
		try {
			String xpathExpr = "//span[@class=\"_35KyD6\"]";
			HtmlSpan prodName = (HtmlSpan) page.getFirstByXPath(xpathExpr);
			String name = prodName.getTextContent().trim();
			System.out.println("Length of name is " + name.length());
			return name;
		} catch (NullPointerException e) {
			return "1";
		} catch (Exception ex) {
			return "3";
		}
	}

}

class Newegg implements PriceApi {

	@Override
	public String getPrice(HtmlPage page, String url) {
		try {
			String xpathExpr = "//meta[@itemprop='price']";
			HtmlMeta prodPrice = (HtmlMeta) page.getFirstByXPath(xpathExpr);
			String price = prodPrice.getAttribute("content").trim();
			return price;
		} catch (NullPointerException e) {
			return "1";
		} catch (Exception ex) {
			return "3";
		}
	}

	@Override
	public String getProdName(HtmlPage page) {
		try {
			String xpathExpr = "//span[@itemprop=\"name\"]";
			HtmlSpan prodName = (HtmlSpan) page.getFirstByXPath(xpathExpr);
			String name = prodName.getTextContent().trim();
			return name;
		} catch (NullPointerException e) {
			return "1";
		} catch (Exception ex) {
			return "3";
		}
	}

}
