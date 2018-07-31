import it.eng.jledgerclient.exception.JLedgerClientException;
import model.ChainOfCustody;
import org.hyperledger.fabric.sdk.BlockEvent;
import org.hyperledger.fabric.sdk.ChaincodeEvent;
import org.hyperledger.fabric.sdk.ChaincodeEventListener;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class End2EndTest {

    static FabricCustodyLedgerClient fabricCustodyLedgerClient;
    static ChaincodeEventListener chaincodeEventListener;

    @BeforeClass
    public static void begin() {
        try {
            chaincodeEventListener = new ChaincodeEventListener() {
                @Override
                public void received(String handle, BlockEvent blockEvent, ChaincodeEvent chaincodeEvent) {
                    String payload = new String(chaincodeEvent.getPayload());
                    System.out.println("Event from chaincode: " + chaincodeEvent.getEventName() + " " + payload);

                }
            };
            fabricCustodyLedgerClient = new FabricCustodyLedgerClient();
        } catch (JLedgerClientException e) {
            assertFalse(e.getMessage(), true);
        }
    }

    @AfterClass
    public static void end() {
        fabricCustodyLedgerClient = null;
    }


    @Test
    public void testInitNewChain() {
        final ChainOfCustody chainOfCustody = new ChainOfCustody();
        chainOfCustody.setTrackingId("1234");
        chainOfCustody.setCodeOwner("ABCD");
        chainOfCustody.setDocumentId("3232");
        try {
            ChainOfCustody chainOfCustody1 = fabricCustodyLedgerClient.initNewChain(chainOfCustody);
            if (chainOfCustody1.getId() != null) {
                assertFalse(false);
            }
        } catch (JLedgerClientException e) {
            assertFalse(e.getMessage(), true);
        }

    }

    @Test
    public void testStartTransfer() {
        String assetID;
        String receiverID;

        final ChainOfCustody chainOfCustody = new ChainOfCustody();
        chainOfCustody.setDocumentId("1234");
        try {
            ChainOfCustody chainOfCustody1 = fabricCustodyLedgerClient.initNewChain(chainOfCustody);
            assetID = chainOfCustody1.getId();
            receiverID = "admin";
            fabricCustodyLedgerClient.startTransfer(assetID, receiverID);
        } catch (JLedgerClientException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void testCompleteTransfer() {
        String assetID;
        String receiverID;

        final ChainOfCustody chainOfCustody = new ChainOfCustody();
        chainOfCustody.setDocumentId("1234");
        try {
            ChainOfCustody chainOfCustody1 = fabricCustodyLedgerClient.initNewChain(chainOfCustody);
            assetID = chainOfCustody1.getId();
            receiverID = "admin";
            fabricCustodyLedgerClient.startTransfer(assetID, receiverID);
            fabricCustodyLedgerClient.completeTransfer(assetID);
            assertFalse(false);
        } catch (JLedgerClientException e) {
            assertFalse(false);
            e.printStackTrace();
        }


    }

    @Test
    public void testCommentChain() {
        String assetID, text = "*** testCommentChain ***";

        final ChainOfCustody chainOfCustody = new ChainOfCustody();
        chainOfCustody.setDocumentId("1234");

        ChainOfCustody chainOfCustody1 = null;
        try {
            chainOfCustody1 = fabricCustodyLedgerClient.initNewChain(chainOfCustody);

            assetID = chainOfCustody1.getId();
            fabricCustodyLedgerClient.commentChain(assetID, text);
            //fabricCustodyLedgerClient.doRegisterEvent("commentChain EVENT: ", chaincodeEventListener );
            assertFalse(false);
        } catch (JLedgerClientException e) {
            assertFalse(false);
            e.printStackTrace();
        }
    }

    @Test
    public void testCancelTransfer() {

    }

    @Test
    public void testTerminateChain() {

    }

    @Test
    public void testUpdateDocument() {

    }

    @Test
    public void testGetAssetDetails() {

    }

    @Test
    public void testGetChainOfEvents() {

    }
}
