package br.com.msproject.payments.service;

import br.com.msproject.payments.dto.PaymentDto;
import br.com.msproject.payments.model.Payment;
import br.com.msproject.payments.model.Status;
import br.com.msproject.payments.repository.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentRepository repository;
    private final ModelMapper modelMapper;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, ModelMapper modelMapper) {
        this.repository = paymentRepository;
        this.modelMapper = modelMapper;
    }

    public Page<PaymentDto> getAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(p -> modelMapper.map(p, PaymentDto.class));
    }

    public PaymentDto getById(Long id) {
        Payment payment = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        return modelMapper.map(payment, PaymentDto.class);
    }

    public PaymentDto save(PaymentDto paymentDto) {
        Payment payment = modelMapper.map(paymentDto, Payment.class);
        payment.setStatus(Status.CREATED);
        repository.save(payment);

        return modelMapper.map(payment, PaymentDto.class);
    }

    public PaymentDto update(Long id, PaymentDto paymentDto) {
        Payment payment = modelMapper.map(paymentDto, Payment.class);
        payment.setId(id);
        payment = repository.save(payment);

        return modelMapper.map(payment, PaymentDto.class);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
