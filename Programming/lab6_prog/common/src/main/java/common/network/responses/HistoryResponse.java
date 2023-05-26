package common.network.responses;

import common.utility.Commands;

import java.util.List;

public class HistoryResponse extends Response{
    public final List<String> history;
    HistoryResponse(List<String> history, String error){
        super(Commands.HISTORY, error);
        this.history = history;
    }
}
