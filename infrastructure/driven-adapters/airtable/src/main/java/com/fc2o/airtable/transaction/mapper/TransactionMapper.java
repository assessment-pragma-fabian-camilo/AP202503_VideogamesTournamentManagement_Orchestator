package com.fc2o.airtable.transaction.mapper;

import com.fc2o.airtable.transaction.dto.*;
import com.fc2o.model.transaction.PaymentMethod;
import com.fc2o.model.transaction.Status;
import com.fc2o.model.transaction.Transaction;
import com.fc2o.model.transaction.Type;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransactionMapper {

  public Transaction toTransaction(RecordDto dto) {
    return Transaction.builder()
      .createdTime(dto.createdTime())
      .id(dto.id())
      .reference(dto.fields().reference())
      .customerId(dto.fields().customerId())
      .tournamentId(dto.fields().tournamentId())
      .status(Status.valueOf(dto.fields().status().name()))
      .paymentMethod(PaymentMethod.valueOf(dto.fields().paymentMethod().name()))
      .type(Type.valueOf(dto.fields().type().name()))
      .build();
  }

  public WrapperDto toWrapperDto(Transaction transaction) {
    return WrapperDto.builder()
      .records(
        List.of(
          RecordDto.builder()
            .id(transaction.id())
            .fields(
              FieldsDto.builder()
                .reference(transaction.reference())
                .customerId(transaction.customerId())
                .tournamentId(transaction.tournamentId())
                .status(StatusDto.valueOf(transaction.status().name()))
                .paymentMethod(PaymentMethodDto.valueOf(transaction.paymentMethod().name()))
                .type(TypeDto.valueOf(transaction.type().name()))
                .build()
            )
            .build()
        )
      )
      .build();
  }
}
