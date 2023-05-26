package common.network.responses;

import common.domain.Product;
import common.utility.Commands;


import java.util.List;

public class FilterStartsWithNameResponse extends Response{
    public final List<Product> filteredProducts;
    FilterStartsWithNameResponse(List<Product> filteredProducts, String error){
        super(Commands.FILTER_STARTS_WITH_NAME, error);
        this.filteredProducts = filteredProducts;
    }
}