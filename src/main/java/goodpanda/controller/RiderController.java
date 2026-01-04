package goodpanda.controller;

import goodpanda.domain.Order;
import goodpanda.domain.OrderStatus;
import goodpanda.domain.Rider;
import goodpanda.domain.RiderStatus;
import goodpanda.service.OrderService;
import goodpanda.service.RiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * @author sanjidaera
 * @since 14/12/24
 */
@Controller
@RequestMapping("/rider/{riderId}")
public class RiderController {

    @Autowired
    private RiderService riderService;

    @Autowired
    private OrderService orderService;

    @GetMapping
    public String riderDashboard(@PathVariable("riderId") Integer riderId, ModelMap model) {
        Rider rider = riderService.getRiderById(riderId);

        if (isNull(rider) || rider.getStatus() == RiderStatus.NOT_AVAILABLE) {
            model.put("error", "Rider is not available.");

            return "errorPage";
        }

        List<Order> assignedOrders = riderService.getAssignedOrdersForRider(riderId);
        List<Order> completedOrders = riderService.getCompletedOrdersForRider(riderId);

        completedOrders.forEach(order -> {
            if (nonNull(order.getDeliveryProof())) {
                String base64Image = orderService.getBase64ImageForOrder(order.getId());
                order.setDeliveryProofBase64(base64Image);
            }
        });

        model.put("assignedOrders", assignedOrders);
        model.put("completedOrders", completedOrders);
        model.put("riderId", riderId);

        return "riderDashboard";
    }

    @PostMapping("/order/{orderId}/deliver")
    public String deliverOrder(
            @PathVariable("riderId") Integer riderId,
            @PathVariable("orderId") Integer orderId,
            @RequestParam(value = "deliveryProofImage") MultipartFile deliveryProofImage,
            ModelMap model) {

        Order order = orderService.getOrderById(orderId);
        Integer userId = riderService.getRiderById(riderId).getUser().getId();

        if (isNull(order) || !order.getRider().getId().equals(userId)) {
            model.put("error", "Invalid order or unauthorized access.");

            return "errorPage";
        }

        try {
            if (!deliveryProofImage.isEmpty()) {
                order.setDeliveryProof(deliveryProofImage.getBytes());
            }
        } catch (IOException e) {
            model.put("error", "Failed to process the image.");
            return "errorPage";
        }

//        order.setDeliveryProof(deliveryProof);
        order.setStatus(OrderStatus.DELIVERED);
        order.setDeliveryTime(LocalDateTime.now());
        orderService.saveOrUpdateOrder(order);

        Rider rider = riderService.getRiderById(riderId);
        rider.setStatus(RiderStatus.FREE);
        riderService.saveOrUpdateRider(rider);

        return "redirect:/rider/" + riderId;
    }
}
