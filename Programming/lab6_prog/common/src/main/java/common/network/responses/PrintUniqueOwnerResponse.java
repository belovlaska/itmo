package common.network.responses;



import common.domain.Person;
import common.utility.Commands;

import java.util.Set;

public class PrintUniqueOwnerResponse extends Response{
    public final Set<Person> uniqueOwners;
    PrintUniqueOwnerResponse(Set<Person> uniqueOwners, String error){
        super(Commands.PRINT_UNIQUE_OWNER, error);
        this.uniqueOwners = uniqueOwners;
    }
}
