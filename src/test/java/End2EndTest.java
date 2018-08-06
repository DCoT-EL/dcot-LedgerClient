import it.eng.jledgerclient.exception.JLedgerClientException;
import org.hyperledger.fabric.sdk.BlockEvent;
import org.hyperledger.fabric.sdk.ChaincodeEvent;
import org.hyperledger.fabric.sdk.ChaincodeEventListener;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

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



   }

}