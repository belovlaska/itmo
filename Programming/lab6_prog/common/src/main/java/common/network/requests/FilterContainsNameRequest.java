package common.network.requests;


import common.utility.Commands;

public class FilterContainsNameRequest extends Request{
    public final String name;
    public FilterContainsNameRequest(String name) {
        super(Commands.FILTER_CONTAINS_NAME);
        this.name = name;
    }
}
