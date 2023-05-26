package common.network.responses;



import common.domain.Product;
import common.utility.Commands;

import java.util.List;

public class FilterContainsNameResponse extends Response{
    public final List<Product> filteredProducts;
    FilterContainsNameResponse(List<Product> filteredProducts, String error){
        super(Commands.FILTER_CONTAINS_NAME, error);
        this.filteredProducts = filteredProducts;
    }
}
