package domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@NamedQueries
({
	@NamedQuery(name="product.all", query="SELECT p FROM Product p"),
	@NamedQuery(name="product.id", query="FROM Product p WHERE p.id=:productId"),
	@NamedQuery(name="product.name", query="SELECT p FROM Product p WHERE p.name = :name"),
	@NamedQuery(name="product.category", query="SELECT p FROM Product p WHERE p.category = :category"),
	@NamedQuery(name="product.howMuch", query="SELECT p FROM Product p WHERE p.price BETWEEN :min AND :max")
//	@NamedQuery(name="product.comment", query="SELECT p FROM Product p WHERE p.comment = :comment")
})
public class Product {

	public enum Category {
		GPU("Karty graficzne"), MB("P³yty g³ówne"), HDD("Dyski twarde"), RAM("Pamieci RAM");

		protected final String description;

		public String getDescription() {
			return description;
		}

		private Category(String description) {
			this.description = description;
		}

	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private BigDecimal price;
	private String name;
	private Category category;
//	private String comment;
	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}

	

}