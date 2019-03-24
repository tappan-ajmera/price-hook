package price_hook.price_hook.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

@SuppressWarnings("serial")
@Entity
@Table(name = "product_history")
public class ProductHistory extends AuditModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

//	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="price_date", nullable = false)
	private Timestamp price_date;

	@NotBlank
	private String product_url;
	
	@NotBlank
	private String product_name;

	@NotBlank
	private String product_price;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getPrice_date() {
		return price_date;
	}

	public void setPrice_date(long price_date) {
		System.out.println("Time is :" + new Timestamp(price_date));
		this.price_date = new Timestamp(price_date);
	}

	public String getProduct_url() {
		return product_url;
	}

	public void setProduct_url(String product_url) {
		this.product_url = product_url;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getProduct_price() {
		return product_price;
	}

	public void setProduct_price(String product_price) {
		this.product_price = product_price;
	}


}
