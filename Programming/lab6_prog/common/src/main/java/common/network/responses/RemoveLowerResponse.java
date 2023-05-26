package common.network.responses;


import common.utility.Commands;

public class RemoveLowerResponse extends Response{
    RemoveLowerResponse(String error){
        super(Commands.REMOVE_LOWER, error);
    }
}
