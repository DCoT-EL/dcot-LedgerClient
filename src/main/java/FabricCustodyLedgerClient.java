import it.eng.jledgerclient.exception.JLedgerClientException;
import it.eng.jledgerclient.fabric.HLFLedgerClient;
import it.eng.jledgerclient.fabric.config.ConfigManager;
import it.eng.jledgerclient.fabric.config.Configuration;
import it.eng.jledgerclient.fabric.config.Organization;
import it.eng.jledgerclient.fabric.helper.InvokeReturn;
import it.eng.jledgerclient.fabric.helper.LedgerInteractionHelper;
import it.eng.jledgerclient.fabric.helper.QueryReturn;
import it.eng.jledgerclient.fabric.utils.JsonConverter;
import model.ChainOfCustody;
import model.Function;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hyperledger.fabric.sdk.ChaincodeEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


final public class FabricCustodyLedgerClient extends HLFLedgerClient implements CustodyLedgerClient {

    private final static Logger log = LogManager.getLogger(FabricCustodyLedgerClient.class);

    public FabricCustodyLedgerClient() throws JLedgerClientException {
        doLedgerClient();
    }


    private void doLedgerClient() throws JLedgerClientException {
        try {
            configManager = ConfigManager.getInstance();
            Configuration configuration = configManager.getConfiguration();
            if (null == configuration || null == configuration.getOrganizations() || configuration.getOrganizations().isEmpty()) {
                log.error("Configuration missing!!! Check you config file!!!");
                throw new JLedgerClientException("Configuration missing!!! Check you config file!!!");
            }
            List<Organization> organizations = configuration.getOrganizations();
            if (null == organizations || organizations.isEmpty())
                throw new JLedgerClientException("Organizations missing!!! Check you config file!!!");
            //for (Organization org : organizations) {
            //FIXME multiple Organizations
            ledgerInteractionHelper = new LedgerInteractionHelper(configManager, organizations.get(0));
            //}
        } catch (Exception e) {
            log.error(e);
            throw new JLedgerClientException(e);
        }
    }

    @Override
     public String doRegisterEvent(String eventName, ChaincodeEventListener chaincodeEventListener) throws JLedgerClientException {
         return super.doRegisterEvent(eventName, chaincodeEventListener);
     }

    private String doInvokeByJson(Function fcn, List<String> args) throws JLedgerClientException {
        final InvokeReturn invokeReturn = ledgerInteractionHelper.invokeChaincode(fcn.name(), args);
        try {
            log.debug("BEFORE -> Store Completable Future at " + System.currentTimeMillis());
            invokeReturn.getCompletableFuture().get(configManager.getConfiguration().getTimeout(), TimeUnit.MILLISECONDS);
            log.debug("AFTER -> Store Completable Future at " + System.currentTimeMillis());
            final String payload = invokeReturn.getPayload();
            return payload;
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            log.error(fcn.name().toUpperCase() + " " + e.getMessage());
            throw new JLedgerClientException(fcn.name() + " " + e.getMessage());
        }
    }

    private String doQueryByJson(Function fcn, List<String> args) throws JLedgerClientException {
        String data = "";
        try {
            final List<QueryReturn> queryReturns = ledgerInteractionHelper.queryChainCode(fcn.name(), args, null);
            for (QueryReturn queryReturn : queryReturns) {
                data += queryReturn.getPayload();
            }
            return data;
        } catch (Exception e) {
            log.error(fcn.name() + " " + e.getMessage());
            throw new JLedgerClientException(fcn.name() + " " + e.getMessage());
        }
    }


    @Override
    public final ChainOfCustody initNewChain(ChainOfCustody chainOfCustody) throws JLedgerClientException {
        if (chainOfCustody == null) {
            throw new JLedgerClientException(Function.initNewChain.name() + " is in error, No input data!");
        }
        String json = JsonConverter.convertToJson(chainOfCustody);
        List<String> args = new ArrayList<>();
        args.add(json);
        final String payload = doInvokeByJson(Function.initNewChain, args);
        log.debug("Payload retrieved: " + payload);
        final ChainOfCustody chainOfCustody1 = (ChainOfCustody) JsonConverter.convertFromJson(payload, ChainOfCustody.class, false);
        return chainOfCustody1;

    }


