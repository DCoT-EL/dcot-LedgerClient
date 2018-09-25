import it.eng.jledgerclient.exception.JLedgerClientException;
import model.ChainOfCustody;

import java.util.List;

/**
 * @author clod16
 */
public interface CustodyLedgerClient   {

    ChainOfCustody initNewChain(ChainOfCustody chainOfCustody) throws  JLedgerClientException;

    void startTransfer(String id, String receiverId) throws JLedgerClientException;

    void completeTransfer(String id) throws JLedgerClientException;

    void commentChain(String id, String text) throws JLedgerClientException;

    void cancelTransfer(String id) throws JLedgerClientException;

    void terminateChain(String id) throws JLedgerClientException;

    ChainOfCustody updateDocument(String id, String docuementId) throws JLedgerClientException;

    ChainOfCustody getAssetDetails(String id) throws JLedgerClientException;

    List<ChainOfCustody> getChainOfEvents(String id) throws JLedgerClientException;

    void callCaincode(String arg ) throws JLedgerClientException;
}
