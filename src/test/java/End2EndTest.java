import it.eng.jledgerclient.exception.JLedgerClientException;
import model.ChainOfCustody;
import org.hyperledger.fabric.sdk.BlockEvent;
import org.hyperledger.fabric.sdk.ChaincodeEvent;
import org.hyperledger.fabric.sdk.ChaincodeEventListener;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;

/**
 * @author clod16
 */

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
    public void integrationTest(){

        //TODO: create a integrationTest complete!!!

        ChainOfCustody chainOfCustody = new ChainOfCustody();
        chainOfCustody.setDocumentId("AHUE2-24CEIHO-33989");
        chainOfCustody.setText("FRAGILE");
        chainOfCustody.setCodeOwner("shdhru-u4h234-bdcbs3-b4782");
        chainOfCustody.setTrackingId("1C1998F051209");
        chainOfCustody.setWeightOfParcel(1.3);
        chainOfCustody.setDistributionOfficeCode("a1234_u4347");
        chainOfCustody.setDistributionZone("Rome");
        chainOfCustody.setSortingCenterDestination("POSTE_ITALIANE_ROME");

        try{
            ChainOfCustody chainOfCustody1 = fabricCustodyLedgerClient.initNewChain(chainOfCustody);
            String assetID = chainOfCustody1.getId();

            fabricCustodyLedgerClient.startTransfer(assetID, "69199da1-e9c8-459c-9ab4-89ba1bb10a66");
            fabricCustodyLedgerClient.commentChain(assetID, "PRIORITY");
            fabricCustodyLedgerClient.updateDocument(assetID, "NEW-DOCUMENT-ID");
            ChainOfCustody chainOfCustody2 = fabricCustodyLedgerClient.getAssetDetails(assetID);
            fabricCustodyLedgerClient.cancelTransfer(assetID);
            fabricCustodyLedgerClient.startTransfer(assetID, "934b0d89-eb53-4f03-9086-79e555253afe");
            fabricCustodyLedgerClient.cancelTransfer(assetID);
            fabricCustodyLedgerClient.startTransfer(assetID, "5a9654f5-ff72-49dd-9be3-b3b524228556");
            fabricCustodyLedgerClient.completeTransfer(assetID);
            fabricCustodyLedgerClient.terminateChain(assetID);
            List<ChainOfCustody> custodyLedgerClientList = fabricCustodyLedgerClient.getChainOfEvents(assetID);
            assertFalse(false);

        } catch (JLedgerClientException e) {
            assertFalse(true);
            e.printStackTrace();
        }

    }

}

// UID of clod16 is 5a9654f5-ff72-49dd-9be3-b3b524228556 with role dcot-operator
//UID of ascat is 15a8256b-faae-4a82-964d-754f26890446 with role dcot-operator
//UID of postman is 69199da1-e9c8-459c-9ab4-89ba1bb10a66 with role dcot-user
//UID of test is 934b0d89-eb53-4f03-9086-79e555253afe with role dcot-user