    @Override
    public void startTransfer(String id, String receiverId) throws JLedgerClientException {
        if (id == null | receiverId == null) {
            throw new JLedgerClientException(Function.startTransfer.name() + " is in error, No input data!");
        }
        List<String> args = new ArrayList<>();
        args.add(id);
        args.add(receiverId);
        final String payload = doInvokeByJson(Function.startTransfer, args);
        log.debug("Payload retrieved: " + payload);
    }

    @Override
    public void completeTransfer(String id) throws JLedgerClientException {
        if (id == null) {
            throw new JLedgerClientException(Function.completeTrasfer.name() + " is in error, No input data!");
        }
        List<String> args = new ArrayList<>();
        args.add(id);
        final String payload = doInvokeByJson(Function.completeTrasfer, args);
        log.debug("Payload retrieved: " + payload);
    }

    @Override
    public void commentChain(String id, String text) throws JLedgerClientException {
        if (id == null | text == null) {
            throw new JLedgerClientException(Function.commentChain.name() + " is in error, No input data!");
        }
        List<String> args = new ArrayList<>();
        args.add(id);
        args.add(text);
        final String payload = doInvokeByJson(Function.commentChain, args);
        log.debug("Payload retrieved: " + payload);

    }

    @Override
    public void cancelTransfer(String id) throws JLedgerClientException {
        if (id == null) {
            throw new JLedgerClientException(Function.cancelTrasfer.name() + " is in error, No input data!");
        }
        List<String> args = new ArrayList<>();
        args.add(id);
        final String payload = doInvokeByJson(Function.cancelTrasfer, args);
        log.debug("Payload retrieved: " + payload);

    }

    @Override
    public void terminateChain(String id) throws JLedgerClientException {
        if (id == null) {
            throw new JLedgerClientException(Function.terminateChain.name() + " is in error, No input data!");
        }
        List<String> args = new ArrayList<>();
        args.add(id);
        final String payload = doInvokeByJson(Function.terminateChain, args);
        log.debug("Payload retrieved: " + payload);

    }

    @Override
    public ChainOfCustody updateDocument(String id, String documentId) throws JLedgerClientException {

        if (id == null | documentId == null) {
            throw new JLedgerClientException(Function.updateDocument.name() + " is in error, No input data!");
        }
        List<String> args = new ArrayList<>();
        args.add(id);
        args.add(documentId);
        final String payload = doInvokeByJson(Function.updateDocument, args);
        log.debug("Payload retrieved: " + payload);
        final ChainOfCustody chainOfCustody = (ChainOfCustody) JsonConverter.convertFromJson(payload, ChainOfCustody.class, false);
        return chainOfCustody;
    }

    @Override
    public ChainOfCustody getAssetDetails(String id) throws JLedgerClientException {

        if (id == null) {
            throw new JLedgerClientException(Function.getAssetDetails.name() + " is in error, No input data!");
        }
        List<String> args = new ArrayList<>();
        args.add(id);
        final String payload = doInvokeByJson(Function.getAssetDetails, args);
        log.debug("Payload retrieved: " + payload);
        final ChainOfCustody chainOfCustody = (ChainOfCustody) JsonConverter.convertFromJson(payload, ChainOfCustody.class, false);
        return chainOfCustody;
    }

    @Override
    public List<ChainOfCustody> getChainOfEvents(String id) throws JLedgerClientException {

        if (id == null) {
            throw new JLedgerClientException(Function.getChainOfEvents.name() + " is in error, No input data!");
        }
        List<String> args = new ArrayList<>();
        args.add(id);
        final String payload = doInvokeByJson(Function.getChainOfEvents, args);
        log.debug("Payload retrieved: " + payload);
        final List<ChainOfCustody> chainOfCustodyList = (List<ChainOfCustody>) JsonConverter.convertFromJson(payload, ChainOfCustody.class, true);
        return chainOfCustodyList;
    }
}
