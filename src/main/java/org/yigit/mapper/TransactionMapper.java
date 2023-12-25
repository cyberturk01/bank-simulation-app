package org.yigit.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.yigit.dto.TransactionDTO;
import org.yigit.entity.Transaction;

@Component
public class TransactionMapper {
    private final ModelMapper modelMapper;

    public TransactionMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public TransactionDTO convertToDTO(Transaction entity){
        return modelMapper.map(entity, TransactionDTO.class);
    }

    public Transaction convertToEntity(TransactionDTO dto){
        return modelMapper.map(dto, Transaction.class);
    }
}
