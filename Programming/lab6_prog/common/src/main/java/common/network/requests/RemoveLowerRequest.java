package common.network.requests;


import common.domain.Product;
import common.utility.Commands;

public class RemoveLowerRequest extends Request{
    public final Product product;
    RemoveLowerRequest(Product product){
        super(Commands.REMOVE_LOWER);
        this.product = product;
    }
}
