
/* queryconditionSubscriber.java

   A publication of data of type querycondition

   This file is derived from code automatically generated by the rtiddsgen 
   command:

   rtiddsgen -language java -example <arch> .idl

   Example publication of type querycondition automatically generated by 
   'rtiddsgen' To test them follow these steps:

   (1) Compile this file and the example subscription.

   (2) Start the subscription on the same domain used for with the command
       java queryconditionSubscriber <domain_id> <sample_count>

   (3) Start the publication with the command
       java queryconditionPublisher <domain_id> <sample_count>

   (4) [Optional] Specify the list of discovery initial peers and 
       multicast receive addresses via an environment variable or a file 
       (in the current working directory) called NDDS_DISCOVERY_PEERS. 
       
   You can run any number of publishers and subscribers programs, and can 
   add and remove them dynamically from the domain.
              
                                   
   Example:
        
       To run the example application on domain <domain_id>:
            
       Ensure that $(NDDSHOME)/lib/<arch> is on the dynamic library path for
       Java.                       
       
        On UNIX systems: 
             add $(NDDSHOME)/lib/<arch> to the 'LD_LIBRARY_PATH' environment
             variable
                                         
        On Windows systems:
             add %NDDSHOME%\lib\<arch> to the 'Path' environment variable
                        

       Run the Java applications:
       
        java -Djava.ext.dirs=$NDDSHOME/class queryconditionPublisher <domain_id>

        java -Djava.ext.dirs=$NDDSHOME/class queryconditionSubscriber <domain_id>  
       
       
modification history
------------ -------   
*/

import com.rti.dds.domain.DomainParticipant;
import com.rti.dds.domain.DomainParticipantFactory;
import com.rti.dds.infrastructure.RETCODE_NO_DATA;
import com.rti.dds.infrastructure.ResourceLimitsQosPolicy;
import com.rti.dds.infrastructure.StatusKind;
import com.rti.dds.infrastructure.StringSeq;
import com.rti.dds.subscription.InstanceStateKind;
import com.rti.dds.subscription.QueryCondition;
import com.rti.dds.subscription.SampleInfo;
import com.rti.dds.subscription.SampleInfoSeq;
import com.rti.dds.subscription.SampleStateKind;
import com.rti.dds.subscription.Subscriber;
import com.rti.dds.subscription.ViewStateKind;
import com.rti.dds.topic.Topic;

// ===========================================================================

public class queryconditionSubscriber {
    // -----------------------------------------------------------------------
    // Public Methods
    // -----------------------------------------------------------------------
    
    public static void main(String[] args) {
        // --- Get domain ID --- //
        int domainId = 0;
        if (args.length >= 1) {
            domainId = Integer.valueOf(args[0]).intValue();
        }
        
        // -- Get max loop count; 0 means infinite loop --- //
        int sampleCount = 0;
        if (args.length >= 2) {
            sampleCount = Integer.valueOf(args[1]).intValue();
        }
        
        
        /* Uncomment this to turn on additional logging
        Logger.get_instance().set_verbosity_by_category(
            LogCategory.NDDS_CONFIG_LOG_CATEGORY_API,
            LogVerbosity.NDDS_CONFIG_LOG_VERBOSITY_STATUS_ALL);
        */
        
        // --- Run --- //
        subscriberMain(domainId, sampleCount);
    }
    
    
    
    // -----------------------------------------------------------------------
    // Private Methods
    // -----------------------------------------------------------------------
    
    // --- Constructors: -----------------------------------------------------
    
    private queryconditionSubscriber() {
        super();
    }
    
    
    // -----------------------------------------------------------------------
    
