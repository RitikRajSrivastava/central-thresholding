package constants;

public interface IKafkaConstants {
    public static String KAFKA_BROKERS = "btpvm4105.hpeswlab.net:31000";

    public static Integer MESSAGE_COUNT = 10;

    public static String CLIENT_ID = "btpvm4105.hpeswlab.net";

    public static String TOPIC_NAME = "test5";

    public static String GROUP_ID_CONFIG = "compose-connect-group";

    public static Integer MAX_NO_MESSAGE_FOUND_COUNT = 100;

    public static String OFFSET_RESET_LATEST = "latest";

    public static String OFFSET_RESET_EARLIER = "earliest";

    public static Integer MAX_POLL_RECORDS = 1;

    public final static String BOOTSTRAP_SERVERS = "kafka1:19092,localhost:9092,localhost:9093,localhost:9094";
}
