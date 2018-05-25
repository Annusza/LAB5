package rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import domain.Product;
import domain.Product.Category;

@Path("/product")
@Stateless
public class ProductResources {

	@PersistenceContext
	EntityManager em;

	// 1. pobranie listy wsztstkich produktów
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getAll() {
		return em.createNamedQuery("product.all", Product.class).getResultList();
	}

	// 2. dodanie (zapisanie) nowego produktu
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response add(Product product) {
		em.persist(product);
		return Response.ok(product.getId()).build();
	}

	// 3. pobranie produktu o podanym id
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("id") int id) {
		Product result = em.createNamedQuery("product.id", Product.class).setParameter("productId", id)
				.getSingleResult();
		if (result == null) {
			return Response.status(404).build();
		}
		return Response.ok(result).build();
	}

	// 4. zaktualizowanie informacji o produkcie
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") int id, Product p) {
		Product result = em.createNamedQuery("product.id", Product.class).setParameter("productId", id)
				.getSingleResult();
		if (result == null) {
			return Response.status(404).build();
		}
		result.setName(p.getName());
		result.setPrice(p.getPrice());
		result.setCategory(p.getCategory());
		em.persist(result);
		return Response.ok().build();
	}

	// 5. pobranie listy produktów o podanej nazwie
	@GET
	@Path("/name/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getName(@PathParam("name") String name) {
		return em.createNamedQuery("product.name", Product.class).setParameter("name", name).getResultList();
	}

	// 6. pobranie listy produktów o podanej kategorii//
	@GET
	@Path("/category/{category}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getCategory(@PathParam("category") String categoryString) {
		Category category;

		try {
			category = Category.valueOf(categoryString);
		} catch (Exception e) {
			
			return new ArrayList<Product>();
		}
		return em.createNamedQuery("product.category", Product.class).setParameter("category", category)
				.getResultList();
	}

	// 7. pobranie listy produktów o podanym zakresie cenowym//
	@GET
	@Path("/price/{min}/{max}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getPrice(@PathParam("min") BigDecimal min, @PathParam("max") BigDecimal max) {
		return em.createNamedQuery("product.howMuch", Product.class).setParameter("min", min).setParameter("max", max)
				.getResultList();
	}

	// 8. usuwanie produktu o podanym id
	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") int id) {
		Product result = em.createNamedQuery("product.id", Product.class).setParameter("productId", id)
				.getSingleResult();
		if (result == null) {
			return Response.status(404).build();
		}
		em.remove(result);
		return Response.ok().build();
	}


}
