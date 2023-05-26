package common.network.requests;

import common.utility.Commands;


public class HistoryRequest extends Request{
    public HistoryRequest() {
        super(Commands.HISTORY);
    }
}
