package common.network.requests;

import common.utility.Commands;

public class FilterStartsWithNamerequest extends Request{
    public final String name;
    FilterStartsWithNamerequest(String name){
        super(Commands.FILTER_STARTS_WITH_NAME);
        this.name = name;
    }
}
