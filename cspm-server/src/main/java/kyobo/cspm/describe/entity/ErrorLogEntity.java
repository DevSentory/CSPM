package kyobo.cspm.describe.entity;

import jakarta.persistence.*;
import kyobo.cspm.utils.Constants;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "error_log", schema = "cspm")
public class ErrorLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_id", length = Constants.LENGTH_SMALL)
    private String accountId;

    @Column(name = "account_name", nullable = false)
    private String accountName;


    @Column(name = "service_group", length = Constants.LENGTH_SMALL)
    private String serviceGroup;

    @Column(name = "resource", length = Constants.LENGTH_SMALL)
    private String resource;

    @Column(name = "describe_time", length = Constants.LENGTH_SMALL)
    private String describeTime;

    @Column(name = "exception_code", length = Constants.LENGTH_SMALL)
    private String exceptionCode;

    @Column(name = "exception_msg", columnDefinition = "TEXT")
    private String exceptionMsg;

    public static ErrorLogEntity of(
            String accountId, String accountName,String serviceGroup, String resource,
            String describeTime, String exceptionCode, String exceptionMsg) {

        ErrorLogEntity newErrorLog = new ErrorLogEntity();
        newErrorLog.accountId = accountId;
        newErrorLog.accountName = accountName;
        newErrorLog.serviceGroup = serviceGroup;
        newErrorLog.resource = resource;
        newErrorLog.describeTime = describeTime;
        newErrorLog.exceptionCode = exceptionCode;
        newErrorLog.exceptionMsg = exceptionMsg;
        return newErrorLog;
    }
}