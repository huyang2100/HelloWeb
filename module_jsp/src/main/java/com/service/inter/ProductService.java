package com.service.inter;

import com.bean.Product;

public interface ProductService {
    Product add(Product product);
    Product get(long id);
}