    private static void subscriberMain(int domainId, int sampleCount) {

        DomainParticipant participant = null;
        Subscriber subscriber = null;
        Topic topic = null;
        queryconditionDataReader reader = null;

        try {

            // --- Create participant --- //
    
            /* To customize participant QoS, use
               the configuration file
               USER_QOS_PROFILES.xml */
    
            participant = DomainParticipantFactory.TheParticipantFactory.
                create_participant(
                    domainId, DomainParticipantFactory.PARTICIPANT_QOS_DEFAULT,
                    null /* listener */, StatusKind.STATUS_MASK_NONE);
            if (participant == null) {
                System.err.println("create_participant error\n");
                return;
            }                         

            // --- Create subscriber --- //
    
            /* To customize subscriber QoS, use
               the configuration file USER_QOS_PROFILES.xml */
    
            subscriber = participant.create_subscriber(
                DomainParticipant.SUBSCRIBER_QOS_DEFAULT, null /* listener */,
                StatusKind.STATUS_MASK_NONE);
            if (subscriber == null) {
                System.err.println("create_subscriber error\n");
                return;
            }     
                
            // --- Create topic --- //
        
            /* Register type before creating topic */
            String typeName = queryconditionTypeSupport.get_type_name(); 
            queryconditionTypeSupport.register_type(participant, typeName);
    
            /* To customize topic QoS, use
               the configuration file USER_QOS_PROFILES.xml */
    
            topic = participant.create_topic(
                "Example querycondition",
                typeName, DomainParticipant.TOPIC_QOS_DEFAULT,
                null /* listener */, StatusKind.STATUS_MASK_NONE);
            if (topic == null) {
                System.err.println("create_topic error\n");
                return;
            }                     
        
            // --- Create reader --- //

    
            
            /* If you want to change datareader_qos.history programmatically 
             * rather than using the XML file, you will need to add the 
             * following lines to your code and comment out the 
             * create_datareader call above. */

            /*
            DataReaderQos reader_qos = new DataReaderQos();
            subscriber.get_default_datareader_qos(reader_qos);
            
            reader_qos.history.kind = 
                HistoryQosPolicyKind.KEEP_LAST_HISTORY_QOS;
            reader_qos.history.depth = 6;
            reader = (queryconditionDataReader)
            subscriber.create_datareader(
                topic, reader_qos, null,
                StatusKind.STATUS_MASK_NONE);            
            */    
                
            /* To customize data reader QoS, use
               the configuration file USER_QOS_PROFILES.xml */
    
            reader = (queryconditionDataReader)
                subscriber.create_datareader(
                    topic, Subscriber.DATAREADER_QOS_DEFAULT, null,
                    StatusKind.STATUS_MASK_ALL);
            if (reader == null) {
                System.err.println("create_datareader error\n");
                return;
            }                         

            /* NOTE: There must be single-quotes in the query parameters around
             * any strings!  The single-quotes do NOT go in the query condition
             * itself. 
             */

            /* Query for 'GUID2'  This query parameter can be changed at runtime, 
             * allowing an application to selectively look at subsets of data
             * at different times. */
            StringSeq query_parameters = new StringSeq(1);
            query_parameters.add("'GUID2'");
            
            /* Create the QueryCondition */
            QueryCondition query_for_guid2 = 
                reader.create_querycondition(
                        SampleStateKind.ANY_SAMPLE_STATE, 
                        ViewStateKind.ANY_VIEW_STATE, 
                        InstanceStateKind.ALIVE_INSTANCE_STATE, 
                        "id MATCH %0", 
                        query_parameters);
           
            // --- Wait for data --- //

            final long receivePeriodSec = 4;

            for (int count = 0;
                 (sampleCount == 0) || (count < sampleCount);
                 ++count) {
            	
            	
            	SampleInfoSeq infoSeq = new SampleInfoSeq();
            	queryconditionSeq dataSeq = new queryconditionSeq();
            	
            	try {
            		reader.read_w_condition(dataSeq, 
            				infoSeq, 
                            ResourceLimitsQosPolicy.LENGTH_UNLIMITED,
                            query_for_guid2);
            		
            		
                    int len = 0;
                    double sum = 0;

                    /* Iterate through the samples read using the 
                     * read_w_condition() method, accessing only the samples of
                     * GUID2.  Then, show the number of samples received and, 
                     * adding the value of x on each of them to calculate the 
                     * average afterwards. */
                    for (int i = 0; i < dataSeq.size(); ++i) {
                        if (false == ((SampleInfo)infoSeq.get(i)).valid_data) {
                            continue;
                        }
                        len++;
                        sum += ((querycondition)dataSeq.get(i)).value;
                        System.out.println("Guid = " + 
            	            ((querycondition)dataSeq.get(i)).id);
                    }

                    if (len > 0)
                        System.out.println("Got " + len + " samples.  Avg = " +
                                sum/len);

                    reader.return_loan(dataSeq, infoSeq);

            		
            	} catch (RETCODE_NO_DATA noData) {
            		System.out.println("No data");
            	}
            	
                System.out.println("querycondition subscriber sleeping for "
                                   + receivePeriodSec + " sec...");
                try {
                    Thread.sleep(receivePeriodSec * 1000);  // in millisec
                } catch (InterruptedException ix) {
                    System.err.println("INTERRUPTED");
                    break;
                }
            }
        } finally {

            // --- Shutdown --- //

            if(participant != null) {
                participant.delete_contained_entities();

                DomainParticipantFactory.TheParticipantFactory.
                    delete_participant(participant);
            }
            /* RTI Connext provides the finalize_instance()
               method for users who want to release memory used by the
               participant factory singleton. Uncomment the following block of
               code for clean destruction of the participant factory
               singleton. */
            //DomainParticipantFactory.finalize_instance();
        }
    }
    
    // -----------------------------------------------------------------------
    // Private Types
    // -----------------------------------------------------------------------
    
    // =======================================================================
    

}


        