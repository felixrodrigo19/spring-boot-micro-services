package br.com.msproject.orders.service;

import br.com.msproject.orders.dto.OrderDto;
import br.com.msproject.orders.dto.StatusDto;
import br.com.msproject.orders.model.Order;
import br.com.msproject.orders.model.Status;
import br.com.msproject.orders.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository repository;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderService(OrderRepository orderRepository, ModelMapper modelMapper) {
        this.repository = orderRepository;
        this.modelMapper = modelMapper;
    }

    public List<OrderDto> findAll() {
        return repository.findAll().stream()
                .map(p -> modelMapper.map(p, OrderDto.class))
                .collect(Collectors.toList());
    }

    public OrderDto findById(Long id) {
        Order order = repository.findById(id).orElseThrow(EntityNotFoundException::new);

        return modelMapper.map(order, OrderDto.class);
    }

    public OrderDto save(OrderDto dto) {
        Order order = modelMapper.map(dto, Order.class);

        order.setDateTime(LocalDateTime.now());
        order.setStatus(Status.DONE);
        order.getItems().forEach(item -> item.setOrder(order));
        Order savedOrder = repository.save(order);

        return modelMapper.map(savedOrder, OrderDto.class);
    }

    public OrderDto update(Long id, StatusDto dto) {
        Order order = repository.findByIdAndWithItems(id)
                .orElseThrow(EntityNotFoundException::new);

        order.setStatus(dto.getStatus());
        repository.updateStatus(dto.getStatus(), order);

        return modelMapper.map(order, OrderDto.class);
    }

    public void approvePayment(Long id) {
        Order order = repository.findByIdAndWithItems(id)
                .orElseThrow(EntityNotFoundException::new);

        order.setStatus(Status.PAID);
        repository.updateStatus(Status.PAID, order);
    }
}
