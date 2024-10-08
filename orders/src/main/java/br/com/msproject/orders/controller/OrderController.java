package br.com.msproject.orders.controller;

import br.com.msproject.orders.dto.OrderDto;
import br.com.msproject.orders.dto.StatusDto;
import br.com.msproject.orders.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService service;

    @Autowired
    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping
    public List<OrderDto> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getById(@PathVariable @NotNull Long id) {
        OrderDto dto = service.findById(id);

        return ResponseEntity.ok(dto);
    }

    @PostMapping()
    public ResponseEntity<OrderDto> create(@RequestBody @Valid OrderDto dto,
                                           UriComponentsBuilder uriBuilder) {
        OrderDto ordered = service.save(dto);

        URI path = uriBuilder.path("/api/v1/orders/{id}").buildAndExpand(ordered.getId()).toUri();

        return ResponseEntity.created(path).body(ordered);
    }

    @PostMapping("/{id}/status")
    public ResponseEntity<OrderDto> updateStatus(@PathVariable Long id, @RequestBody StatusDto statusDto) {
        OrderDto dto = service.update(id, statusDto);

        return ResponseEntity.ok(dto);

    }

    @PutMapping("/{id}/done")
    public ResponseEntity<OrderDto> approvePayment(@PathVariable @NotNull Long id) {
        service.approvePayment(id);

        return ResponseEntity.ok().build();
    }
}
