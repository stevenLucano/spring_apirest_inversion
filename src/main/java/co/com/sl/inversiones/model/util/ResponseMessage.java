package co.com.sl.inversiones.model.util;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@Builder
public class ResponseMessage implements Serializable {
    private String statusCode;
    private String message;
    private Object result;
}
