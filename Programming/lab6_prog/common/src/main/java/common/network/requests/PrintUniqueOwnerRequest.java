package common.network.requests;

import common.utility.Commands;


public class PrintUniqueOwnerRequest extends Request{
    PrintUniqueOwnerRequest(){
        super(Commands.PRINT_UNIQUE_OWNER);
    }
}
