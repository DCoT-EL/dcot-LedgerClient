import it.eng.jledgerclient.exception.JLedgerClientException;
import model.ChainOfCustody;
import org.hyperledger.fabric.sdk.BlockEvent;
import org.hyperledger.fabric.sdk.ChaincodeEvent;
import org.hyperledger.fabric.sdk.ChaincodeEventListener;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
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
        final ChainOfCustody chainOfCustody3 = new ChainOfCustody();
        chainOfCustody.setDocumentId("1234");
        chainOfCustody3.setDocumentId("4321");
        try {
            ChainOfCustody chainOfCustody1 = fabricCustodyLedgerClient.initNewChain(chainOfCustody);
            assetID = chainOfCustody1.getId();
            receiverID = "b6a14d80-6262-4d03-b8ea-4ee20ddfe075";
            fabricCustodyLedgerClient.startTransfer(assetID, receiverID);
            //ChainOfCustody chainOfCustody2 = fabricCustodyLedgerClient.initNewChain(chainOfCustody3);
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
            receiverID = "b6a14d80-6262-4d03-b8ea-4ee20ddfe075";
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
            assertFalse(true);
            e.printStackTrace();
        }
    }

    @Test
    public void testCancelTransfer() {

        final ChainOfCustody chainOfCustody  = new ChainOfCustody();
        chainOfCustody.setDocumentId("127FIEI438FB22");

        try {
             ChainOfCustody chainOfCustody1 = fabricCustodyLedgerClient.initNewChain(chainOfCustody);

        String assetID = chainOfCustody1.getId();
        String receiverID = "b6a14d80-6262-4d03-b8ea-4ee20ddfe075";
        fabricCustodyLedgerClient.startTransfer(assetID, receiverID);
        fabricCustodyLedgerClient.cancelTransfer(assetID);
        assertFalse(false);
        } catch (JLedgerClientException e) {
            assertFalse(true);
            System.out.println(e.getMessage());
            //e.printStackTrace();
        }



    }

    @Test
    public void testTerminateChain() {
        final ChainOfCustody chainOfCustody  = new ChainOfCustody();
        chainOfCustody.setDocumentId("127FIEI438FB22");

        try {
            ChainOfCustody chainOfCustody1 = fabricCustodyLedgerClient.initNewChain(chainOfCustody);

            String assetID = chainOfCustody1.getId();
            String receiverID = "5a9654f5-ff72-49dd-9be3-b3b524228556";
            fabricCustodyLedgerClient.startTransfer(assetID, receiverID);
            fabricCustodyLedgerClient.cancelTransfer(assetID);
            fabricCustodyLedgerClient.terminateChain(assetID);
            assertFalse(false);
        } catch (JLedgerClientException e) {
            assertFalse(true);
            System.out.println(e.getMessage());
            //e.printStackTrace();
        }



    }

    @Test
    public void testUpdateDocument() {
        String assetID, docID;

        final ChainOfCustody chainOfCustody = new ChainOfCustody();
        chainOfCustody.setDocumentId("1234");

        try {
            ChainOfCustody chainOfCustody1 = fabricCustodyLedgerClient.initNewChain(chainOfCustody);
            String receiverID = "5a9654f5-ff72-49dd-9be3-b3b524228556";
            assetID = chainOfCustody1.getId();
            docID = "4321";
            fabricCustodyLedgerClient.startTransfer(assetID, receiverID);
            ChainOfCustody chainOfCustody2 = fabricCustodyLedgerClient.updateDocument(assetID, docID);
            if( chainOfCustody2.getDocumentId() == docID) {
                assertFalse(false);
            }
        } catch (JLedgerClientException e) {
            assertFalse(true);
            e.printStackTrace();
        }



    }

    @Test
    public void testGetAssetDetails() {

        final ChainOfCustody chainOfCustody = new ChainOfCustody();
        chainOfCustody.setDocumentId("DVSSYGD353739HDHDDC");

        try{
            ChainOfCustody chainOfCustody1 = fabricCustodyLedgerClient.initNewChain(chainOfCustody);
            String assetID = chainOfCustody1.getId();

            chainOfCustody.setId(assetID);
            assertEquals(chainOfCustody,chainOfCustody1);

        }catch (JLedgerClientException e){
            assertFalse(true);
            System.out.println(e.getMessage());
        }

    }

    @Test
    public void testGetChainOfEvents() {
        String assetID;
        String text;

        final ChainOfCustody chainOfCustody = new ChainOfCustody();
        chainOfCustody.setDocumentId("1234");
        try {
            ChainOfCustody chainOfCustody1 = fabricCustodyLedgerClient.initNewChain(chainOfCustody);
            assetID = chainOfCustody1.getId();
            text = "hello word!";
            fabricCustodyLedgerClient.commentChain(assetID, text);
            //fabricCustodyLedgerClient.completeTransfer(assetID);
            List<ChainOfCustody> chainOfCustodyList;
            chainOfCustodyList = fabricCustodyLedgerClient.getChainOfEvents(assetID);
            Arrays.toString(chainOfCustodyList.toArray());
            assertFalse(false);
        } catch (JLedgerClientException e) {
            assertFalse(true);
            e.printStackTrace();
        }


    }
}


//5a9654f5-ff72-49dd-9be3-b3b524228556
//b6a14d80-6262-4d03-b8ea-4ee20ddfe075