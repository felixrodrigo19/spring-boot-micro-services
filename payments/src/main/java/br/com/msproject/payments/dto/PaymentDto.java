package br.com.msproject.payments.dto;

import br.com.msproject.payments.model.Status;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaymentDto {

    private Long id;

    private BigDecimal value;

    private String name;

    private String number;

    private String expirationDate;

    private String code;

    private Status status;

    private Long orderId;

    private Long paymentMethodId;
}
