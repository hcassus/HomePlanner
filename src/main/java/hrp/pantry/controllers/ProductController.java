package hrp.pantry.controllers;

import hrp.pantry.persistence.entities.Product;
import hrp.pantry.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/product")
public class ProductController {

  @Autowired
  ProductService service;

  @RequestMapping(path = "/{eanCode}", method = RequestMethod.GET)
  public Product retrieveProductData(@PathVariable String eanCode){
    return service.retrieveItemDataByEan(eanCode);
  }

}
