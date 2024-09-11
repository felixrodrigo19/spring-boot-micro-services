package br.com.msproject.payments.controller;

import br.com.msproject.payments.dto.PaymentDto;
import br.com.msproject.payments.service.PaymentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService service;

    @Autowired
    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @GetMapping
    public Page<PaymentDto> findAll(@PageableDefault(size = 20) Pageable pageable) {
        return service.getAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto> findById(@PathVariable @NotNull Long id) {
        PaymentDto dto = service.getById(id);

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<PaymentDto> create(@RequestBody @Valid PaymentDto dto,
                                             UriComponentsBuilder uriComponentsBuilder) {
        PaymentDto paymentDto = service.save(dto);

        URI uri = uriComponentsBuilder.path("/api/v1/payments/{id}")
                .buildAndExpand(paymentDto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(paymentDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentDto> update(@PathVariable @NotNull Long id,
                                             @RequestBody @Valid PaymentDto dto) {
        PaymentDto paymentDto = service.update(id, dto);
        return ResponseEntity.ok(paymentDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
